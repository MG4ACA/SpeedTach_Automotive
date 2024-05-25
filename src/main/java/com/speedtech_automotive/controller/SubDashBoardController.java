package com.speedtech_automotive.controller;

import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubDashBoardController {
    public ResultSet getAllByMonth() throws SQLException, ClassNotFoundException {
        return Utils.executeQuery("SELECT MONTH(addedDate) AS month, SUM(finalTotal) AS total_sale FROM Orders GROUP BY MONTH(addedDate) ORDER BY month ASC");
    }
}
