/*
 */


package com.cigarshop.entity;

import java.io.Serializable;


public class LineItemKey implements Serializable {
    private static final long serialVersionUID = 1562260205094677677L;
    private Integer order;
    private int itemId;

    public LineItemKey() {
    }

    public LineItemKey(
        Integer order,
        int itemId) 
    {
        this.setOrder(order);
        this.setItemId(itemId);
    }

    @Override
    public int hashCode() 
    {
        return (((this.getOrder() == null) ? 0 : this.getOrder()
                                                     .hashCode())
        ^ ((int) this.getItemId()));
    }

    @Override
    public boolean equals(Object otherOb) 
    {
        if (this == otherOb) {
            return true;
        }

        if (!(otherOb instanceof LineItemKey)) {
            return false;
        }

        LineItemKey other = (LineItemKey) otherOb;

        return (((this.getOrder() == null) ? (other.getOrder() == null)
                                           : this.getOrder()
                                                 .equals(other.getOrder()))
        && (this.getItemId() == other.getItemId()));
    }

    @Override
    public String toString() {
        return "" + getOrder() + "-" + getItemId();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
