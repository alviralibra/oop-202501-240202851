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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.print.PrinterJob;
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
    private String currentUsername;
    private Runnable onLogoutCallback;

    // Tab components
    private TableView<Product> productTable;
    private TableView<CartItem> cartTable;
    private Label totalLabel;
    private Label itemCountLabel;

    public PosView(PosController controller, int userId, String userRole, String username, Runnable onLogout) {
        this.controller = controller;
        this.currentUserId = userId;
        this.currentUserRole = userRole;
        this.currentUsername = username;
        this.onLogoutCallback = onLogout;
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
        VBox vbox = new VBox(0);
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #1a252f, #2c3e50);");

        // Main title bar dengan gradient
        HBox titleBar = new HBox(15);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPadding(new Insets(15, 20, 15, 20));
        titleBar.setStyle("-fx-background-color: linear-gradient(to right, #16a085, #1abc9c);");

        // Logo/Icon area
        Label logoLabel = new Label("🌾");
        logoLabel.setFont(Font.font("Arial", 32));
        
        Label titleLabel = new Label("AGRI-POS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: white;");

        Label subtitleLabel = new Label("Point of Sale System");
        subtitleLabel.setFont(Font.font("Arial", 11));
        subtitleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9);");

        VBox titleBox = new VBox(2);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        // Spacer
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // Role badge
        Label roleLabel = new Label(currentUserRole);
        roleLabel.setPadding(new Insets(6, 12, 6, 12));
        roleLabel.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 11; -fx-font-weight: bold; -fx-border-radius: 3; -fx-background-radius: 3;");

        titleBar.getChildren().addAll(logoLabel, titleBox, spacer1, roleLabel);

        // Bottom info bar
        HBox infoBar = new HBox(20);
        infoBar.setAlignment(Pos.CENTER_LEFT);
        infoBar.setPadding(new Insets(10, 20, 10, 20));
        infoBar.setStyle("-fx-background-color: #34495e;");

        // User info dengan icon
        Label userIconLabel = new Label("👤");
        userIconLabel.setFont(Font.font(14));
        
        Label userLabel = new Label(currentUsername);
        userLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        userLabel.setStyle("-fx-text-fill: #1abc9c;");

        VBox userBox = new VBox(1);
        userBox.getChildren().addAll(userLabel, new Label("User ID: " + currentUserId));
        ((Label) userBox.getChildren().get(1)).setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 10;");

        // Item count dengan icon
        Label cartIconLabel = new Label("🛒");
        cartIconLabel.setFont(Font.font(14));
        
        this.itemCountLabel = new Label("0 items");
        this.itemCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        this.itemCountLabel.setStyle("-fx-text-fill: #3498db;");

        VBox cartBox = new VBox(1);
        cartBox.getChildren().addAll(this.itemCountLabel, new Label("In Cart"));
        ((Label) cartBox.getChildren().get(1)).setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 10;");

        // Separator
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Logout button dengan style modern
        Button logoutBtn = new Button("🚪 Logout");
        logoutBtn.setStyle("-fx-padding: 8 18; -fx-font-size: 11; -fx-font-weight: bold; " +
                           "-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                           "-fx-border-radius: 3; -fx-background-radius: 3; " +
                           "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 1);");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(
                "-fx-padding: 8 18; -fx-font-size: 11; -fx-font-weight: bold; " +
                "-fx-background-color: #c0392b; -fx-text-fill: white; " +
                "-fx-border-radius: 3; -fx-background-radius: 3; " +
                "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 0, 2);"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(
                "-fx-padding: 8 18; -fx-font-size: 11; -fx-font-weight: bold; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-border-radius: 3; -fx-background-radius: 3; " +
                "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 1);"));
        logoutBtn.setOnAction(e -> handleLogout());

        infoBar.getChildren().addAll(userIconLabel, userBox, cartIconLabel, cartBox, spacer2, logoutBtn);

        vbox.getChildren().addAll(titleBar, infoBar);
        return vbox;
    }

    private void handleLogout() {
        // Konfirmasi dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Logout Confirmation");
        confirmDialog.setHeaderText("Yakin ingin logout?");
        confirmDialog.setContentText("Session Anda akan dihapus dan kembali ke login screen.");

        var result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("✓ User logged out: " + currentUsername);
            
            // Clear session data
            controller.clearCart();
            
            // Trigger callback untuk kembali ke login
            if (onLogoutCallback != null) {
                onLogoutCallback.run();
            }
        }
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

        // Tab 2.5: History Transaksi (untuk Kasir) - NEW
        if ("KASIR".equals(currentUserRole)) {
            Tab historyTab = new Tab("History Transaksi", createHistoryTab());
            tabPane.getTabs().add(historyTab);
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

        // === FORM INPUT PRODUK ===
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));
        formGrid.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");

        TextField txtCode = new TextField();
        txtCode.setPromptText("KODE: P001");
        txtCode.setPrefWidth(150);

        TextField txtName = new TextField();
        txtName.setPromptText("Nama produk");
        txtName.setPrefWidth(200);

        TextField txtCategory = new TextField();
        txtCategory.setPromptText("Kategori: Benih/Pupuk/Alat/Obat");
        txtCategory.setPrefWidth(200);

        TextField txtPrice = new TextField();
        txtPrice.setPromptText("Harga (Rp)");
        txtPrice.setPrefWidth(150);

        TextField txtStock = new TextField();
        txtStock.setPromptText("Jumlah stok");
        txtStock.setPrefWidth(150);

        formGrid.add(new Label("Kode:"), 0, 0);
        formGrid.add(txtCode, 1, 0);
        formGrid.add(new Label("Nama:"), 0, 1);
        formGrid.add(txtName, 1, 1);
        formGrid.add(new Label("Kategori:"), 0, 2);
        formGrid.add(txtCategory, 1, 2);
        formGrid.add(new Label("Harga:"), 0, 3);
        formGrid.add(txtPrice, 1, 3);
        formGrid.add(new Label("Stok:"), 0, 4);
        formGrid.add(txtStock, 1, 4);

        // === BUTTONS ===
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(5, 0, 0, 0));

        Button btnAdd = new Button("Tambah Produk");
        btnAdd.setStyle("-fx-padding: 8 15; -fx-font-size: 12;");
        btnAdd.setOnAction(e -> handleAddProduct(txtCode, txtName, txtCategory, txtPrice, txtStock, formGrid));

        Button btnEdit = new Button("Edit Produk");
        btnEdit.setStyle("-fx-padding: 8 15; -fx-font-size: 12;");
        btnEdit.setOnAction(e -> handleUpdateProduct(txtCode, txtName, txtCategory, txtPrice, txtStock, formGrid));

        Button btnDelete = new Button("Hapus Produk");
        btnDelete.setStyle("-fx-padding: 8 15; -fx-font-size: 12;");
        btnDelete.setOnAction(e -> handleDeleteProduct());

        Button btnClear = new Button("Bersihkan Form");
        btnClear.setStyle("-fx-padding: 8 15; -fx-font-size: 12;");
        btnClear.setOnAction(e -> {
            txtCode.clear();
            txtName.clear();
            txtCategory.clear();
            txtPrice.clear();
            txtStock.clear();
        });

        Button btnExport = new Button("📊 Export to CSV");
        btnExport.setStyle("-fx-padding: 8 15; -fx-font-size: 12; -fx-background-color: #16a085; -fx-text-fill: white;");
        btnExport.setOnAction(e -> handleExportStock());

        buttonBox.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnClear, btnExport);

        // === PRODUCT TABLE ===
        productTable = createProductTable();
        productTable.setOnMouseClicked(e -> {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txtCode.setText(selected.getCode());
                txtName.setText(selected.getName());
                txtCategory.setText(selected.getCategory() != null ? selected.getCategory() : "");
                txtPrice.setText(String.valueOf(selected.getPrice()));
                txtStock.setText(String.valueOf(selected.getStock()));
            }
        });
        refreshProductTable();

        Label formLabel = new Label("Form Input Produk:");
        formLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label tableLabel = new Label("Daftar Produk (Klik untuk Edit):");
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        vbox.getChildren().addAll(formLabel, formGrid, buttonBox, new Separator(), tableLabel, productTable);
        VBox.setVgrow(productTable, javafx.scene.layout.Priority.ALWAYS);
        return vbox;
    }

    private VBox createTransactionTab() {
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10));

        // Left - Product list with Add button
        VBox leftBox = new VBox(10);
        Label prodLabel = new Label("Daftar Produk:");
        prodLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        // Search/Filter Box
        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 8;");
        
        TextField txtSearchCode = new TextField();
        txtSearchCode.setPromptText("Cari kode barang...");
        txtSearchCode.setPrefWidth(150);
        
        Button btnSearch = new Button("Cari");
        btnSearch.setStyle("-fx-padding: 5 10;");
        btnSearch.setOnAction(e -> handleSearchProduct(txtSearchCode.getText()));
        
        Button btnClearSearch = new Button("Tampilkan Semua");
        btnClearSearch.setStyle("-fx-padding: 5 10;");
        btnClearSearch.setOnAction(e -> {
            txtSearchCode.clear();
            refreshProductTable();
        });
        
        searchBox.getChildren().addAll(new Label("Kode:"), txtSearchCode, btnSearch, btnClearSearch);
        
        productTable = createProductTable();
        refreshProductTable();

        // Add to Cart section
        HBox addToCartBox = new HBox(5);
        addToCartBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtAddQty = new TextField();
        txtAddQty.setPromptText("Qty");
        txtAddQty.setPrefWidth(60);
        Button btnAddCart = new Button("Tambah ke Keranjang");
        btnAddCart.setStyle("-fx-padding: 5 10;");
        btnAddCart.setOnAction(e -> handleAddProductToCart(txtAddQty));
        addToCartBox.getChildren().addAll(new Label("Qty:"), txtAddQty, btnAddCart);

        leftBox.getChildren().addAll(prodLabel, searchBox, productTable, new Separator(), addToCartBox);

        // Right - Cart & Checkout
        VBox rightBox = new VBox(10);
        rightBox.setPrefWidth(350);
        rightBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");

        Label cartLabel = new Label("Keranjang Belanja:");
        cartLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        cartTable = createCartTable();
        HBox cartButtonBox = new HBox(5);
        cartButtonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button updateButton = new Button("Update Qty");
        updateButton.setStyle("-fx-padding: 5 10;");
        updateButton.setOnAction(e -> handleUpdateCartQuantity(txtAddQty));
        
        Button removeButton = new Button("Hapus Item");
        removeButton.setStyle("-fx-padding: 5 10;");
        removeButton.setOnAction(e -> removeFromCart());
        
        Button clearButton = new Button("Kosongkan");
        clearButton.setStyle("-fx-padding: 5 10;");
        clearButton.setOnAction(e -> {
            controller.clearCart();
            refreshCartTable();
            showInfo("Keranjang telah dikosongkan");
        });
        cartButtonBox.getChildren().addAll(updateButton, removeButton, clearButton);

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

        // Title
        Label label = new Label("Laporan Penjualan & Grafik Omset:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Split: Chart (top) + Text Report (bottom)
        VBox chartBox = createSalesChart();
        
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button generateButton = new Button("📊 Generate Laporan");
        generateButton.setStyle("-fx-padding: 8 15; -fx-font-size: 12;");
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

        Button exportButton = new Button("💾 Export Laporan");
        exportButton.setStyle("-fx-padding: 8 15; -fx-font-size: 12; -fx-background-color: #3498db; -fx-text-fill: white;");
        exportButton.setOnAction(e -> handleExportReport(reportArea.getText()));

        Button printButton = new Button("🖨️ Cetak Laporan");
        printButton.setStyle("-fx-padding: 8 15; -fx-font-size: 12; -fx-background-color: #27ae60; -fx-text-fill: white;");
        printButton.setOnAction(e -> handlePrintReport(reportArea.getText()));
        
        buttonBox.getChildren().addAll(generateButton, exportButton, printButton);

        vbox.getChildren().addAll(label, chartBox, buttonBox, reportArea);
        VBox.setVgrow(reportArea, javafx.scene.layout.Priority.ALWAYS);
        return vbox;
    }

    private VBox createSalesChart() {
        VBox chartContainer = new VBox(5);
        chartContainer.setPadding(new Insets(5));
        chartContainer.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 3;");

        try {
            // Create chart
            NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel("Periode");
            xAxis.setLowerBound(0);
            xAxis.setAutoRanging(true);
            
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Omset (Rp)");
            yAxis.setAutoRanging(true);
            
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Grafik Omset Penjualan");
            lineChart.setPrefHeight(250);
            lineChart.setStyle("-fx-font: 11px Arial;");

            // Get daily sales data
            java.util.Map<String, Double> dailySales = controller.getTransactionService().getDailySalesData();
            
            if (!dailySales.isEmpty()) {
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName("Omset Harian");
                
                int index = 0;
                for (java.util.Map.Entry<String, Double> entry : dailySales.entrySet()) {
                    series.getData().add(new XYChart.Data<>(index++, entry.getValue()));
                }
                
                lineChart.getData().add(series);
            }

            chartContainer.getChildren().add(lineChart);
            
        } catch (Exception e) {
            Label errorLabel = new Label("Error loading chart: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #e74c3c;");
            chartContainer.getChildren().add(errorLabel);
        }

        return chartContainer;
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
            
            int itemCount = controller.getCartItemCount();
            int totalQty = items.stream().mapToInt(CartItem::getQuantity).sum();
            itemCountLabel.setText(String.format("Items: %d | Qty: %d", itemCount, totalQty));
        } catch (Exception e) {
            showError("Error refreshing cart: " + e.getMessage());
        }
    }

    private void handleSearchProduct(String kodeBarang) {
        try {
            if (kodeBarang == null || kodeBarang.trim().isEmpty()) {
                showError("Masukkan kode barang!");
                return;
            }

            List<Product> searchResults = controller.searchProductByCode(kodeBarang.trim());
            if (searchResults.isEmpty()) {
                showError("Produk dengan kode '" + kodeBarang + "' tidak ditemukan");
                ObservableList<Product> emptyList = FXCollections.observableArrayList();
                productTable.setItems(emptyList);
            } else {
                ObservableList<Product> data = FXCollections.observableArrayList(searchResults);
                productTable.setItems(data);
                showInfo("✓ Ditemukan " + searchResults.size() + " produk");
            }
        } catch (Exception e) {
            showError("Error searching product: " + e.getMessage());
        }
    }

    private void removeFromCart() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                controller.removeFromCart(selected.getProduct().getCode());
                refreshCartTable();
                showInfo("Item dihapus dari keranjang");
            } catch (Exception e) {
                showError("Error removing item: " + e.getMessage());
            }
        } else {
            showError("Pilih item di keranjang untuk dihapus");
        }
    }

    // ===== CART MANAGEMENT HANDLERS =====

    private void handleAddProductToCart(TextField txtQty) {
        try {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Pilih produk dari daftar terlebih dahulu!");
                return;
            }

            String qtyStr = txtQty.getText().trim();
            if (qtyStr.isEmpty()) {
                showError("Masukkan jumlah (Qty)!");
                return;
            }

            int quantity = Integer.parseInt(qtyStr);
            if (quantity <= 0) {
                showError("Jumlah harus lebih dari 0!");
                return;
            }

            if (quantity > selected.getStock()) {
                showError("Jumlah melebihi stok tersedia!\nStok: " + selected.getStock());
                return;
            }

            controller.addToCart(selected.getCode(), quantity);
            refreshCartTable();
            txtQty.clear();
            showInfo("✓ Produk ditambahkan ke keranjang");
        } catch (NumberFormatException ex) {
            showError("Jumlah harus berupa angka!");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void handleUpdateCartQuantity(TextField txtQty) {
        try {
            CartItem selected = cartTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Pilih item di keranjang untuk update!");
                return;
            }

            String qtyStr = txtQty.getText().trim();
            if (qtyStr.isEmpty()) {
                showError("Masukkan jumlah baru (Qty)!");
                return;
            }

            int newQuantity = Integer.parseInt(qtyStr);
            if (newQuantity <= 0) {
                showError("Jumlah harus lebih dari 0!");
                return;
            }

            if (newQuantity > selected.getProduct().getStock()) {
                showError("Jumlah melebihi stok tersedia!\nStok: " + selected.getProduct().getStock());
                return;
            }

            controller.updateCartItemQuantity(selected.getProduct().getCode(), newQuantity);
            refreshCartTable();
            txtQty.clear();
            showInfo("✓ Jumlah item diperbarui");
        } catch (NumberFormatException ex) {
            showError("Jumlah harus berupa angka!");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
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

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===== PRODUCT CRUD HANDLERS =====

    private void handleAddProduct(TextField txtCode, TextField txtName, TextField txtCategory,
                                   TextField txtPrice, TextField txtStock, GridPane formGrid) {
        try {
            if (txtCode.getText().isEmpty() || txtName.getText().isEmpty()) {
                showError("Kode dan Nama produk tidak boleh kosong!");
                return;
            }

            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (price < 0 || stock < 0) {
                showError("Harga dan Stok tidak boleh negatif!");
                return;
            }

            controller.addProduct(
                txtCode.getText(),
                txtName.getText(),
                txtCategory.getText(),
                price,
                stock
            );

            showInfo("Produk berhasil ditambahkan!");
            txtCode.clear();
            txtName.clear();
            txtCategory.clear();
            txtPrice.clear();
            txtStock.clear();
            refreshProductTable();
        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka!");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void handleUpdateProduct(TextField txtCode, TextField txtName, TextField txtCategory,
                                      TextField txtPrice, TextField txtStock, GridPane formGrid) {
        try {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Pilih produk di tabel untuk mengedit!");
                return;
            }

            if (txtName.getText().isEmpty()) {
                showError("Nama produk tidak boleh kosong!");
                return;
            }

            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (price < 0 || stock < 0) {
                showError("Harga dan Stok tidak boleh negatif!");
                return;
            }

            controller.updateProduct(
                selected.getCode(),
                txtName.getText(),
                txtCategory.getText(),
                price,
                stock
            );

            showInfo("Produk berhasil diperbarui!");
            refreshProductTable();
            txtCode.clear();
            txtName.clear();
            txtCategory.clear();
            txtPrice.clear();
            txtStock.clear();
        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka!");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void handleDeleteProduct() {
        try {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Pilih produk di tabel untuk menghapus!");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Konfirmasi Hapus");
            confirm.setHeaderText("Hapus Produk?");
            confirm.setContentText("Apakah Anda yakin ingin menghapus produk:\n\"" + selected.getName() + "\"?");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                controller.deleteProduct(selected.getCode());
                showInfo("Produk berhasil dihapus!");
                refreshProductTable();
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private VBox createHistoryTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // === TITLE ===
        Label titleLabel = new Label("📋 History Transaksi Anda");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // === FILTER BY DATE (Optional) ===
        HBox filterBox = new HBox(10);
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1;");

        Button btnRefresh = new Button("🔄 Refresh History");
        btnRefresh.setPrefWidth(150);
        btnRefresh.setStyle("-fx-padding: 8; -fx-font-size: 12;");
        btnRefresh.setOnAction(e -> loadTransactionHistory());

        filterBox.getChildren().addAll(
            new Label("Filter:"),
            btnRefresh
        );

        // === HISTORY TABLE ===
        TableView<com.upb.agripos.model.Transaction> historyTable = new TableView<>();
        
        TableColumn<com.upb.agripos.model.Transaction, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId())
        );
        colId.setPrefWidth(60);

        TableColumn<com.upb.agripos.model.Transaction, String> colDateTime = new TableColumn<>("Waktu Transaksi");
        colDateTime.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().getTransactionDate().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                )
            )
        );
        colDateTime.setPrefWidth(180);

        TableColumn<com.upb.agripos.model.Transaction, Double> colAmount = new TableColumn<>("Total (Rp)");
        colAmount.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTotalAmount())
        );
        colAmount.setPrefWidth(120);

        TableColumn<com.upb.agripos.model.Transaction, String> colPayment = new TableColumn<>("Metode");
        colPayment.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPaymentMethod())
        );
        colPayment.setPrefWidth(100);

        TableColumn<com.upb.agripos.model.Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPaymentStatus())
        );
        colStatus.setPrefWidth(100);

        historyTable.getColumns().addAll(colId, colDateTime, colAmount, colPayment, colStatus);
        historyTable.setPrefHeight(400);

        // === DETAIL BUTTON ===
        HBox actionBox = new HBox(10);
        actionBox.setPadding(new Insets(10));

        Button btnDetail = new Button("📄 Lihat Detail");
        btnDetail.setPrefWidth(150);
        btnDetail.setStyle("-fx-padding: 8; -fx-font-size: 12; -fx-background-color: #3498db; -fx-text-fill: white;");
        btnDetail.setOnAction(e -> showTransactionDetail(historyTable.getSelectionModel().getSelectedItem()));

        Button btnReceipt = new Button("🧾 Lihat Struk");
        btnReceipt.setPrefWidth(150);
        btnReceipt.setStyle("-fx-padding: 8; -fx-font-size: 12; -fx-background-color: #27ae60; -fx-text-fill: white;");
        btnReceipt.setOnAction(e -> showTransactionReceipt(historyTable.getSelectionModel().getSelectedItem()));

        actionBox.getChildren().addAll(btnDetail, btnReceipt);

        // === SUMMARY SECTION ===
        Label summaryLabel = new Label("Total Transaksi: 0 | Total Penjualan: Rp 0");
        summaryLabel.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-width: 1; -fx-font-size: 12; -fx-font-weight: bold;");
        this.historyTableView = historyTable;
        this.historySummaryLabel = summaryLabel;

        vbox.getChildren().addAll(
            titleLabel,
            filterBox,
            new Label("Riwayat Transaksi Penjualan:"),
            historyTable,
            actionBox,
            summaryLabel
        );

        // Load history saat tab dibuka
        loadTransactionHistory();

        return vbox;
    }

    // Components untuk history tab
    private TableView<com.upb.agripos.model.Transaction> historyTableView;
    private Label historySummaryLabel;

    private void loadTransactionHistory() {
        try {
            List<com.upb.agripos.model.Transaction> transactions = controller.getUserTransactionHistory(currentUserId);
            
            // Sort by transaction date (newest first)
            transactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
            
            ObservableList<com.upb.agripos.model.Transaction> data = FXCollections.observableArrayList(transactions);
            historyTableView.setItems(data);

            // Update summary
            double totalAmount = transactions.stream()
                .mapToDouble(com.upb.agripos.model.Transaction::getTotalAmount)
                .sum();
            
            String summary = String.format("Total Transaksi: %d | Total Penjualan: Rp %.0f", 
                transactions.size(), totalAmount);
            historySummaryLabel.setText(summary);
            
        } catch (Exception e) {
            showError("Gagal load history: " + e.getMessage());
        }
    }

    private void showTransactionDetail(com.upb.agripos.model.Transaction transaction) {
        if (transaction == null) {
            showError("Pilih transaksi terlebih dahulu!");
            return;
        }

        try {
            com.upb.agripos.model.Transaction detail = controller.getTransactionDetails(transaction.getId());
            
            StringBuilder sb = new StringBuilder();
            sb.append("ID Transaksi: ").append(detail.getId()).append("\n");
            sb.append("Waktu: ").append(detail.getTransactionDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
            sb.append("Metode: ").append(detail.getPaymentMethod()).append("\n");
            sb.append("Status: ").append(detail.getPaymentStatus()).append("\n\n");
            
            sb.append("DETAIL ITEM:\n");
            sb.append("─".repeat(50)).append("\n");
            for (com.upb.agripos.model.TransactionItem item : detail.getItems()) {
                sb.append(String.format("  Produk #%d (Qty: %d x Rp %.0f = Rp %.0f)\n",
                    item.getProductId(), 
                    item.getQuantity(), 
                    item.getUnitPrice(),
                    item.getSubtotal()
                ));
            }
            sb.append("─".repeat(50)).append("\n");
            sb.append(String.format("TOTAL: Rp %.0f", detail.getTotalAmount()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detail Transaksi #" + detail.getId());
            alert.setHeaderText(null);
            alert.setContentText(sb.toString());
            alert.showAndWait();
            
        } catch (Exception e) {
            showError("Error load detail: " + e.getMessage());
        }
    }

    private void showTransactionReceipt(com.upb.agripos.model.Transaction transaction) {
        if (transaction == null) {
            showError("Pilih transaksi terlebih dahulu!");
            return;
        }

        try {
            String receipt = controller.generateTransactionReceipt(transaction.getId());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Struk Transaksi #" + transaction.getId());
            alert.setHeaderText(null);
            
            TextArea textArea = new TextArea(receipt);
            textArea.setWrapText(false);
            textArea.setEditable(false);
            textArea.setPrefRowCount(20);
            textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11;");
            
            alert.getDialogPane().setContent(textArea);
            alert.showAndWait();
            
        } catch (Exception e) {
            showError("Error generate receipt: " + e.getMessage());
        }
    }
    private void handleExportStock() {
        try {
            List<Product> allProducts = controller.getAllProducts();
            
            if (allProducts.isEmpty()) {
                showError("Tidak ada produk untuk diexport!");
                return;
            }
            
            // File chooser untuk pilih lokasi save
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Simpan File Stok Produk");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            
            // Set default filename
            fileChooser.setInitialFileName(controller.generateDefaultStockFilename());
            
            // Show save dialog
            java.io.File file = fileChooser.showSaveDialog(new Stage());
            
            if (file != null) {
                String filename = controller.exportStockToCSV(allProducts, file.getAbsolutePath());
                showInfo("✓ Stok berhasil diexport ke:\n" + filename);
            }
            
        } catch (Exception e) {
            showError("Error export stock: " + e.getMessage());
        }
    }

    private void handleExportReport(String reportContent) {
        if (reportContent == null || reportContent.isEmpty()) {
            showError("Generate laporan terlebih dahulu!");
            return;
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Simpan Laporan Penjualan");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            
            fileChooser.setInitialFileName("laporan_penjualan_" + 
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt");
            
            java.io.File file = fileChooser.showSaveDialog(new Stage());
            
            if (file != null) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    writer.write(reportContent);
                }
                showInfo("✓ Laporan berhasil diexport ke:\n" + file.getAbsolutePath());
            }
            
        } catch (Exception e) {
            showError("Error export laporan: " + e.getMessage());
        }
    }

    private void handlePrintReport(String reportContent) {
        if (reportContent == null || reportContent.isEmpty()) {
            showError("Generate laporan terlebih dahulu!");
            return;
        }

        try {
            // Create printer job
            javafx.print.PrinterJob printerJob = javafx.print.PrinterJob.createPrinterJob();
            
            if (printerJob != null) {
                // Get window dari scene
                Window window = scene.getWindow();
                
                if (window != null && (window instanceof Stage)) {
                    Stage stage = (Stage) window;
                    
                    if (printerJob.showPrintDialog(stage)) {
                        // Create text area untuk print
                        TextArea printArea = new TextArea(reportContent);
                        printArea.setWrapText(true);
                        printArea.setPrefWidth(600);
                        printArea.setPrefHeight(800);
                        printArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11;");
                        
                        // Print the page
                        boolean success = printerJob.printPage(printArea);
                        
                        if (success) {
                            printerJob.endJob();
                            showInfo("✓ Laporan berhasil dikirim ke printer!");
                        } else {
                            showError("Gagal mengirim laporan ke printer");
                        }
                    }
                } else {
                    showError("Window tidak ditemukan - coba tutup dan buka aplikasi ulang");
                }
            } else {
                showError("Printer tidak tersedia di sistem");
            }
            
        } catch (Exception e) {
            System.err.println("Error print laporan: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            showError("Error print laporan: " + e.getMessage() + "\n\nCatatan: Pastikan printer terinstall di sistem");
        }
    }

    public Scene getScene() {

        return scene;
    }

    public void updateUI() {
        refreshProductTable();
        refreshCartTable();
    }
}
