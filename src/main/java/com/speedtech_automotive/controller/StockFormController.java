package com.speedtech_automotive.controller;

import com.speedtech_automotive.model.Product;
import com.speedtech_automotive.model.Stock;
import com.speedtech_automotive.model.Supplier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class StockFormController {
    public Stock selectedStock;
    public Product selectedProduct;
    public Supplier selectedSupplier;
    private final  StockController stockController = new StockController();
    private final  BatchController batchController = new BatchController();
    private final  InventoryController inventoryController = new InventoryController();
    public Button btnProductClear;
    public Button btnSupplierClear;
    public ListView lvStockSearch;
    public TextField txtSearchStock;
    public Button btnStockClear;
    ArrayList<Supplier> allSuppliers;
    ArrayList<Product> allProducts;
    ArrayList<Stock> allStocks;
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
    public ListView lvSupplierSearch;
    public ListView lvProductSearch;

    Product product;
    Supplier supplier;

    public void initialize() throws SQLException, ClassNotFoundException {
        allSuppliers = batchController.getAll();
        allProducts = inventoryController.getAll();
        allStocks = stockController.getAll();

        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("company_name"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        TableColumn<Stock, Button> lastCol = (TableColumn<Stock, Button>) tblStockManage.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-border-radius:red; -fx-border-radius: 10;-fx-background-radius: 10");

            btnDelete.setOnAction(event -> {
                DeleteStock(param.getValue());
                tblStockManage.getSelectionModel().clearSelection();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        loadStocksTable();

        tblStockManage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedSupplierDetail) -> {
            if (selectedSupplierDetail != null) {
                setUpdateData((Stock) selectedSupplierDetail);
            }
        });
    }

    private void setUpdateData(Stock dto) {
        selectedStock = dto;
        product = loadProduct(dto.getProduct_id());
        setProductData(product);
        supplier = loadSupplier(dto.getSupplier_id());
        setSupplierData(supplier);
        lblSupplierName.setText(supplier.getSupplierName());
        lblCompanyName.setText(supplier.getCompanyName());
        lblProductName.setText(product.getName());
        lblProductCode.setText(product.getCode());
        txtPrice.setText(String.valueOf(dto.getPrice()));
        txtQuantity.setText(String.valueOf(dto.getQuantity()));
    }

    private Product loadProduct(String productId) {
        try {
            return inventoryController.getProductById(productId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Supplier loadSupplier(String supplierId) {
        try {
            return batchController.getBatchById(supplierId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSupplierData(Supplier dto) {
        selectedSupplier = dto;
        lblSupplierName.setText(dto.getSupplierName());
        lblCompanyName.setText(dto.getCompanyName());
    }

    private void setProductData(Product dto) {
        selectedProduct = dto;
        lblProductName.setText(dto.getName());
        lblProductCode.setText(dto.getCode());
    }

    private void DeleteStock(Stock param) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do You Won't Delete stock ..!", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getText().equals("Yes")) {
            try {
                if (stockController.deleteStockById(param.getStock_id())) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock Deleted ..!").show();
                    loadStocksTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveStockOnClick(ActionEvent actionEvent) {
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));
        int qty = Integer.parseInt(txtQuantity.getText());
        Date addedDate = Date.valueOf(LocalDate.now());

        try {
            if (selectedStock == null){
                boolean isSaved = stockController.saveStock(new Stock(selectedProduct.getProduct_id(), selectedProduct.getName(), selectedSupplier.getBatch_id(), selectedSupplier.getCompanyName(), price, qty, addedDate, true));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock added successfully..!").show();
                    clearFieldsOnClick();
                    loadStocksTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                selectedStock.setProduct_id(selectedProduct.getProduct_id());
                selectedStock.setSupplier_id(selectedSupplier.getBatch_id());
                selectedStock.setProduct_name(selectedProduct.getName());
                selectedStock.setCompany_name(selectedSupplier.getCompanyName());
                selectedStock.setPrice(new BigDecimal(txtPrice.getText()));
                selectedStock.setQuantity(Integer.parseInt(txtQuantity.getText()));

                boolean isSaved = stockController.updateStock(selectedStock);
                selectedStock = null;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock updated successfully..!").show();
                    loadStocksTable();
                    clearFieldsOnClick();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearFieldsOnClick() {
        selectedStock = null;
        txtQuantity.clear();
        txtPrice.clear();
        txtSearchProduct.clear();
        txtSearchProduct.clear();
        lblCompanyName.setText("");
        lblProductCode.setText("");
        lblProductName.setText("");
        lblSupplierName.setText("");
    }

    private void loadStocksTable() {
        tblStockManage.getItems().clear();
        try {
            allStocks = stockController.getAll();
            for (Stock dto:allStocks) {
                tblStockManage.getItems().add(
                        new Stock(
                                dto.getStock_id(),
                                dto.getProduct_id(),
                                dto.getProduct_name(),
                                dto.getSupplier_id(),
                                dto.getCompany_name(),
                                dto.getPrice(),
                                dto.getQuantity(),
                                dto.getAddedDate(),
                                dto.getStatus()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void onProductSearch(MouseEvent keyEvent) {
        lvProductSearch.setVisible(true);
        btnProductClear.setVisible(true);

        ArrayList<String> productArray = new ArrayList<>();
        for (Product product : allProducts) {
            productArray.add(product.getProduct_id() +" - "+ product.getCode()+" - "+product.getName());
        }
        lvProductSearch.getItems().clear();
        lvProductSearch.getItems().addAll(productArray);
    }

    public void onProductSelected(MouseEvent mouseEvent) {
        String[] array = lvProductSearch.getSelectionModel().getSelectedItem().toString().split("-");
        for (Product product: allProducts) {
            if (product.getProduct_id().equals(array[0].trim())){
                setProductData(product);
                break;
            }
        }
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
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

    public void onSupplierSelected(MouseEvent mouseEvent) {
        String[] array = lvSupplierSearch.getSelectionModel().getSelectedItem().toString().split("-");
        for (Supplier supplier : allSuppliers) {
            if (supplier.getBatch_id().equals(array[0].trim())){
                setSupplierData(supplier);
                break;
            }
        }
        lvSupplierSearch.setVisible(false);
        btnSupplierClear.setVisible(false);

    }

    public void onSearchSupplierKeyType(KeyEvent keyEvent) {
        ArrayList<Supplier> tempAllSupplier = allSuppliers;
        ArrayList<String> supplierArray = new ArrayList<>();
        for (Supplier supplier : tempAllSupplier) {
            if (supplier.getCompanyName().toLowerCase().contains(txtSearchSupplier.getText().toLowerCase()) || supplier.getSupplierName().toLowerCase().contains(txtSearchSupplier.getText().toLowerCase())) {
                supplierArray.add(supplier.getBatch_id() +" - "+ supplier.getCompanyName()+" - "+supplier.getSupplierName());
            }
        }
        lvSupplierSearch.getItems().clear();
        lvSupplierSearch.getItems().addAll(supplierArray);
        supplierArray.clear();
    }

    public void onSupplierSerach(MouseEvent mouseEvent) {
        lvSupplierSearch.setVisible(true);
        btnSupplierClear.setVisible(true);

        ArrayList<String> supplierArray = new ArrayList<>();
        for (Supplier supplier : allSuppliers) {
            supplierArray.add(supplier.getBatch_id() +" - "+ supplier.getCompanyName()+" - "+supplier.getSupplierName());
        }
        lvSupplierSearch.getItems().clear();
        lvSupplierSearch.getItems().addAll(supplierArray);
    }

    public void clearSearchProduct(ActionEvent actionEvent) {
        lvProductSearch.setVisible(false);
        btnProductClear.setVisible(false);
    }

    public void clearSearchSupplier(ActionEvent actionEvent) {
        lvSupplierSearch.setVisible(false);
        btnSupplierClear.setVisible(false);
    }

    public void onSearchStockKeyType(KeyEvent keyEvent) {
        ArrayList<Stock> tempAllStocks = allStocks;
        ArrayList<String> stockArray = new ArrayList<>();
        for (Stock stock : tempAllStocks) {
            if (stock.getCompany_name().toLowerCase().contains(txtSearchStock.getText().toLowerCase()) || stock.getProduct_name().toLowerCase().contains(txtSearchStock.getText().toLowerCase())) {
                stockArray.add(stock.getStock_id() + "- " + stock.getProduct_name() +" - "+ stock.getCompany_name()+" - "+ stock.getAddedDate());
            }
        }
        lvStockSearch.getItems().clear();
        lvStockSearch.getItems().addAll(stockArray);
        stockArray.clear();
    }

    public void onStockSelected(MouseEvent mouseEvent) {
        String[] array = lvStockSearch.getSelectionModel().getSelectedItem().toString().split("-");
        for (Stock stock : allStocks) {
            if (stock.getStock_id().equals(array[0].trim())){
                setUpdateData(stock);
                break;
            }
        }
        lvStockSearch.setVisible(false);
        btnStockClear.setVisible(false);
    }

    public void onStockSearch(MouseEvent mouseEvent) {
        lvStockSearch.setVisible(true);
        btnStockClear.setVisible(true);

        ArrayList<String> stockArray = new ArrayList<>();
        for (Stock stock : allStocks) {
            stockArray.add(stock.getStock_id() + "- " + stock.getProduct_name() +" - "+ stock.getCompany_name()+" - "+ stock.getAddedDate());
        }
        lvStockSearch.getItems().clear();
        lvStockSearch.getItems().addAll(stockArray);
    }

    public void clearSearchStock(ActionEvent actionEvent) {
        lvStockSearch.setVisible(false);
        btnStockClear.setVisible(false);
    }
}
