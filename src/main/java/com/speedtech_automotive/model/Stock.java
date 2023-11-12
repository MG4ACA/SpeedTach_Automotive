package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Stock {
    String stock_id;
    String product_id;
    String product_name;
    String supplier_id;
    String company_name;
    BigDecimal price;
    int quantity;
    Date addedDate;
    Boolean status;

    public Stock() {
    }

    public Stock(String stock_id, String product_id, String product_name, String supplier_id, String company_name, BigDecimal price, int quantity, Date addedDate, Boolean status) {
        this.stock_id = stock_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.supplier_id = supplier_id;
        this.company_name = company_name;
        this.price = price;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.status = status;
    }

    public Stock(String product_id, String product_name, String supplier_id, String company_name, BigDecimal price, int quantity, Date addedDate, Boolean status) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.supplier_id = supplier_id;
        this.company_name = company_name;
        this.price = price;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.status = status;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stock_id='" + stock_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", supplier_id='" + supplier_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", addedDate=" + addedDate +
                ", status=" + status +
                '}';
    }
}
