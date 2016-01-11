/*
 */


package com.cigarshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@IdClass(com.cigarshop.entity.LineItemKey.class)
@Entity
@Table(name = "SCEA_ORDER_LINEITEM")
@NamedQueries({
    @NamedQuery(name = "findAllLineItems",query = "SELECT l "
    + "FROM LineItem l")
    , @NamedQuery(name = "findLineItemsByOrderId", query = "SELECT l FROM LineItem l "
    + "WHERE l.order.orderId = :orderId " + "ORDER BY l.itemId")
    , @NamedQuery(name = "findLineItemById", query = "SELECT DISTINCT l FROM LineItem l "
    + "WHERE l.itemId = :itemId AND l.order.orderId = :orderId")
})
public class LineItem implements java.io.Serializable {
    private static final long serialVersionUID = 3229188813505619743L;
    private Order order;
    private Cigar cigar;
    private int itemId;
    private int quantity;

    public LineItem() {
    }

    public LineItem(
        Order order,
        int quantity,
        Cigar cigar) {
        this.order = order;
        this.itemId = order.getNextId();
        this.quantity = quantity;
        this.cigar = cigar;
    }

    @Id
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne
    public Cigar getCigar() {
        return cigar;
    }

    public void setCigar(Cigar cigar) {
        this.cigar = cigar;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "ORDERID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
