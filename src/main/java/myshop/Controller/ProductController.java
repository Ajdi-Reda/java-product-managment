package myshop.Controller;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import myshop.Model.Product;
import myshop.Model.ProductDAO;

public class ProductController {
    @FXML
    private TextField idField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField designationField;
    @FXML
    private TextField quantityField;
    @FXML
    private ListView<String> productList;
    @FXML
    private Button deleteProdBtn;
    @FXML
    private Button updateProdBtn;
    @FXML
    private Button achatProdBtn;
    @FXML
    private Button venteProdBtn;
    @FXML
    private Button addProdBtn;
    @FXML
    private Button updateView;

    private ObservableList<Product> prodList = ProductDAO.getProductsList();

    @FXML
    private void initialize() {
        productList.getItems().clear();
        for (Product product : prodList) {
            productList.getItems().addAll(product.getDesignation());
        }
    }

    @FXML
    public void onClickProduct() {
        String selectedItem = productList.getSelectionModel().getSelectedItem();
        for (Product product : prodList) {
            if (product.getDesignation().equals(selectedItem)) {
                idField.setText(String.valueOf(product.getId()));
                designationField.setText((product.getDesignation()));
                priceField.setText(String.valueOf(product.getPrice()));
                quantityField.setText(String.valueOf(product.getQuantity()));
            }
            ;
        }
    }

    @FXML
    public void onDeleteProd() throws SQLException {
        try {
            int id = Integer.parseInt(idField.getText());
            ProductDAO.deleteProd(id);
            updateView();
            initialize();
            alert(AlertType.CONFIRMATION, "Product deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting product " + e);
            throw e;
        }
    }

    @FXML
    public void onAchatProd() throws SQLException {
        try {
            int id = Integer.parseInt(idField.getText());
            ProductDAO.achatVenteProd(id, "achat");
            updateView();
        } catch (Exception e) {
            System.out.println("Error executing operation achat " + e);
            throw e;
        }
    }

    @FXML
    public void onVenteProd() throws SQLException {
        try {
            int id = Integer.parseInt(idField.getText());
            ProductDAO.achatVenteProd(id, "vente");
            updateView();
        } catch (Exception e) {
            System.out.println("Error executing operation vente " + e);
            throw e;
        }
    }

    @FXML
    public void onAddProd() throws Exception {
        if (addProdBtn.getText().equals("Add Product")) {
            clearFields();
            addProdBtn.setText("Save");
            idField.setDisable(true);
            return;
        }
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            String designation = designationField.getText();
            double price = Double.parseDouble(priceField.getText());
            ProductDAO.insertProd(designation, price, quantity);
            addProdBtn.setText("Add Product");
            clearFields();
            updateView();
            initialize();
            alert(AlertType.CONFIRMATION, "Product added successfully");
            idField.setDisable(false);
        } catch (Exception e) {
            System.out.println("Error inserting product " + e);
            throw e;
        }
    }

    @FXML
    public void onUpdateProd() throws Exception {
        try {
            int id = Integer.parseInt(idField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String designation = designationField.getText();
            double price = Double.parseDouble(priceField.getText());
            ProductDAO.updateProd(id, designation, price, quantity);
            updateView();
            initialize();
            alert(AlertType.CONFIRMATION, "Product updated successfully");

        } catch (Exception e) {
            System.out.println("Error inserting product " + e);
            throw e;
        }
    }

    public void updateView() {
        prodList = ProductDAO.getProductsList();
        onClickProduct();
    }

    public void clearFields() {
        idField.clear();
        designationField.clear();
        priceField.clear();
        quantityField.clear();
    }

    public void alert(AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

}
