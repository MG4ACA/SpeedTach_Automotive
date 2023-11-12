package com.speedtech_automotive.model;

import java.sql.Date;

public class Supplier {
    String batch_id;
    String supplierName;
    String companyName;
    int contact;
    String description;
    Date addedDate;

    public Supplier(String supplierName, String companyName, int contact, String description, Date addedDate) {
        this.supplierName = supplierName;
        this.companyName = companyName;
        this.contact = contact;
        this.description = description;
        this.addedDate = addedDate;
    }

    public Supplier() {
    }

    public Supplier(String batch_id, String supplierName, String companyName, int contact, String description, Date addedDate) {
        this.batch_id = batch_id;
        this.supplierName = supplierName;
        this.companyName = companyName;
        this.contact = contact;
        this.description = description;
        this.addedDate = addedDate;
    }

    public Supplier(String batch_id, String companyName) {
        this.batch_id = batch_id;
        this.companyName = companyName;
    }

    public Supplier(int anInt, String string) {
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "batch_id='" + batch_id + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contact=" + contact +
                ", description='" + description + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
