package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {
    String order_id;
    BigDecimal totalPrice;
    String customer_id;
    String numberPlate;
    BigDecimal totalDiscount;
    BigDecimal finalTotal;
    Date addedDate;

    public Order(BigDecimal totalPrice, String customer_id, String numberPlate, BigDecimal totalDiscount, BigDecimal finalTotal, Date addedDate) {
        this.totalPrice = totalPrice;
        this.customer_id = customer_id;
        this.numberPlate = numberPlate;
        this.totalDiscount = totalDiscount;
        this.finalTotal = finalTotal;
        this.addedDate = addedDate;
    }

    public Order(String order_id, BigDecimal totalPrice, String customer_id, String numberPlate, BigDecimal totalDiscount, BigDecimal finalTotal, Date addedDate) {
        this.order_id = order_id;
        this.totalPrice = totalPrice;
        this.customer_id = customer_id;
        this.numberPlate = numberPlate;
        this.totalDiscount = totalDiscount;
        this.finalTotal = finalTotal;
        this.addedDate = addedDate;
    }

    public Order(BigDecimal totalPrice, BigDecimal totalDiscount, BigDecimal finalTotal, String order_id) {
        this.order_id = order_id;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.finalTotal = finalTotal;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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
