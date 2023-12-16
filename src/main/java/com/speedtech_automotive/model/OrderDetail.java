package com.speedtech_automotive.model;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDetail {
    String orderDetail_id;
    String product_id;
    String order_id;
    String productName;
    String productCode;
    BigDecimal sellingPrice;
    int quantity;
    BigDecimal discount;
    BigDecimal productTotal;

    public OrderDetail() {
    }

    public OrderDetail(String orderDetail_id, String product_id, String productName, String productCode, BigDecimal sellingPrice, int quantity, BigDecimal discount, BigDecimal productTotal) {
        this.orderDetail_id = orderDetail_id;
        this.product_id = product_id;
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.productTotal = productTotal;
    }
}
