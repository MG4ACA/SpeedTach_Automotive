package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.OrderDetail;
import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.ProductStockJoin;
import com.speedtech_automotive.model.Stock;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.ListCell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderFormController {
    public OrderDetail selectedOrderDetail;
    public ArrayList<OrderDetail> orderDetailsList;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TableColumn colCompanyName;
    public TableColumn colProductName;
    public TableColumn colPrice;
    public TableColumn colQuantity;
    public TableColumn colDate;
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
    public Text lblDateThree;
    public Text lblPriceThree;
    public Text lblDateTwo;
    public Text lblDateOne;
    public Text lblPriceTwo;
    public Text lblPriceOne;
    public Text lblQtyOne;
    public Text lblQtyTwo;
    public Text lblQtyThree;
    public Text lblOne;
    public Text lblTwo;
    public Text lblThree;
    public Text lblQuantity;

    StockController stockController = new StockController();
    private ArrayList<Stock> allStocks;
    ArrayList<Product> allProducts;
    ArrayList<ProductStockJoin> joinList;
    private final InventoryController inventoryController = new InventoryController();
    public Button btnProductClear;

    public void initialize() {
        try {
            joinList = stockController.getStockProductJoin();
            mapProductList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<OrderDetail, Button> lastCol = (TableColumn<OrderDetail, Button>) tblOrderedProductList.getColumns().get(5);

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
                setOrderDetailsUpdateData((OrderDetail) selectedOrderDetail);
            }
        });
    }

    private void mapProductList() {

    }

    private void loadOrderDetailsTable() {
        tblOrderedProductList.getItems().clear();
        if (orderDetailsList != null) {
            for (OrderDetail dto : orderDetailsList) {
                tblOrderedProductList.getItems().add(dto);
            }
        }

    }

    private void setProductData(Product dto) {
        lblProductName.setText(dto.getName());
        lblProductCode.setText(dto.getCode());
    }

    private void setOrderDetailsUpdateData(OrderDetail dto) {
//        txtDiscount.setText(String.valueOf(dto.get()));
//        txtPrice.setText(String.valueOf(dto.getProductPrice()));
//        txtQuantity.setText(String.valueOf(dto.getQuantity()));
    }

    //delete order  detail from the table
    private void deleteOrderTable(OrderDetail value) {

    }

    public void clearFieldsOnClick() {
    }

    public void addOrderDetailToTable(ActionEvent actionEvent) {
    }

    public void onSearchProductKeyType(KeyEvent keyEvent) {
        ArrayList<Product> tempAllProducts = allProducts;
        ArrayList<String> productArray = new ArrayList<>();
        for (Product product : tempAllProducts) {
            if (product.getName().toLowerCase().contains(txtSearchProduct.getText().toLowerCase()) || product.getCode().toLowerCase().contains(txtSearchProduct.getText().toLowerCase())) {
                productArray.add(product.getProduct_id() +" - "+product.getCode()+" - "+product.getName());
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
                productArray.add(product.getProductId() +" - "+ product.getCode()+" - "+product.getProductName()+"\t\t->quantity - " +product.getQty() );
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
        return quantity > 0 ? false:true ;
    }
    public void onProductSelected() {
        String[] array = lvProductSearch.getSelectionModel().getSelectedItem().toString().split("-");
        for (Product product: allProducts) {
            if (product.getProduct_id().equals(array[0].trim())){
                setProductData(product);
                loadStockData(product);
                break;
            }
        }
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
    }

    private void loadStockData(Product product) {
        try {
            allStocks = stockController.getByProductId(product.getProduct_id());
            enableStockLabels();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void enableStockLabels() {
        lblPriceOne.setVisible(true);
        lblDateOne.setVisible(true);
        lblQuantity.setVisible(true);
        if (allStocks.size() == 1){
            enableLabelSetOne();
        } else if (allStocks.size() == 2){
            enableLabelSetOne();
            enableLabelSetTwo();
        } else if (allStocks.size() == 3){
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


    public  void hideAllStockLabels(){
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
    public void clearSearchProduct(ActionEvent actionEvent) {
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
        hideAllStockLabels();
    }

    public void clearSearchStock(ActionEvent actionEvent) {

    }

    public void clearOrderDetailsTable(ActionEvent actionEvent) {
    }

    public void placeOrder(ActionEvent actionEvent) {
    }

}
