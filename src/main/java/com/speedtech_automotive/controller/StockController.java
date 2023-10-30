package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Stock;
import com.speedtech_automotive.utils.Utils;

import java.sql.SQLException;

public class StockController {
//    public ArrayList<Stock> getAll() throws SQLException, ClassNotFoundException {
//        ArrayList<Stock> supplierArrayList = new ArrayList<>();
//        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Batch");
//        while (resultSet.next()){
//            supplierArrayList.add(
//                    new Stock(
//                            resultSet.getString(1),
//                            resultSet.getString(5),
//                            resultSet.getString(4),
//                            resultSet.getInt(6),
//                            resultSet.getString(3),
//                            resultSet.getDate(2)
//                    ));
//        }
//        return supplierArrayList;
//    }

    public boolean deleteSupplierById(String supplierId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM Batch WHERE batch_id=?", supplierId);
    }

    public boolean saveStock(Stock stock) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT MAX(product_id) FROM Product");
//        while (resultSet.next()) {
//            String productId = resultSet.getString(1);
//        }

        return Utils.executeUpdate("insert into Stock values(?,?,?,?,?,?,?)",
                0,
                stock.getProduct().getProduct_id(),
                stock.getSupplier().getBatch_id(),
                stock.getPrice(),
                stock.getQuantity(),
                stock.getStatus(),
                stock.getAddedDate()
        );
    }

    public boolean updateStock(Stock stock) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Stock SET product_id=?, batch_id=?, unit_price=?, qty=?, status=?, addedDate=? WHERE stock_id=?",
                stock.getProduct().getProduct_id(),
                stock.getSupplier().getBatch_id(),
                stock.getPrice(),
                stock.getQuantity(),
                stock.getStatus(),
                stock.getAddedDate()
        );
    }
}
