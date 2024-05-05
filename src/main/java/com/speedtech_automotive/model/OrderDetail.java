package com.speedtech_automotive.model;

import com.speedtech_automotive.tm.OrderDetailTm;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDetail extends OrderDetailTm {
    String orderDetail_id;
    String order_id;
    String product_id;
    String productName;
    String productCode;
    BigDecimal sellingPrice;
    int quantity;
    BigDecimal discount;
    BigDecimal productTotal;

    public OrderDetail() {
    }

    public OrderDetail(String orderDetail_id, String product_id, String productName, String productCode, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.orderDetail_id = orderDetail_id;
        this.product_id = product_id;
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
    }

    public OrderDetail(String orderDetail_id, String order_id, String product_id, String productName, String productCode, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.orderDetail_id = orderDetail_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
    }

    public String getOrderDetail_id() {
        return orderDetail_id;
    }

    public void setOrderDetail_id(String orderDetail_id) {
        this.orderDetail_id = orderDetail_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }
}
