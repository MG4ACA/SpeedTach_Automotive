package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class StockFormController {
    public TextField txtSearchSupplier;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TableView tblStockManage;
    public TableColumn colCompanyName;
    public TableColumn colProductName;
    public TableColumn colPrice;
    public TableColumn colQuantity;
    public TableColumn colDate;
    public TableColumn colProductCode1;
    public Text lblSupplierName;
    public Text lblCompanyName;
    public TextField txtSearchProduct;
    public Text lblProductName;
    public Text lblProductCode;

    Product product;
    Supplier supplier;

}
