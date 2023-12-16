package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Order;
import com.speedtech_automotive.tm.OrderDetailTm;
import com.speedtech_automotive.utils.Utils;

import java.sql.SQLException;

public class OrderController {
    public boolean saveOrderDetail(OrderDetailTm orderDetail, String order_id) throws SQLException, ClassNotFoundException {
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
        return Utils.executeUpdateWithReturnId("insert into orders values(?,?,?,?,?)",
                0,
                order.getTotalPrice(),
                order.getTotalDiscount(),
                order.getFinalTotal(),
                order.getAddedDate()
        );
    }
}
