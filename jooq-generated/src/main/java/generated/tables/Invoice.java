/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.InvoiceRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Invoice extends TableImpl<InvoiceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.invoice</code>
     */
    public static final Invoice INVOICE = new Invoice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InvoiceRecord> getRecordType() {
        return InvoiceRecord.class;
    }

    /**
     * The column <code>public.invoice.ID</code>.
     */
    public final TableField<InvoiceRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("nextval('\"invoice_ID_seq\"'::regclass)", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.invoice.overhead_id</code>.
     */
    public final TableField<InvoiceRecord, Integer> OVERHEAD_ID = createField(DSL.name("overhead_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.invoice.product_id</code>.
     */
    public final TableField<InvoiceRecord, Integer> PRODUCT_ID = createField(DSL.name("product_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.invoice.price</code>.
     */
    public final TableField<InvoiceRecord, Double> PRICE = createField(DSL.name("price"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.invoice.amount</code>.
     */
    public final TableField<InvoiceRecord, Integer> AMOUNT = createField(DSL.name("amount"), SQLDataType.INTEGER.nullable(false), this, "");

    private Invoice(Name alias, Table<InvoiceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Invoice(Name alias, Table<InvoiceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(String alias) {
        this(DSL.name(alias), INVOICE);
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(Name alias) {
        this(alias, INVOICE);
    }

    /**
     * Create a <code>public.invoice</code> table reference
     */
    public Invoice() {
        this(DSL.name("invoice"), null);
    }

    public <O extends Record> Invoice(Table<O> child, ForeignKey<O, InvoiceRecord> key) {
        super(child, key, INVOICE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<InvoiceRecord> getPrimaryKey() {
        return Keys.INVOICE_PKEY;
    }

    @Override
    public List<ForeignKey<InvoiceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.INVOICE__INVOICE_FK0, Keys.INVOICE__INVOICE_FK1);
    }

    private transient Overhead _overhead;
    private transient Products _products;

    /**
     * Get the implicit join path to the <code>public.overhead</code> table.
     */
    public Overhead overhead() {
        if (_overhead == null)
            _overhead = new Overhead(this, Keys.INVOICE__INVOICE_FK0);

        return _overhead;
    }

    /**
     * Get the implicit join path to the <code>public.products</code> table.
     */
    public Products products() {
        if (_products == null)
            _products = new Products(this, Keys.INVOICE__INVOICE_FK1);

        return _products;
    }

    @Override
    public Invoice as(String alias) {
        return new Invoice(DSL.name(alias), this);
    }

    @Override
    public Invoice as(Name alias) {
        return new Invoice(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(String name) {
        return new Invoice(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(Name name) {
        return new Invoice(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Double, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
