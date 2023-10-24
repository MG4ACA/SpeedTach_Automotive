package com.speedtech_automotive.model;

import java.sql.Date;

public class Product {
    String product_id;
    String name;
    String code;
    String description;
    Date addedDate;

    public Product(String name, String code, String description, Date addedDate) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Product(String product_id, String name, String code, String description, Date addedDate) {
        this.product_id = product_id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.addedDate = addedDate;
    }

    public Product() {
    }
}
