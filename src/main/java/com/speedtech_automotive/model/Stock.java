package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Stock {
    String stock_id;
    Product product;
    Supplier supplier;
    BigDecimal price;
    int quantity;
    Date addedDate;
    Boolean status;

    public Stock() {
    }

    public Stock(String stock_id, Product product, Supplier supplier, BigDecimal price, int quantity, Date addedDate, Boolean status) {
        this.stock_id = stock_id;
        this.product = product;
        this.supplier = supplier;
        this.price = price;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.status = status;
    }

    public Stock(Product product, Supplier supplier, BigDecimal price, int quantity, Date addedDate, Boolean status) {
        this.product = product;
        this.supplier = supplier;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
                ", product=" + product +
                ", supplier=" + supplier +
                ", price=" + price +
                ", quantity=" + quantity +
                ", addedDate=" + addedDate +
                ", status=" + status +
                '}';
    }
}
