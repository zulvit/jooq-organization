/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Organizations;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrganizationsRecord extends UpdatableRecordImpl<OrganizationsRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.organizations.INN</code>.
     */
    public OrganizationsRecord setInn(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.INN</code>.
     */
    public Integer getInn() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.organizations.name</code>.
     */
    public OrganizationsRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.organizations.checking_account</code>.
     */
    public OrganizationsRecord setCheckingAccount(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.checking_account</code>.
     */
    public Integer getCheckingAccount() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Organizations.ORGANIZATIONS.INN;
    }

    @Override
    public Field<String> field2() {
        return Organizations.ORGANIZATIONS.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return Organizations.ORGANIZATIONS.CHECKING_ACCOUNT;
    }

    @Override
    public Integer component1() {
        return getInn();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Integer component3() {
        return getCheckingAccount();
    }

    @Override
    public Integer value1() {
        return getInn();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public Integer value3() {
        return getCheckingAccount();
    }

    @Override
    public OrganizationsRecord value1(Integer value) {
        setInn(value);
        return this;
    }

    @Override
    public OrganizationsRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public OrganizationsRecord value3(Integer value) {
        setCheckingAccount(value);
        return this;
    }

    @Override
    public OrganizationsRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrganizationsRecord
     */
    public OrganizationsRecord() {
        super(Organizations.ORGANIZATIONS);
    }

    /**
     * Create a detached, initialised OrganizationsRecord
     */
    public OrganizationsRecord(Integer inn, String name, Integer checkingAccount) {
        super(Organizations.ORGANIZATIONS);

        setInn(inn);
        setName(name);
        setCheckingAccount(checkingAccount);
    }

    /**
     * Create a detached, initialised OrganizationsRecord
     */
    public OrganizationsRecord(generated.tables.pojos.Organizations value) {
        super(Organizations.ORGANIZATIONS);

        if (value != null) {
            setInn(value.getInn());
            setName(value.getName());
            setCheckingAccount(value.getCheckingAccount());
        }
    }
}
