package ru.zulvit.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.zulvit.entity.Organization;
import ru.zulvit.flyway.JDBCCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static generated.Tables.ORGANIZATIONS;
import static org.jooq.impl.DSL.row;

public final class OrganizationDaoImpl implements DAO<Organization> {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    public @NotNull Optional<Organization> findById(int inn) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(ORGANIZATIONS)
                    .where(ORGANIZATIONS.INN.equal(inn))
                    .fetch();
            Organization organization = new Organization(
                    result.getValue(0, ORGANIZATIONS.INN),
                    result.getValue(0, ORGANIZATIONS.NAME),
                    result.getValue(0, ORGANIZATIONS.CHECKING_ACCOUNT)
            );
            return Optional.of(organization);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Organization> getAll() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            final Result<Record> result = dslContext
                    .select()
                    .from(ORGANIZATIONS)
                    .fetch();

            List<Organization> list = new ArrayList<>();
            for (Record record : result) {
                Integer inn = record.getValue(ORGANIZATIONS.INN);
                String name = record.getValue(ORGANIZATIONS.NAME);
                Integer account = record.getValue(ORGANIZATIONS.CHECKING_ACCOUNT);
                list.add(new Organization(inn, name, account));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Organization entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
            dslContext
                    .insertInto(ORGANIZATIONS, ORGANIZATIONS.INN, ORGANIZATIONS.NAME, ORGANIZATIONS.CHECKING_ACCOUNT)
                    .values(entity.inn(), entity.name(), entity.checking_account())
                    .execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Organization entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
            dslContext
                    .update(ORGANIZATIONS)
                    .set(
                            row(ORGANIZATIONS.INN, ORGANIZATIONS.NAME, ORGANIZATIONS.CHECKING_ACCOUNT),
                            row(entity.inn(), entity.name(), entity.checking_account()))
                    .where(ORGANIZATIONS.INN.eq(entity.inn()))
                    .execute();

            dslContext
                    .selectFrom(ORGANIZATIONS)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Organization entity) {
        try (Connection conn = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            context
                    .delete(ORGANIZATIONS)
                    .where(ORGANIZATIONS.INN.equal(entity.inn()))
                    .execute();

            context
                    .selectFrom(ORGANIZATIONS)
                    .fetchStream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
