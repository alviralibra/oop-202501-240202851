package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ReceiptService;
import com.upb.agripos.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

/**
 * PosView - JavaFX GUI untuk Main POS Application
 * Tampilan dengan Tab untuk Produk, Keranjang, dan Laporan
 */
public class PosView {
    private Scene scene;
    private PosController controller;
    private int currentUserId;
    private String currentUserRole;

    // Tab components
    private TableView<Product> productTable;
    private TableView<CartItem> cartTable;
    private Label totalLabel;
    private Label itemCountLabel;

    public PosView(PosController controller, int userId, String userRole) {
        this.controller = controller;
        this.currentUserId = userId;
        this.currentUserRole = userRole;
        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();

        // Top - Header
        VBox headerBox = createHeader();
        root.setTop(headerBox);

        // Center - Tabbed Interface
        TabPane tabPane = createTabPane();
        root.setCenter(tabPane);

        scene = new Scene(root, 1000, 600);
    }

    private VBox createHeader() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #2c3e50;");

        Label titleLabel = new Label("AGRI-POS - Point of Sale System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");

        Label userLabel = new Label("User: " + currentUserRole + " | Total Items: ");
        itemCountLabel = new Label("0");
        userLabel.setStyle("-fx-text-fill: white;");
        itemCountLabel.setStyle("-fx-text-fill: white;");

        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().addAll(userLabel, itemCountLabel);
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(5));

        vbox.getChildren().addAll(titleLabel, topBar);
        return vbox;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Tab 1: Produk (untuk Admin)
        if ("ADMIN".equals(currentUserRole)) {
            Tab productTab = new Tab("Manajemen Produk", createProductTab());
            tabPane.getTabs().add(productTab);
        }

        // Tab 2: Transaksi (untuk Kasir)
        if ("KASIR".equals(currentUserRole)) {
            Tab transactionTab = new Tab("Transaksi Penjualan", createTransactionTab());
            tabPane.getTabs().add(transactionTab);
        }

        // Tab 3: Laporan (untuk Admin)
        if ("ADMIN".equals(currentUserRole)) {
            Tab reportTab = new Tab("Laporan", createReportTab());
            tabPane.getTabs().add(reportTab);
        }

        return tabPane;
    }

    private VBox createProductTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Product List
        productTable = createProductTable();
        refreshProductTable();

        Label label = new Label("Daftar Produk:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        vbox.getChildren().addAll(label, productTable);
        return vbox;
    }

    private VBox createTransactionTab() {
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10));

        // Left - Product list
        VBox leftBox = new VBox(10);
        Label prodLabel = new Label("Daftar Produk:");
        prodLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        productTable = createProductTable();
        refreshProductTable();
        leftBox.getChildren().addAll(prodLabel, productTable);

        // Right - Cart & Checkout
        VBox rightBox = new VBox(10);
        rightBox.setPrefWidth(300);
        rightBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");

        Label cartLabel = new Label("Keranjang Belanja:");
        cartLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        cartTable = createCartTable();
        HBox cartButtonBox = new HBox(5);
        Button removeButton = new Button("Hapus Item");
        removeButton.setOnAction(e -> removeFromCart());
        Button clearButton = new Button("Kosongkan");
        clearButton.setOnAction(e -> controller.clearCart());
        cartButtonBox.getChildren().addAll(removeButton, clearButton);

        totalLabel = new Label("Total: Rp 0");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalLabel.setStyle("-fx-text-fill: #27ae60;");

        HBox paymentBox = new HBox(5);
        ComboBox<String> paymentCombo = new ComboBox<>();
        paymentCombo.setItems(FXCollections.observableArrayList("Tunai", "E-Wallet"));
        paymentCombo.setValue("Tunai");
        Button checkoutButton = new Button("CHECKOUT");
        checkoutButton.setStyle("-fx-font-size: 12; -fx-padding: 10;");
        checkoutButton.setOnAction(e -> handleCheckout(paymentCombo.getValue()));
        paymentBox.getChildren().addAll(new Label("Metode:"), paymentCombo, checkoutButton);

        rightBox.getChildren().addAll(
            cartLabel,
            cartTable,
            cartButtonBox,
            new Separator(),
            totalLabel,
            paymentBox
        );

        HBox mainContent = new HBox(10);
        mainContent.getChildren().addAll(leftBox, rightBox);
        HBox.setHgrow(leftBox, javafx.scene.layout.Priority.ALWAYS);

        mainBox.getChildren().add(mainContent);
        return mainBox;
    }

    private VBox createReportTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Laporan Penjualan:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);

        Button generateButton = new Button("Generate Laporan");
        generateButton.setOnAction(e -> {
            try {
                TransactionService.TransactionSummary summary = controller.getTransactionService().generateSalesReport();
                String report = "=== LAPORAN PENJUALAN ===\n\n";
                report += "Total Revenue: Rp " + String.format("%.2f", summary.totalRevenue) + "\n";
                report += "Total Transactions: " + summary.totalTransactions + "\n";
                report += "Average per Transaction: Rp " + String.format("%.2f", summary.averageTransaction) + "\n";
                reportArea.setText(report);
            } catch (Exception ex) {
                reportArea.setText("Error: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(label, generateButton, reportArea);
        VBox.setVgrow(reportArea, javafx.scene.layout.Priority.ALWAYS);
        return vbox;
    }

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();

        TableColumn<Product, String> codeCol = new TableColumn<>("Kode");
        codeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCode()));
        codeCol.setPrefWidth(80);

        TableColumn<Product, String> nameCol = new TableColumn<>("Nama Produk");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);

        TableColumn<Product, String> categoryCol = new TableColumn<>("Kategori");
        categoryCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));
        categoryCol.setPrefWidth(100);

        TableColumn<Product, Double> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrice()));
        priceCol.setPrefWidth(100);

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stok");
        stockCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStock()));
        stockCol.setPrefWidth(80);

        table.getColumns().addAll(codeCol, nameCol, categoryCol, priceCol, stockCol);
        table.setPrefHeight(300);

        return table;
    }

    private TableView<CartItem> createCartTable() {
        TableView<CartItem> table = new TableView<>();

        TableColumn<CartItem, String> productCol = new TableColumn<>("Produk");
        productCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productCol.setPrefWidth(120);

        TableColumn<CartItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        qtyCol.setPrefWidth(50);

        TableColumn<CartItem, Double> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getUnitPrice()));
        priceCol.setPrefWidth(80);

        TableColumn<CartItem, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSubtotal()));
        subtotalCol.setPrefWidth(80);

        table.getColumns().addAll(productCol, qtyCol, priceCol, subtotalCol);
        table.setPrefHeight(200);

        return table;
    }

    private void refreshProductTable() {
        try {
            List<Product> products = controller.getAllProducts();
            ObservableList<Product> data = FXCollections.observableArrayList(products);
            productTable.setItems(data);
        } catch (Exception e) {
            showError("Error loading products: " + e.getMessage());
        }
    }

    private void refreshCartTable() {
        try {
            List<CartItem> items = controller.getCartItems();
            ObservableList<CartItem> data = FXCollections.observableArrayList(items);
            cartTable.setItems(data);
            
            double total = controller.getCartTotal();
            totalLabel.setText(String.format("Total: Rp %.0f", total));
            itemCountLabel.setText(String.valueOf(controller.getCartItemCount()));
        } catch (Exception e) {
            showError("Error refreshing cart: " + e.getMessage());
        }
    }

    private void removeFromCart() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                controller.removeFromCart(selected.getProduct().getCode());
                refreshCartTable();
            } catch (Exception e) {
                showError("Error removing item: " + e.getMessage());
            }
        }
    }

    private void handleCheckout(String paymentMethod) {
        try {
            if (controller.isCartEmpty()) {
                showError("Keranjang kosong");
                return;
            }

            // Set payment method
            if ("Tunai".equals(paymentMethod)) {
                controller.setPaymentMethodCash();
            } else {
                controller.setPaymentMethodEWallet("GCash");
            }

            double total = controller.getCartTotal();
            
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Checkout");
            alert.setHeaderText("Lanjutkan Checkout?");
            alert.setContentText("Total: Rp " + String.format("%.0f", total) + "\nMetode: " + paymentMethod);
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                int transactionId = controller.checkout(currentUserId, paymentMethod, total);
                
                // Show receipt
                String receipt = ReceiptService.generateReceipt(
                    controller.getTransactionService().getTransactionDetails(transactionId),
                    currentUserRole
                );
                
                Alert receiptAlert = new Alert(Alert.AlertType.INFORMATION);
                receiptAlert.setTitle("Receipt");
                receiptAlert.setHeaderText("Transaksi Berhasil - ID: " + transactionId);
                
                TextArea textArea = new TextArea(receipt);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                receiptAlert.getDialogPane().setContent(textArea);
                receiptAlert.showAndWait();
                
                refreshCartTable();
            }
        } catch (Exception e) {
            showError("Checkout gagal: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }

    public void updateUI() {
        refreshProductTable();
        refreshCartTable();
    }
}
