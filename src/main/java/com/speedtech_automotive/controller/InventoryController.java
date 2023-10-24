package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class InventoryController {
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
            ArrayList<Product> productArrayList = new ArrayList<>();
            ResultSet resultSet = Utils.executeQuery("SELECT * FROM Product");
            while (resultSet.next()){
                productArrayList.add(
                        new Product(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(5),
                                resultSet.getDate(4)
                        ));
            }
            return productArrayList;
        }

    public boolean deleteProductById(String productId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM Product WHERE product_id=?", productId);
    }

    public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT MAX(product_id) FROM Product");
//        while (resultSet.next()) {
//            String productId = resultSet.getString(1);
//        }

        return Utils.executeUpdate("insert into Product values(?,?,?,?,?)",
                0,
                product.getCode(),
                product.getName(),
                product.getAddedDate(),
                product.getDescription()
        );
    }

    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Product SET code=?,name=?,addedDate=?, description=? WHERE product_id=?",
                product.getCode(),
                product.getName(),
                product.getAddedDate(),
                product.getDescription(),
                product.getProduct_id()
        );
    }
}
