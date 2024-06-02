package com.speedtech_automotive.controller;

import com.speedtech_automotive.db.DBConnection;
import com.speedtech_automotive.model.*;
import com.speedtech_automotive.tm.OrderDetailTm;
import com.speedtech_automotive.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.ListCell;

import java.math.BigDecimal;
import java.sql.Connection;
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
    public Customer selectedCustomer;
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
    public TextField txtSearchCustomer;
    public Button btnCustomerClear;
    public ListView<String> lvCustomerSearch;
    public TableColumn colCustomerName;
    public BigDecimal totalInitialCost = BigDecimal.ZERO;
    public BigDecimal orderTotalInitialCost = BigDecimal.ZERO;

    StockController stockController = new StockController();
    OrderController orderController = new OrderController();
    CustomerController customerController = new CustomerController();
    private ArrayList<Stock> allStocks;
    private ArrayList<Customer> allCustomers;
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
            allCustomers = customerController.getAll();
            joinList = stockController.getStockProductJoin();
            mapProductList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colProductTotal.setCellValueFactory(new PropertyValueFactory<>("productTotal"));
        TableColumn<OrderDetailTm, Button> lastCol = (TableColumn<OrderDetailTm, Button>) tblOrderedProductList.getColumns().get(7);

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
        btnCustomerClear.setVisible(false);
        txtSearchCustomer.clear();
    }

    public void addOrderDetailToTable(ActionEvent actionEvent) {
        BigDecimal quantity = BigDecimal.valueOf(Integer.parseInt(txtQuantity.getText()));
        int intQuantity = Integer.parseInt(txtQuantity.getText());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));
        BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(txtDiscount.getText()));
        String customerId = null;
        String customerName = null;
        if (!txtSearchCustomer.getText().isEmpty()){
            customerId = txtSearchCustomer.getText().split("-")[0];
            customerName = txtSearchCustomer.getText().split("-")[1];
        }
        OrderDetailTm orderDetailTm = new OrderDetailTm(selectedProduct.getProduct_id(),customerId, customerName, lblProductName.getText(), lblProductCode.getText(), price, intQuantity, discount, (price.subtract(discount)).multiply(quantity));

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
        finalTotal = new BigDecimal(0);
        totalDiscount = new BigDecimal(0);
        totalPrice = new BigDecimal(0);
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

    public void onProductSearch() {
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
        txtSearchProduct.clear();
        onProductSearch();
        txtSearchProduct.requestFocus();
    }

    public void clearAllFields() {
        tblOrderedProductList.getItems().clear();
        lblTotalDiscount.setText("--");
        lblTotalValue.setText("--");
        lblDiscountedPrice.setText("--");
        clearFieldsOnClick();
    }

    public void placeOrder(ActionEvent actionEvent) {
        Date addedDate = Date.valueOf(LocalDate.now());
        boolean status = false;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String customer_id = null;
            String numberPlate = null;
            if (selectedCustomer != null) {
                customer_id = selectedCustomer.getCus_id();
                numberPlate = selectedCustomer.getNumber_plate();
            }

            String order_id = orderController.saveOrder(new Order(totalPrice, customer_id, numberPlate, totalDiscount, finalTotal, addedDate));

            if (order_id != null) {
                for (OrderDetailTm orderDetailTm : orderedProductList) {
                    totalInitialCost = BigDecimal.ZERO;
                    availableStocksForAProduct = stockController.getAvailableStocks(orderDetailTm.getProduct_id());
                    int remainingQuantity = availableStocksForAProduct.get(0).getQuantity() - orderDetailTm.getQuantity();
                    status = updateStocksTable(orderDetailTm, remainingQuantity);
                    if (status){
                        status = OrderController.saveOrderDetail(orderDetailTm, order_id);
                        if (!status) {
                            throw new SQLException("Something went wrong..!!");
                        }
                    } else {
                        throw new SQLException("Something went wrong..!!");
                    }
                    orderTotalInitialCost = orderTotalInitialCost.add(totalInitialCost);
                }
                boolean updated = orderController.updateOrderInitialCost(order_id, orderTotalInitialCost);
                orderTotalInitialCost = BigDecimal.ZERO;
                if (!updated) {
                    throw new SQLException("Something went wrong..!!");
                }
                con.commit();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
                clearAllFields();
                joinList = stockController.getStockProductJoin();

            }
        } catch (Exception er) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new Alert(Alert.AlertType.ERROR, "Something went wrong..!!").show();
        } finally {
            try {
                assert con != null;
                con.setAutoCommit(true);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong..!!").show();
                throw new RuntimeException(e);
            }
        }

    }

    private Boolean updateStocksTable(OrderDetailTm orderDetailTm, int remainingQuantity) throws SQLException, ClassNotFoundException {
        Boolean status = false;
            if (remainingQuantity >= 0) {
                totalInitialCost = totalInitialCost.add(BigDecimal.valueOf(remainingQuantity).multiply(availableStocksForAProduct.get(0).getPrice()));
                status = stockController.updateStockQtyOnOder(remainingQuantity, orderDetailTm.getProduct_id(), availableStocksForAProduct.get(0).getAddedDate());
            } else {
                totalInitialCost = totalInitialCost.add(BigDecimal.valueOf(availableStocksForAProduct.get(0).getQuantity()).multiply(availableStocksForAProduct.get(0).getPrice()));
                status = stockController.updateStockQtyOnOder(0, orderDetailTm.getProduct_id(), availableStocksForAProduct.get(0).getAddedDate());
                availableStocksForAProduct.remove(0);
                remainingQuantity = Math.abs(remainingQuantity);
                if ((availableStocksForAProduct.size() > 0)){
                    remainingQuantity = availableStocksForAProduct.get(0).getQuantity() - remainingQuantity;
                }
                updateStocksTable(orderDetailTm, remainingQuantity);
            }
            return status;
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

    public void onListViewMouseExited(MouseEvent mouseEvent) {
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
    }

    public void viewOrderDetails(ActionEvent actionEvent) {
        Utils.setUi("OrderDetailsForm");
    }

    public void onSearchCustomerKeyType(KeyEvent keyEvent) {
        ArrayList<Customer> tempAllCustomers = allCustomers;
        ArrayList<String> customerArray = new ArrayList<>();
        String searchFieldText = txtSearchCustomer.getText().toLowerCase();
        for (Customer customer : tempAllCustomers) {
            if (customer.getName().toLowerCase().contains(searchFieldText) ||
                    customer.getCustomer_nic().toLowerCase().contains(searchFieldText) ||
                    customer.getNumber_plate().toLowerCase().contains(searchFieldText)) {
                selectedCustomer = customer;
                customerArray.add(customer.getCus_id() +" - "+ customer.getName()+" - "+ customer.getCustomer_nic()+" - "+customer.getNumber_plate());
            }
        }
        lvCustomerSearch.getItems().clear();
        lvCustomerSearch.getItems().addAll(customerArray);
        customerArray.clear();
    }

    public void onCustomerSearch(MouseEvent mouseEvent) {
        lvCustomerSearch.setVisible(true);
        btnCustomerClear.setVisible(true);

        ArrayList<String> customerArray = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customerArray.add(customer.getCus_id() +" - "+ customer.getName()+" - "+ customer.getCustomer_nic()+" - "+customer.getNumber_plate());
        }
        lvCustomerSearch.getItems().clear();
        lvCustomerSearch.getItems().addAll(customerArray);
    }

    public void clearSearchCustomer() {
        lvCustomerSearch.getItems().clear();
        txtSearchCustomer.clear();
    }

    public void onCustomerListViewMouseExited(MouseEvent mouseEvent) {
        lvCustomerSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }

    public void onCustomerSearchClicked(MouseEvent mouseEvent) {
        txtSearchCustomer.setText(lvCustomerSearch.getSelectionModel().getSelectedItem());
        String[] array = lvCustomerSearch.getSelectionModel().getSelectedItem().split("-");
        for (Customer customer : allCustomers) {
            if (customer.getCus_id().equals(array[0].trim())){
                selectedCustomer = customer;
                break;
            }
        }
        lvCustomerSearch.setVisible(false);
        btnCustomerClear.setVisible(false);
    }
}
