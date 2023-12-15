package com.speedtech_automotive.tm;

import java.math.BigDecimal;

public class OrderDetailTm {
    String product_id;
    String productName;
    String productCode;
    BigDecimal sellingPrice;
    int quantity;
    BigDecimal discount;
    BigDecimal productTotal;

    public OrderDetailTm() {
    }

    public BigDecimal getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }

    public OrderDetailTm(String product_id, String productNameText, String productCodeText, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.product_id = product_id;
        this.productName = productNameText;
        this.productCode = productCodeText;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
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
