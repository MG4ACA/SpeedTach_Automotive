package com.speedtech_automotive.model;

import java.sql.Date;

public class Customer {
    String cus_id;
    String name;
    String contact;
    String number_plate;
    String address;
    String customer_nic;


    public Customer(String cus_id, String name, String contact, String number_plate, String address, String customer_nic) {
        this.cus_id = cus_id;
        this.name = name;
        this.contact = contact;
        this.number_plate = number_plate;
        this.address = address;
        this.customer_nic = customer_nic;
    }

    public Customer(String name, String contact, String numberPlate, String address, String customerNic) {
        this.name = name;
        this.contact = contact;
        this.number_plate = numberPlate;
        this.address = address;
        this.customer_nic = customerNic;
    }


    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer_nic() {
        return customer_nic;
    }

    public void setCustomer_nic(String customer_nic) {
        this.customer_nic = customer_nic;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cus_id='" + cus_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", number_plate='" + number_plate + '\'' +
                ", address='" + address + '\'' +
                ", customer_nic='" + customer_nic + '\'' +
                '}';
    }
}