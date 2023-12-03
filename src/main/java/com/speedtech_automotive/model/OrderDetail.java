package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDetail {
    String orderDetail_id;
    String companyName;
    String productName;
    BigDecimal productPrice;
    BigDecimal discount;
    int  quantity;
    Date addedDate;

    public OrderDetail() {
    }

    public OrderDetail(String orderDetail_id, String companyName, String productName, BigDecimal productPrice, BigDecimal discount, int quantity, Date addedDate) {
        this.orderDetail_id = orderDetail_id;
        this.companyName = companyName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.discount = discount;
        this.quantity = quantity;
        this.addedDate = addedDate;
    }

    public String getOrderDetail_id() {
        return orderDetail_id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setOrderDetail_id(String orderDetail_id) {
        this.orderDetail_id = orderDetail_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetail_id='" + orderDetail_id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", addedDate=" + addedDate +
                '}';
    }
}
