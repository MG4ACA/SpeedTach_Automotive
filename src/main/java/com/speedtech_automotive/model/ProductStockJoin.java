package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class ProductStockJoin {
    private String productId;
    private String stockId;
    private String productName;
    private String code;
    private Date addedDate;
    private int qty;
    private int status;
    private BigDecimal unitPrice;
    private String companyName;

    public ProductStockJoin() {
    }

    public ProductStockJoin(String productId, String productName, String code, int qty) {
        this.productId = productId;
        this.productName = productName;
        this.code = code;
        this.qty = qty;
    }

    public ProductStockJoin(String productId, String stockId, String productName, String code, Date addedDate, int qty, int status, BigDecimal unitPrice, String companyName) {
        this.productId = productId;
        this.stockId = stockId;
        this.productName = productName;
        this.code = code;
        this.addedDate = addedDate;
        this.qty = qty;
        this.status = status;
        this.unitPrice = unitPrice;
        this.companyName = companyName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ProductStockJoin{" +
                "productId=" + productId +
                ", stockId=" + stockId +
                ", productName='" + productName + '\'' +
                ", code='" + code + '\'' +
                ", addedDate=" + addedDate +
                ", qty=" + qty +
                ", status=" + status +
                ", unitPrice=" + unitPrice +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
