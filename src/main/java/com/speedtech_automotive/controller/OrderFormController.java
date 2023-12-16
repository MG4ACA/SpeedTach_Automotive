package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.*;
import com.speedtech_automotive.tm.OrderDetailTm;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.ListCell;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Pattern;

public class OrderFormController {
    public OrderDetail selectedOrderDetail;
    public Product selectedProduct;
    public ArrayList<OrderDetail> orderDetailsList;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TableColumn colProductName;
    public TableColumn colQuantity;
    public TableColumn colProductCode;
    public TableColumn colSellingPrice;
    public TableColumn colDiscount;
    public TableColumn colProductTotal;
    public TableView tblOrderedProductList;
    public TextField txtSearchProduct;
    public Text lblProductName;
    public Text lblProductCode;
    public ListView lvProductSearch;
    public Text lblSPOne;
    public Text lblSPTwo;
    public Text lblSDOne;
    public Text lblSDTwo;
    public Text lblSPThree;
    public Text lblSDThree;
    public TextField txtDiscount;
    public Text lblTotalDiscount;
    public Text lblDiscountedPrice;
    public Text lblTotalValue;
    public Text lblDateOne;
    public Text lblPriceOne;
    public Text lblQtyOne;
    public Text lblQtyTwo;
    public Text lblQtyThree;
    public Text lblOne;
    public Text lblTwo;
    public Text lblThree;
    public Text lblQuantity;
    public Button btnAddUpdateOrder;


    StockController stockController = new StockController();
    OrderController orderController = new OrderController();
    private ArrayList<Stock> allStocks;
    private ArrayList<OrderDetailTm> orderedProductList = new ArrayList<>();
    ArrayList<Product> allProducts;
    ArrayList<ProductStockJoin> joinList;
    private final InventoryController inventoryController = new InventoryController();
    public Button btnProductClear;
    private Boolean isOrderTableUpdate = false;
    ArrayList<Stock> availableStocksForAProduct = new ArrayList<>();
    BigDecimal totalPrice = new BigDecimal(0);
    BigDecimal totalDiscount = new BigDecimal(0);
    BigDecimal finalTotal = new BigDecimal(0);

    public void initialize() {
        try {
            joinList = stockController.getStockProductJoin();
            mapProductList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colProductTotal.setCellValueFactory(new PropertyValueFactory<>("productTotal"));
        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblOrderedProductList.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                deleteOrderTable(param.getValue());
                tblOrderedProductList.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        clearFieldsOnClick();
        loadOrderDetailsTable();

        tblOrderedProductList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                isOrderTableUpdate = true;
                btnAddUpdateOrder.setText("Update Order");
                setOrderDetailsUpdateData((OrderDetailTm) selectedOrderDetail);
            }
        });

    }

    private void mapProductList() {

    }

    private void loadOrderDetailsTable() {
        tblOrderedProductList.getItems().clear();
        if (orderedProductList != null) {
            tblOrderedProductList.getItems().addAll(orderedProductList);
        }
    }

    private void setProductData(Product dto) {
        selectedProduct = dto;
        lblProductName.setText(dto.getName());
        lblProductCode.setText(dto.getCode());
    }

    private void setOrderDetailsUpdateData(OrderDetailTm dto) {
        txtDiscount.setText(String.valueOf(dto.getDiscount()));
        txtPrice.setText(String.valueOf(dto.getSellingPrice()));
        txtQuantity.setText(String.valueOf(dto.getQuantity()));
        setProductData(new Product(dto.getProduct_id(), dto.getProductCode(), dto.getProductName()));
        loadStockDataByProductId(dto.getProduct_id());
    }

    private void deleteOrderTable(OrderDetailTm param) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't remove product " + param.getProductCode() + " from table..!", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getText().equals("Yes")) {
            try {
                if (orderedProductList.remove(param)) {
                    new Alert(Alert.AlertType.INFORMATION, "Product " + param.getProductCode() + " removed ..!").show();
                    loadOrderDetailsTable();
                    calculateTotals();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clearFieldsOnClick() {
        txtPrice.clear();
        txtQuantity.clear();
        txtDiscount.clear();
        allStocks = null;
        txtQuantity.setStyle(null);
        btnAddUpdateOrder.setText("Add Table");

        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
        hideAllStockLabels();
        lblProductCode.setText("--");
        lblProductName.setText("--");
    }

    public void addOrderDetailToTable(ActionEvent actionEvent) {
        BigDecimal quantity = BigDecimal.valueOf(Integer.parseInt(txtQuantity.getText()));
        int intQuantity = Integer.parseInt(txtQuantity.getText());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));
        BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(txtDiscount.getText()));
        OrderDetailTm orderDetailTm = new OrderDetailTm(selectedProduct.getProduct_id(), lblProductName.getText(), lblProductCode.getText(), price, intQuantity, discount, (price.subtract(discount)).multiply(quantity));

        tblOrderedProductList.getItems().clear();
        if (isOrderTableUpdate) {
            Iterator<OrderDetailTm> iterator = orderedProductList.iterator();
            while (iterator.hasNext()) {
                OrderDetailTm obj = iterator.next();
                if (obj.getProduct_id().equals(orderDetailTm.getProduct_id())) {
                    iterator.remove();
                }
            }
        }
        orderedProductList.add(orderDetailTm);
        tblOrderedProductList.getItems().addAll(orderedProductList);
        clearFieldsOnClick();
        isOrderTableUpdate = false;
        btnAddUpdateOrder.setText("Add Order");
        calculateTotals();

    }

    private void calculateTotals() {
        for (OrderDetailTm orderDetailTm : orderedProductList) {
            finalTotal = finalTotal.add(orderDetailTm.getProductTotal());
            totalPrice = totalPrice.add(orderDetailTm.getSellingPrice().multiply(BigDecimal.valueOf(orderDetailTm.getQuantity())));
        }
        totalDiscount = totalPrice.subtract(finalTotal);

        lblTotalValue.setText(totalPrice.toString());
        lblTotalDiscount.setText(totalDiscount.toString());
        lblDiscountedPrice.setText(finalTotal.toString());
    }

    public void onSearchProductKeyType(KeyEvent keyEvent) {
        ArrayList<Product> tempAllProducts = allProducts;
        ArrayList<String> productArray = new ArrayList<>();
        for (Product product : tempAllProducts) {
            if (product.getName().toLowerCase().contains(txtSearchProduct.getText().toLowerCase()) || product.getCode().toLowerCase().contains(txtSearchProduct.getText().toLowerCase())) {
                productArray.add(product.getProduct_id() + " - " + product.getCode() + " - " + product.getName());
            }
        }
        lvProductSearch.getItems().clear();
        lvProductSearch.getItems().addAll(productArray);
        productArray.clear();
    }

    public void onProductSearch(MouseEvent mouseEvent) {
        hideAllStockLabels();
        lvProductSearch.setVisible(true);
        btnProductClear.setVisible(true);

        try {
            allProducts = inventoryController.getAll();
            ArrayList<String> productArray = new ArrayList<>();
            for (ProductStockJoin product : joinList) {
                productArray.add(product.getProductId() + " - " + product.getCode() + " - " + product.getProductName() + "\t\t->quantity - " + product.getQty());
            }
            lvProductSearch.getItems().clear();
            lvProductSearch.getItems().addAll(productArray);
            lvProductSearch.setCellFactory(list -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setDisable(false);
                    } else {
                        setText(item);
                        if (checkQuantityAvailability(item)) {
                            setDisable(true);
                            setStyle("-fx-opacity: 0.5; -fx-text-fill: #2c2c2c;");
                            setOnMouseClicked(event -> {
                                event.consume();
                            });
                        } else {
                            setDisable(false);
                            setOnMouseClicked(event -> {
                                onProductSelected();
                            });
                        }
                    }
                }


            });
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkQuantityAvailability(String value) {
        String quantityString = value.replaceAll(".*quantity - (\\d+).*", "$1");
        int quantity = Integer.parseInt(quantityString);
        return quantity > 0 ? false : true;
    }

    public void onProductSelected() {
        String[] array = lvProductSearch.getSelectionModel().getSelectedItem().toString().split("-");
        String productId = array[0].trim();

        for (OrderDetailTm orderDetailTm : orderedProductList) {
            if (orderDetailTm.getProduct_id().equals(productId)) {
                new Alert(Alert.AlertType.WARNING, "Product already added to table, update it..!").show();
                return;
            }
        }

        for (Product product : allProducts) {
            if (product.getProduct_id().equals(productId)) {
                setProductData(product);

                loadStockDataByProductId(product.getProduct_id());
                break;
            }
        }
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
        txtPrice.requestFocus();
    }

    private void loadStockDataByProductId(String productId) {
        try {
            allStocks = stockController.getByProductId(productId);
            enableStockLabels();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void enableStockLabels() {
        lblPriceOne.setVisible(true);
        lblDateOne.setVisible(true);
        lblQuantity.setVisible(true);
        if (allStocks.size() == 1) {
            enableLabelSetOne();
        } else if (allStocks.size() == 2) {
            enableLabelSetOne();
            enableLabelSetTwo();
        } else if (allStocks.size() == 3) {
            enableLabelSetOne();
            enableLabelSetTwo();
            enableLabelSetThree();
        }
    }

    private void enableLabelSetThree() {
        lblThree.setVisible(true);
        lblSPThree.setVisible(true);
        lblSDThree.setVisible(true);
        lblQtyThree.setVisible(true);
        lblSPThree.setText(allStocks.get(2).getPrice().toString());
        lblSDThree.setText((allStocks.get(2).getAddedDate()).toString());
        lblQtyThree.setText(String.valueOf((allStocks.get(2).getQuantity())));
    }

    private void enableLabelSetTwo() {
        lblTwo.setVisible(true);
        lblSPTwo.setVisible(true);
        lblSDTwo.setVisible(true);
        lblQtyTwo.setVisible(true);
        lblSPTwo.setText(allStocks.get(1).getPrice().toString());
        lblSDTwo.setText((allStocks.get(1).getAddedDate()).toString());
        lblQtyTwo.setText(String.valueOf((allStocks.get(1).getQuantity())));
    }

    private void enableLabelSetOne() {
        lblOne.setVisible(true);
        lblSPOne.setVisible(true);
        lblSDOne.setVisible(true);
        lblQtyOne.setVisible(true);
        lblSPOne.setText(allStocks.get(0).getPrice().toString());
        lblSDOne.setText((allStocks.get(0).getAddedDate()).toString());
        lblQtyOne.setText(String.valueOf((allStocks.get(0).getQuantity())));
    }

    public void hideAllStockLabels() {
        lblPriceOne.setVisible(false);
        lblDateOne.setVisible(false);
        lblSPOne.setVisible(false);
        lblSPTwo.setVisible(false);
        lblSPThree.setVisible(false);
        lblSDOne.setVisible(false);
        lblSDTwo.setVisible(false);
        lblSDThree.setVisible(false);
        lblQtyOne.setVisible(false);
        lblQtyTwo.setVisible(false);
        lblQtyThree.setVisible(false);
        lblOne.setVisible(false);
        lblTwo.setVisible(false);
        lblThree.setVisible(false);
        lblQuantity.setVisible(false);
    }

    public void clearSearchProduct() {
        clearFieldsOnClick();
    }

    public void clearSearchStock(ActionEvent actionEvent) {

    }

    public void clearOrderDetailsTable(ActionEvent actionEvent) {
        lblTotalDiscount.setText("--");
        lblTotalValue.setText("--");
        lblDiscountedPrice.setText("--");
        clearFieldsOnClick();
    }

    public void placeOrder(ActionEvent actionEvent) {
        Date addedDate = Date.valueOf(LocalDate.now());

        try {
            String order_id = orderController.saveOrder(new Order(totalPrice, totalDiscount, finalTotal, addedDate));
            if (order_id != null){
                for (OrderDetailTm orderDetailTm : orderedProductList) {
                    availableStocksForAProduct = stockController.getAvailableStocks(orderDetailTm.getProduct_id());
                    int remainingQuantity = availableStocksForAProduct.get(0).getQuantity() - orderDetailTm.getQuantity();
                    updateStocksTable(orderDetailTm, remainingQuantity);
                    orderController.saveOrderDetail(orderDetailTm, order_id);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStocksTable(OrderDetailTm orderDetailTm, int remainingQuantity) {
        try {
            if (remainingQuantity >= 0) {
                Boolean status = stockController.updateStockQtyOnOder(remainingQuantity, orderDetailTm.getProduct_id(), availableStocksForAProduct.get(0).getAddedDate());

            } else {
                Boolean status = stockController.updateStockQtyOnOder(0, orderDetailTm.getProduct_id(), availableStocksForAProduct.get(0).getAddedDate());

                int remaining = Math.abs(remainingQuantity);
                availableStocksForAProduct.remove(0);
                updateStocksTable(orderDetailTm, remaining);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void validateQuantity(KeyEvent keyEvent) {
        if (allStocks != null && !txtQuantity.getText().equals("") && Pattern.matches("[0-9]*", txtQuantity.getText())) {
            int totalQty = 0;
            for (Stock stock : allStocks) {
                totalQty += stock.getQuantity();
            }
            if (Integer.parseInt(txtQuantity.getText()) > totalQty) {
                txtQuantity.setStyle("-fx-border-color: red;");
            } else {
                txtQuantity.setStyle(null);
            }
        }
    }


    private void updateStockQtyOnOder() {

    }
}
