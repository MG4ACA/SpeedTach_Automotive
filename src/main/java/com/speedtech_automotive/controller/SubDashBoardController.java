package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubDashBoardController {
    public ResultSet getAllByMonth() throws SQLException, ClassNotFoundException {
        return Utils.executeQuery("SELECT MONTH(addedDate) AS month, SUM(finalTotal) AS total_sale FROM Orders GROUP BY MONTH(addedDate) ORDER BY month ASC");
    }
    public ResultSet getProductsBySoldQty() throws SQLException, ClassNotFoundException {
        return Utils.executeQuery("SELECT product_id, productName, productCode, SUM(quantity) AS total_quantity_sold FROM order_details GROUP BY product_id, productName, productCode ORDER BY total_quantity_sold DESC LIMIT 5");
    }

    public ResultSet getOverAllIncome() throws SQLException, ClassNotFoundException {
        return Utils.executeQuery("SELECT\n" +
                "    MONTH(addedDate) AS month,\n" +
                "    SUM(finalTotal) AS total_income,\n" +
                "    SUM(totalPrice) AS total_expenses\n" +
                "FROM\n" +
                "    orders\n" +
                "WHERE\n" +
                "    YEAR(addedDate) = YEAR(CURDATE())\n" +
                "GROUP BY\n" +
                "    MONTH(addedDate)\n" +
                "ORDER BY\n" +
                "    MONTH(addedDate)");
    }
}
