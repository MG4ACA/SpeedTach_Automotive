package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.OrderDetail;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsController {
    public ArrayList<OrderDetail> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM order_details");
        return mapOrdersResultSet(resultSet);
    }

    private ArrayList<OrderDetail> mapOrdersResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<OrderDetail> orderArrayList = new ArrayList<>();
        while (resultSet.next()) {
            orderArrayList.add(
                    new OrderDetail(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getBigDecimal(6),
                            resultSet.getInt(7),
                            resultSet.getBigDecimal(8),
                            resultSet.getBigDecimal(9)
                    ));
        }
        return orderArrayList;
    }

    public boolean deleteOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("DELETE FROM order_details WHERE order_id=?", orderId);

    }
}