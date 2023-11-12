package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Supplier;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchController {
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Batch");
        while (resultSet.next()) {
            supplierArrayList.add(
                    new Supplier(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getDate(6)
                    ));
        }
        return supplierArrayList;
    }

    public boolean deleteSupplierById(String supplierId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM Batch WHERE batch_id=?", supplierId);
    }

    public boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT MAX(product_id) FROM Product");
//        while (resultSet.next()) {
//            String productId = resultSet.getString(1);
//        }

        return Utils.executeUpdate("insert into Batch values(?,?,?,?,?,?)",
                0,
                supplier.getAddedDate(),
                supplier.getDescription(),
                supplier.getCompanyName(),
                supplier.getSupplierName(),
                supplier.getContact()
        );
    }

    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Batch SET supplierName=?, companyName=?, contact=?, addedDate=?, description=? WHERE batch_id=?",
                supplier.getSupplierName(),
                supplier.getCompanyName(),
                supplier.getContact(),
                supplier.getAddedDate(),
                supplier.getDescription(),
                supplier.getBatch_id()

        );
    }

    public Supplier getBatchById(String supplierId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Batch WHERE batch_id=?", supplierId);
        resultSet.next();
        return new Supplier(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getDate(6));
    }
}
