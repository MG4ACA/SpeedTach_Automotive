package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {
    String order_id;
    BigDecimal totalPrice;
    BigDecimal totalDiscount;
    BigDecimal finalTotal;
    Date addedDate;

    public Order() {
    }

    public Order(BigDecimal totalPrice, BigDecimal totalDiscount, BigDecimal finalTotal, Date addedDate) {
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.finalTotal = finalTotal;
        this.addedDate = addedDate;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
