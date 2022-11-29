package ru.zulvit.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.zulvit.entity.Overhead;
import ru.zulvit.flyway.JDBCCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static generated.Tables.OVERHEAD;
import static org.jooq.impl.DSL.row;

public final class OverheadDaoImpl implements DAO<Overhead> {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    public @NotNull Optional<Overhead> findById(int id) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(OVERHEAD)
                    .where(OVERHEAD.ID.equal(id))
                    .fetch();
            Overhead overhead = new Overhead(
                    result.getValue(0, OVERHEAD.ID),
                    result.getValue(0, OVERHEAD.DATE),
                    result.getValue(0, OVERHEAD.ORGANIZATION_ID)
            );
            return Optional.of(overhead);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Overhead> getAll() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(OVERHEAD)
                    .fetch();

            List<Overhead> list = new ArrayList<>();
            for (Record record : result) {
                Integer id = record.getValue(OVERHEAD.ID);
                LocalDate date = record.getValue(OVERHEAD.DATE);
                Integer organizationId = record.getValue(OVERHEAD.ORGANIZATION_ID);
                list.add(new Overhead(id, date, organizationId));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Overhead entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            dslContext
                    .insertInto(OVERHEAD, OVERHEAD.ID, OVERHEAD.DATE, OVERHEAD.ORGANIZATION_ID)
                    .values(entity.id(), entity.date(), entity.organizationId())
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Overhead entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            dslContext
                    .update(OVERHEAD)
                    .set(
                            row(OVERHEAD.ID, OVERHEAD.DATE, OVERHEAD.ORGANIZATION_ID),
                            row(entity.id(), entity.date(), entity.organizationId()))
                    .where(OVERHEAD.ID.eq(entity.id()))
                    .execute();

            dslContext
                    .selectFrom(OVERHEAD)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Overhead entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            context
                    .delete(OVERHEAD)
                    .where(OVERHEAD.ID.equal(entity.id()))
                    .execute();
            context
                    .selectFrom(OVERHEAD)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}