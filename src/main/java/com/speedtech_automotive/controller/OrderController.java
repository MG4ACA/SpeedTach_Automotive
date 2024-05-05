package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Order;
import com.speedtech_automotive.tm.OrderDetailTm;
import com.speedtech_automotive.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderController {
    public static boolean saveOrderDetail(OrderDetailTm orderDetail, String order_id) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("insert into order_details values(?,?,?,?,?,?,?,?,?)",
                0,
                order_id,
                orderDetail.getProduct_id(),
                orderDetail.getProductName(),
                orderDetail.getProductCode(),
                orderDetail.getSellingPrice(),
                orderDetail.getQuantity(),
                orderDetail.getDiscount(),
                orderDetail.getProductTotal()
        );
    }

    public String saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdateWithReturnId("insert into orders values(?,?,?,?,?,?,?)",
                0,
                order.getTotalPrice(),
                order.getTotalDiscount(),
                order.getFinalTotal(),
                order.getAddedDate(),
                order.getCustomer_id(),
                order.getNumberPlate()
        );
    }


    public ArrayList<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Orders");
        return mapOrdersResultSet(resultSet);
    }

    private ArrayList<Order> mapOrdersResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        while (resultSet.next()){
            orderArrayList.add(
                    new Order(
                            resultSet.getString(1),
                            resultSet.getBigDecimal(2),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getBigDecimal(3),
                            resultSet.getBigDecimal(4),
                            resultSet.getDate(5)
                    ));
        }
        return orderArrayList;
    }

    public boolean updateOrder(Order order) throws SQLException, ClassNotFoundException {
        return Utils.executeUpdate("UPDATE Orders SET totalPrice=?, totalDiscount=?, finalTotal=? WHERE order_id=?",
                order.getTotalPrice(),
                order.getTotalDiscount(),
                order.getFinalTotal(),
                order.getOrder_id()
        );    }

    public ArrayList<Order> getOrdersByDate(LocalDate date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = Utils.executeQuery("SELECT * FROM Orders WHERE addedDate=?", date);
        return mapOrdersResultSet(resultSet);
    }
}
