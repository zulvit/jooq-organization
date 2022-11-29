package ru.zulvit.reports;

import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import ru.zulvit.flyway.JDBCCredentials;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static generated.Tables.*;
import static generated.tables.Invoice.INVOICE;
import static org.jooq.impl.DSL.*;

public class PersonalizedReports {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    private record AveragePrice(double price, int amount) {
    }

    private record ListForThePeriod(int amount, @NotNull List<Integer> listProductsId) {
    }

    /**
     * @param n сколько вывести поставщиков
     * @return первые n поставщиков по количеству поставленного товара
     */
    public Map<Integer, Object> topNByDelivered(int n) {
        //SQL
        //SELECT DISTINCT overhead.organization_id, sum(invoice.amount)
        //OVER(PARTITION BY overhead.organization_id) AS total_sum
        //FROM overhead JOIN invoice ON overhead."ID" = invoice."ID"
        //ORDER BY total_sum DESC
        // LIMIT 10
        final LinkedHashMap<Integer, Object> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            final @NotNull Result<Record2<Integer, BigDecimal>> result = context
                    .selectDistinct(
                            OVERHEAD.ORGANIZATION_ID,
                            sum(INVOICE.AMOUNT).over().partitionBy(OVERHEAD.ORGANIZATION_ID).as("total_sum")
                    )
                    .from(OVERHEAD).join(INVOICE).on(OVERHEAD.ID.eq(INVOICE.ID))
                    .orderBy(inline(2).desc())
                    .limit(n)
                    .fetch();
            for (Record record : result) {
                Integer organizationId = record.getValue(OVERHEAD.ORGANIZATION_ID);
                Object sum = record.getValue("total_sum");
                map.put(organizationId, sum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param minValue минимальное значение товара
     * @return поставщики, с количеством поставленного товара выше minValue
     */
    @NotNull
    public Map<Integer, Object> supplierOfAtLeast(int minValue) {
        final LinkedHashMap<Integer, Object> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            final @NotNull Result<Record2<Integer, BigDecimal>> result = context
                    .selectDistinct(
                            OVERHEAD.ORGANIZATION_ID,
                            sum(INVOICE.AMOUNT).over().partitionBy(OVERHEAD.ORGANIZATION_ID).as("total_sum")
                    )
                    .from(OVERHEAD).join(INVOICE).on(OVERHEAD.ID.eq(INVOICE.ID))
                    .fetch();
            for (Record record : result) {
                Integer organizationId = record.getValue(OVERHEAD.ORGANIZATION_ID);
                Object sum = record.getValue("total_sum");
                if (Integer.parseInt(sum.toString()) > minValue) {
                    map.put(organizationId, sum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private ResultSet prepareDate(@NotNull Calendar start, @NotNull Calendar end, Statement statement, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH) + 1) + "-" + start.get(Calendar.DAY_OF_MONTH));
        preparedStatement.setString(2, end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH) + 1) + "-" + end.get(Calendar.DAY_OF_MONTH));
        return statement.executeQuery(preparedStatement.toString());
    }

    /**
     * @param start начало периода
     * @param end   конец периода
     * @return количество и сумма полученного товара в указанном периоде
     */
    public Map<String, Map<Integer, Double>> calcAllProduct(@NotNull LocalDate start, @NotNull LocalDate end) {
        Map<String, Map<Integer, Double>> replyMap = new TreeMap<>();
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = using(connection, SQLDialect.POSTGRES);
            final @NotNull Result<Record4<LocalDate, Integer, Integer, Double>> result = context
                    .select(
                            OVERHEAD.DATE,
                            INVOICE.PRODUCT_ID,
                            INVOICE.AMOUNT,
                            INVOICE.PRICE
                    )
                    .from(OVERHEAD).join(INVOICE).on(OVERHEAD.ID.eq(INVOICE.ID))
                    .where(OVERHEAD.DATE.between(start, end))
                    .fetch();

            for (Record record : result) {
                Map<Integer, Double> idProductMap = new TreeMap<>(); //Integer - id товара, Double - цена
                Integer productId = record.getValue(INVOICE.PRODUCT_ID);
                Double price = record.getValue(INVOICE.PRICE);
                Integer amount = record.getValue(INVOICE.AMOUNT);
                String date = record.getValue(OVERHEAD.DATE).toString();
                idProductMap.merge(productId, price * amount, Double::sum);
                replyMap.put(date, idProductMap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return replyMap;
    }

    /**
     * @param start начало периода
     * @param end   конец периода
     * @return средняя цена по каждому товару за период
     */
    public Map<Integer, Double> calcAveragePriceOfThePeriod(@NotNull LocalDate start, @NotNull LocalDate end) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = using(connection, SQLDialect.POSTGRES);
            final @NotNull Result<Record5<Integer, LocalDate, Integer, Integer, Double>> result = context
                    .select(
                            OVERHEAD.ORGANIZATION_ID,
                            OVERHEAD.DATE,
                            INVOICE.PRODUCT_ID,
                            INVOICE.AMOUNT,
                            INVOICE.PRICE
                    )
                    .from(OVERHEAD).join(INVOICE).on(OVERHEAD.ID.eq(INVOICE.ID))
                    .where(OVERHEAD.DATE.between(start, end))
                    .fetch();
            final Map<Integer, AveragePrice> map = new TreeMap<>();
            for (Record record : result) {
                Integer productId = record.getValue(INVOICE.PRODUCT_ID);
                Integer amount = record.getValue(INVOICE.AMOUNT);
                Double price = record.getValue(INVOICE.PRICE);
                if (map.get(productId) != null) {
                    map.put(productId, new AveragePrice(map.get(productId).price + price, map.get(productId).amount + amount));
                } else {
                    map.put(productId, new AveragePrice(price, amount));
                }
            }
            Map<Integer, Double> averageMap = new TreeMap<>();
            HashSet<Integer> set = new HashSet<>(map.keySet());
            Object[] arrayKeys = set.toArray();
            for (int i = 0; i < map.size(); i++) {
                double price = map.get((Integer) arrayKeys[i]).price / map.get((Integer) arrayKeys[i]).amount;
                averageMap.put((Integer) arrayKeys[i], price);
            }
            return averageMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * @param start начало периода
     * @param end   конец периода
     * @return список товаров, поставленных организациями за период.
     */
    @NotNull
    public Map<Integer, ListForThePeriod> dateProduct(@NotNull LocalDate start, @NotNull LocalDate end) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = using(connection, SQLDialect.POSTGRES);
            final @NotNull Result<Record3<Integer, Integer, Integer>> result = context
                    .select(
                            OVERHEAD.ORGANIZATION_ID,
                            INVOICE.PRODUCT_ID,
                            INVOICE.AMOUNT
                    )
                    .from(OVERHEAD).join(INVOICE).on(OVERHEAD.ID.eq(INVOICE.ID))
                    .where(OVERHEAD.DATE.between(start, end))
                    .fetch();

            TreeMap<Integer, ListForThePeriod> map = new TreeMap<>();
            for (Record record : result) {
                List<Integer> list = new ArrayList<>();
                Integer organizationId = record.getValue(OVERHEAD.ORGANIZATION_ID);
                Integer productId = record.getValue(INVOICE.PRODUCT_ID);
                Integer amount = record.getValue(INVOICE.AMOUNT);
                list.add(productId);
                if (map.get(organizationId) != null) {
                    map.put(organizationId, new ListForThePeriod(amount + map.get(organizationId).amount, Stream.concat(map.get(organizationId).listProductsId.stream(), list.stream())
                            .collect(Collectors.toList())));
                } else {
                    map.put(organizationId, new ListForThePeriod(amount, list));
                }
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}