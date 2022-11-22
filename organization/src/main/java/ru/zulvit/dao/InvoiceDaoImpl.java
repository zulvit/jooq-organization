package ru.zulvit.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.zulvit.entity.Invoice;
import ru.zulvit.flyway.JDBCCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static generated.tables.Invoice.INVOICE;
import static org.jooq.impl.DSL.row;

public final class InvoiceDaoImpl implements DAO<Invoice> {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    public @NotNull Optional<Invoice> findById(int id) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(INVOICE)
                    .where(INVOICE.ID.equal(id))
                    .fetch();
            Invoice invoice = new Invoice(
                    result.getValue(0, INVOICE.ID),
                    result.getValue(0, INVOICE.OVERHEAD_ID),
                    result.getValue(0, INVOICE.PRODUCT_ID),
                    result.getValue(0, INVOICE.PRICE),
                    result.getValue(0, INVOICE.AMOUNT)
            );
            return Optional.of(invoice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Invoice> getAll() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(INVOICE)
                    .fetch();

            List<Invoice> list = new ArrayList<>();
            for (Record record : result) {
                Integer id = record.getValue(INVOICE.ID);
                Integer overheadId = record.getValue(INVOICE.OVERHEAD_ID);
                Integer productId = record.getValue(INVOICE.PRODUCT_ID);
                Double price = record.getValue(INVOICE.PRICE);
                Integer amount = record.getValue(INVOICE.AMOUNT);
                list.add(new Invoice(id, overheadId, productId, price.floatValue(), amount));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Invoice entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            dslContext
                    .insertInto(INVOICE, INVOICE.ID, INVOICE.OVERHEAD_ID, INVOICE.PRODUCT_ID, INVOICE.PRICE, INVOICE.AMOUNT)
                    .values(entity.ID(), entity.overheadId(), entity.productId(), entity.price(), entity.amount())
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Invoice entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            dslContext
                    .update(INVOICE)
                    .set(
                            row(INVOICE.OVERHEAD_ID, INVOICE.PRODUCT_ID, INVOICE.PRICE, INVOICE.AMOUNT),
                            row(entity.overheadId(), entity.productId(), entity.price(), entity.amount()))
                    .where(INVOICE.ID.eq(entity.ID()))
                    .execute();

            dslContext
                    .selectFrom(INVOICE)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Invoice entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            context
                    .delete(INVOICE)
                    .where(INVOICE.ID.equal(entity.ID()))
                    .execute();

            context
                    .selectFrom(INVOICE)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
