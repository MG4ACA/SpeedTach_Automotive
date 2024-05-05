package com.speedtech_automotive.tm;

import java.math.BigDecimal;

public class OrderDetailTm {
    String product_id;
    String customer_id;
    String customerName;
    String productName;
    String productCode;
    BigDecimal sellingPrice;
    int quantity;
    BigDecimal discount;
    BigDecimal productTotal;

    public OrderDetailTm() {
    }

    public OrderDetailTm(String productName, String productCode, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
    }

    public BigDecimal getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }

    public OrderDetailTm(String product_id, String customerId, String customerName, String productNameText, String productCodeText, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.product_id = product_id;
        this.customer_id = customerId;
        this.customerName = customerName;
        this.productName = productNameText;
        this.productCode = productCodeText;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

}
