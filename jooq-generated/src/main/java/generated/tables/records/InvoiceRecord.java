/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Invoice;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InvoiceRecord extends UpdatableRecordImpl<InvoiceRecord> implements Record5<Integer, Integer, Integer, Double, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.invoice.ID</code>.
     */
    public InvoiceRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.invoice.overhead_id</code>.
     */
    public InvoiceRecord setOverheadId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice.overhead_id</code>.
     */
    public Integer getOverheadId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.invoice.product_id</code>.
     */
    public InvoiceRecord setProductId(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice.product_id</code>.
     */
    public Integer getProductId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.invoice.price</code>.
     */
    public InvoiceRecord setPrice(Double value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice.price</code>.
     */
    public Double getPrice() {
        return (Double) get(3);
    }

    /**
     * Setter for <code>public.invoice.amount</code>.
     */
    public InvoiceRecord setAmount(Integer value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice.amount</code>.
     */
    public Integer getAmount() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Double, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, Integer, Integer, Double, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Invoice.INVOICE.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Invoice.INVOICE.OVERHEAD_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Invoice.INVOICE.PRODUCT_ID;
    }

    @Override
    public Field<Double> field4() {
        return Invoice.INVOICE.PRICE;
    }

    @Override
    public Field<Integer> field5() {
        return Invoice.INVOICE.AMOUNT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getOverheadId();
    }

    @Override
    public Integer component3() {
        return getProductId();
    }

    @Override
    public Double component4() {
        return getPrice();
    }

    @Override
    public Integer component5() {
        return getAmount();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getOverheadId();
    }

    @Override
    public Integer value3() {
        return getProductId();
    }

    @Override
    public Double value4() {
        return getPrice();
    }

    @Override
    public Integer value5() {
        return getAmount();
    }

    @Override
    public InvoiceRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public InvoiceRecord value2(Integer value) {
        setOverheadId(value);
        return this;
    }

    @Override
    public InvoiceRecord value3(Integer value) {
        setProductId(value);
        return this;
    }

    @Override
    public InvoiceRecord value4(Double value) {
        setPrice(value);
        return this;
    }

    @Override
    public InvoiceRecord value5(Integer value) {
        setAmount(value);
        return this;
    }

    @Override
    public InvoiceRecord values(Integer value1, Integer value2, Integer value3, Double value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InvoiceRecord
     */
    public InvoiceRecord() {
        super(Invoice.INVOICE);
    }

    /**
     * Create a detached, initialised InvoiceRecord
     */
    public InvoiceRecord(Integer id, Integer overheadId, Integer productId, Double price, Integer amount) {
        super(Invoice.INVOICE);

        setId(id);
        setOverheadId(overheadId);
        setProductId(productId);
        setPrice(price);
        setAmount(amount);
    }

    /**
     * Create a detached, initialised InvoiceRecord
     */
    public InvoiceRecord(generated.tables.pojos.Invoice value) {
        super(Invoice.INVOICE);

        if (value != null) {
            setId(value.getId());
            setOverheadId(value.getOverheadId());
            setProductId(value.getProductId());
            setPrice(value.getPrice());
            setAmount(value.getAmount());
        }
    }
}