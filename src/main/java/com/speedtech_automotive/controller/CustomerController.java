package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Customer;
import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Customer");
        while (resultSet.next()){
            customerArrayList.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    ));
        }
        return customerArrayList;
    }
    public boolean deleteCustomerById(String customerId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM Customer WHERE cus_id=?", customerId);
    }

    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT MAX(product_id) FROM Product");
//        while (resultSet.next()) {
//            String productId = resultSet.getString(1);
//        }

        return Utils.executeUpdate("insert into Customer values(?,?,?,?,?,?)",
                0,
                customer.getName(),
                customer.getContact(),
                customer.getNumber_plate(),
                customer.getAddress(),
                customer.getCustomer_nic()
        );
    }

    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Customer SET name=?, contact=?, number_plate=?, address=?, customer_nic=? WHERE cus_id=?",
                customer.getName(),
                customer.getContact(),
                customer.getNumber_plate(),
                customer.getAddress(),
                customer.getCustomer_nic(),
                customer.getCus_id()
        );
    }
//
//
//    public Product getProductById(String productId) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Product WHERE product_id=?", productId);
//        resultSet.next();
//        return new Product(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getString(5), resultSet.getDate(4));
//    }
}
