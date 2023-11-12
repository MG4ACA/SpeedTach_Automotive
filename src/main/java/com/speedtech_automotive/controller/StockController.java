package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Stock;
import com.speedtech_automotive.model.Supplier;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockController {
    public ArrayList<Stock> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Stock> stocksArrayList = new ArrayList<>();
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Stock");
        while (resultSet.next()){
            stocksArrayList.add(
                    new Stock(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(8),
                            resultSet.getString(3),
                            resultSet.getString(9),
                            resultSet.getBigDecimal(4),
                            resultSet.getInt(5),
                            resultSet.getDate(7),
                            resultSet.getBoolean(6)
                    ));
        }
        return stocksArrayList;
    }

    public boolean deleteStockById(String stockId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM stock WHERE stock_id=?", stockId);
    }

    public boolean saveStock(Stock stock) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = Utils.executeQuery("SELECT MAX(product_id) FROM Product");
//        while (resultSet.next()) {
//            String productId = resultSet.getString(1);
//        }

        return Utils.executeUpdate("insert into Stock values(?,?,?,?,?,?,?,?,?)",
                0,
                stock.getProduct_id(),
                stock.getSupplier_id(),
                stock.getPrice(),
                stock.getQuantity(),
                stock.getStatus(),
                stock.getAddedDate(),
                stock.getProduct_name(),
                stock.getCompany_name()
        );
    }

    public boolean updateStock(Stock stock) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Stock SET product_id=?, batch_id=?, unit_price=?, qty=?, status=?, addedDate=?, product_name=?, company_name=? WHERE stock_id=?",
                stock.getProduct_id(),
                stock.getSupplier_id(),
                stock.getPrice(),
                stock.getQuantity(),
                stock.getStatus(),
                stock.getAddedDate(),
                stock.getProduct_name(),
                stock.getCompany_name(),
                stock.getStock_id()

        );
    }
}
