/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.persistence.Transient;

@Entity
@Table(name = "SCEA_ORDER")
@NamedQueries({
    @NamedQuery(name = "Order.findAllOrders", query = "SELECT o FROM Order o ORDER BY o.orderId"),
    @NamedQuery(name = "Order.findCart", query = "SELECT o FROM Order o where o.status = :status")})
public class Order implements java.io.Serializable {
    private static final long serialVersionUID = 6582105865012174694L;
    private Collection<LineItem> lineItems;
    private Date lastUpdate;
    private Integer orderId;
    private String shipmentInfo;
    private OrderStatus status;
    //private int discount;

    public Order() {
        this.lastUpdate = new Date();
        this.lineItems = new ArrayList<LineItem>();
    }

    public Order(
        //Integer orderId,
        OrderStatus status,
        //int discount,
        String shipmentInfo) {
        this.orderId = orderId;
        this.status = status;
        //this.discount = discount;
        this.shipmentInfo = shipmentInfo;
        this.lastUpdate = new Date();
        this.lineItems = new ArrayList<LineItem>();
    }

    @TableGenerator(name = "orderGen", table = "SCEA_SEQUENCE_GENERATOR", 
            pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", 
            pkColumnValue = "ORDER_ID", allocationSize = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderGen")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Temporal(TIMESTAMP)
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
/*
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
*/
    public String getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(String shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    @OneToMany(cascade = ALL, mappedBy = "order")
    public Collection<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Collection<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public double calculateTotal() {
        double ammount = 0;
        Collection<LineItem> items = getLineItems();

        for (Iterator it = items.iterator(); it.hasNext();) {
            LineItem item = (LineItem) it.next();
            Cigar part = item.getCigar();
            ammount += (part.getPrice() * item.getQuantity());
        }

        //return (ammount * (100 - getDiscount())) / 100;
        return ammount;
    }

    public void addLineItem(LineItem lineItem) {
        this.getLineItems()
            .add(lineItem);
    }

    @Transient
    public int getNextId() {
        return this.lineItems.size() + 1;
    }
}
