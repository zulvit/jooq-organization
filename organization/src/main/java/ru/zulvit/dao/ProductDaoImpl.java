package ru.zulvit.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.zulvit.entity.Product;
import ru.zulvit.flyway.JDBCCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static generated.Tables.PRODUCTS;
import static org.jooq.impl.DSL.row;

public final class ProductDaoImpl implements DAO<Product> {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    public @NotNull Optional<Product> findById(int id) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(PRODUCTS)
                    .where(PRODUCTS.ID.equal(id))
                    .fetch();
            Product product = new Product(
                    result.getValue(0, PRODUCTS.ID),
                    result.getValue(0, PRODUCTS.TITLE)
            );
            return Optional.of(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Product> getAll() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(PRODUCTS)
                    .fetch();

            List<Product> list = new ArrayList<>();
            for (Record record : result) {
                Integer id = record.getValue(PRODUCTS.ID);
                String name = record.getValue(PRODUCTS.TITLE);
                list.add(new Product(id, name));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Product entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            dslContext
                    .insertInto(PRODUCTS, PRODUCTS.ID, PRODUCTS.TITLE)
                    .values(entity.id(), entity.title())
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Product entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            dslContext
                    .update(PRODUCTS)
                    .set(
                            row(PRODUCTS.ID, PRODUCTS.TITLE),
                            row(entity.id(), entity.title()))
                    .where(PRODUCTS.ID.eq(entity.id()))
                    .execute();

            dslContext
                    .selectFrom(PRODUCTS)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Product entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            context
                    .delete(PRODUCTS)
                    .where(PRODUCTS.ID.equal(entity.id()))
                    .execute();
            context
                    .selectFrom(PRODUCTS)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
