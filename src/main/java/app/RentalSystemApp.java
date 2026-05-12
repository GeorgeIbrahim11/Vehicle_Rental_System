package com.mycompany.vehicle_rental_system;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RentalSystemApp extends Application {

    // --- CORE DATA ---
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList(); // NEW: List for Manager
    private Customer currentCustomer; 

    // --- GUI SCREENS (Stacked) ---
    private StackPane rootStack;
    private VBox loginScreen;
    private BorderPane customerScreen;
    private BorderPane managerScreen;
    private VBox profileScreen;

    // --- COMPONENT REFERENCES ---
    private ComboBox<String> cmbLoginRole;
    private Label lblWelcome;
    private TableView<Vehicle> customerTable;
    private TableView<Vehicle> managerTable;
    private TableView<Customer> managerCustomerTable; // NEW: Table for Customers
    private TextField tfDays; 
    private Label lblStandardCost, lblDiscount, lblTotal, lblStatus; 
    
    // Manager Inputs
    private TextField tfNewId, tfNewModel, tfNewPrice, tfNewBrand; // Added Brand Field
    private ComboBox<String> cmbNewType;
    private CheckBox chkShowAvailable;
    private Label lblManagerStatus; // To show messages in GUI

    // Profile Labels
    private Label lblProfileName, lblProfileEmail, lblProfileScore;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initializeData(); 

        // Root Layout 
        rootStack = new StackPane();
        rootStack.setStyle("-fx-background-color: #f4f6f8;");

        // Build Screens
        createLoginScreen();
        createCustomerScreen();
        createManagerScreen();
        createProfileScreen();

        // Add to Root
        rootStack.getChildren().addAll(customerScreen, managerScreen, profileScreen, loginScreen);
        
        // Default Visibility
        showScreen(loginScreen);

        Scene scene = new Scene(rootStack, 1100, 700);
        stage.setTitle("BlueSky Rental System - Final Version");
        stage.setScene(scene);
        stage.show();
    }

    
    // LOGIN SCREEN
    
    private void createLoginScreen() {
        loginScreen = new VBox(20);
        loginScreen.setAlignment(Pos.CENTER);
        loginScreen.setStyle("-fx-background-color: #f4f6f8;");

        VBox card = new VBox(15);
        card.setMaxWidth(400);
        card.setPadding(new Insets(40));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        card.setAlignment(Pos.CENTER);

        Label title = new Label("System Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#2c3e50"));

        cmbLoginRole = new ComboBox<>();
        cmbLoginRole.getItems().addAll("Regular Customer", "VIP Customer", "Manager");
        cmbLoginRole.setPromptText("Select Identity...");
        cmbLoginRole.setMaxWidth(Double.MAX_VALUE);

        Button btnLogin = new Button("Login");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        
        btnLogin.setOnAction(e -> handleLoginLogic());

        card.getChildren().addAll(title, new Separator(), new Label("Select Role:"), cmbLoginRole, btnLogin);
        loginScreen.getChildren().add(card);
    }

    private void handleLoginLogic() {
        String role = cmbLoginRole.getValue();
        if (role == null) return;

        if (role.equals("Manager")) {
            refreshManagerTable();
            showScreen(managerScreen);
        } else {
            // Simulate Login: Pick a customer from the list or create one
            if (role.contains("VIP")) {
                currentCustomer = findOrCreateCustomer(101, "Samy (VIP)", "vip");
            } else {
                currentCustomer = findOrCreateCustomer(102, "Ramy (Regular)", "reg");
            }
            lblWelcome.setText("Welcome, " + currentCustomer.getName());
            showScreen(customerScreen);
        }
    }
    
    // Helper to keep customer list consistent
    private Customer findOrCreateCustomer(int id, String name, String type) {
        for(Customer c : customerList) {
            if(c.getId() == id) return c;
        }
        Customer newC;
        if(type.equals("vip")) newC = new VipCustomer(id, name, "samy@vip.com", "999");
        else newC = new Customer(id, name, "ramy@email.com", "111");
        
        customerList.add(newC);
        return newC;
    }

    
    // CUSTOMER DASHBOARD
    
    private void createCustomerScreen() {
        customerScreen = new BorderPane();
        customerScreen.setStyle("-fx-background-color: white;");

        // --- Top Bar ---
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");
        
        Label brand = new Label("BlueSky Rentals");
        brand.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        brand.setTextFill(Color.web("#3498db"));
        
        lblWelcome = new Label("Welcome");
        lblWelcome.setTextFill(Color.GREY);
        
        Button btnProfile = new Button("My Profile");
        btnProfile.setStyle("-fx-background-color: #ecf0f1;");
        btnProfile.setOnAction(e -> updateAndShowProfile()); 
        
        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-underline: true;");
        btnLogout.setOnAction(e -> logout());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(brand, spacer, lblWelcome, btnProfile, btnLogout);
        customerScreen.setTop(topBar);

        // --- Center: Table ---
        customerTable = new TableView<>();
        setupTable(customerTable, false);
        VBox centerBox = new VBox(10, new Label("Select a Vehicle:"), customerTable);
        centerBox.setPadding(new Insets(20));
        customerScreen.setCenter(centerBox);

        // --- Right: Live Quote Panel ---
        VBox rightBox = new VBox(15);
        rightBox.setPrefWidth(300);
        rightBox.setPadding(new Insets(20));
        rightBox.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 0 0 0 1;");

        Label qTitle = new Label("Live Quote Calculator");
        qTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        tfDays = new TextField();
        tfDays.setPromptText("Enter Days...");
        tfDays.setOnKeyReleased(e -> calculateLiveQuote()); 
        
        lblStandardCost = new Label("0.00 EGP");
        lblDiscount = new Label("-0.00 EGP");
        lblDiscount.setTextFill(Color.GREEN);
        
        lblTotal = new Label("0.00 EGP");
        lblTotal.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Button btnRent = new Button("Confirm & Rent");
        btnRent.setMaxWidth(Double.MAX_VALUE);
        btnRent.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRent.setOnAction(e -> confirmRentalLogic());
        
        lblStatus = new Label();
        lblStatus.setWrapText(true);

        rightBox.getChildren().addAll(qTitle, new Separator(), 
                new Label("Duration (Days):"), tfDays, 
                new Label("Standard:"), lblStandardCost, 
                new Label("Discount:"), lblDiscount, 
                new Separator(), new Label("TOTAL:"), lblTotal, 
                btnRent, lblStatus);
        
        customerScreen.setRight(rightBox);
        
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> calculateLiveQuote());
    }

    
    // MANAGER DASHBOARD 
    
    private void createManagerScreen() {
        managerScreen = new BorderPane();
        managerScreen.setStyle("-fx-background-color: white;");

        // Top
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c3e50;");
        Label title = new Label("Manager Dashboard");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnLogout.setOnAction(e -> logout());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(title, spacer, btnLogout);
        managerScreen.setTop(topBar);

        // --- Center: TABS for Vehicles and Customers ---
        TabPane tabs = new TabPane();
        
        // TAB 1: VEHICLES
        Tab tabVehicles = new Tab("Fleet Inventory");
        tabVehicles.setClosable(false);
        
        managerTable = new TableView<>();
        setupTable(managerTable, true); 
        
        chkShowAvailable = new CheckBox("Show Available Only");
        chkShowAvailable.setOnAction(e -> refreshManagerTable());
        
        VBox vehBox = new VBox(10, chkShowAvailable, managerTable);
        vehBox.setPadding(new Insets(15));
        tabVehicles.setContent(vehBox);
        
        // TAB 2: CUSTOMERS (NEW)
        Tab tabCustomers = new Tab("Customer Database");
        tabCustomers.setClosable(false);
        
        managerCustomerTable = new TableView<>();
        setupCustomerTable(managerCustomerTable);
        
        VBox custBox = new VBox(10, new Label("Registered Customers:"), managerCustomerTable);
        custBox.setPadding(new Insets(15));
        tabCustomers.setContent(custBox);
        
        tabs.getTabs().addAll(tabVehicles, tabCustomers);
        managerScreen.setCenter(tabs);

        // Bottom Actions (Vehicle Controls)
        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(15));
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        bottomBox.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");
        
        tfNewId = new TextField(); tfNewId.setPromptText("ID"); tfNewId.setPrefWidth(50);
        tfNewBrand = new TextField(); tfNewBrand.setPromptText("Brand"); // New
        cmbNewType = new ComboBox<>(); cmbNewType.getItems().addAll("Car", "Van", "Bike"); cmbNewType.setPromptText("Type");
        tfNewModel = new TextField(); tfNewModel.setPromptText("Model");
        tfNewPrice = new TextField(); tfNewPrice.setPromptText("Price"); tfNewPrice.setPrefWidth(70);
        
        Button btnAdd = new Button("Add");
        btnAdd.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnAdd.setOnAction(e -> addVehicleLogic());
        
        Button btnRemove = new Button("Remove");
        btnRemove.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white;");
        btnRemove.setOnAction(e -> {
            Vehicle v = managerTable.getSelectionModel().getSelectedItem();
            if(v!=null) { vehicleList.remove(v); refreshTables(); }
        });
        
        Button btnRepair = new Button("Repair");
        btnRepair.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        btnRepair.setOnAction(e -> handleRepairLogic()); // New Logic

        lblManagerStatus = new Label("");
        lblManagerStatus.setTextFill(Color.BLUE);

        bottomBox.getChildren().addAll(new Label("New:"), tfNewId, tfNewBrand, cmbNewType, tfNewModel, tfNewPrice, 
                                     btnAdd, new Separator(javafx.geometry.Orientation.VERTICAL), 
                                     btnRemove, btnRepair, lblManagerStatus);
        managerScreen.setBottom(bottomBox);
    }

    
    // PROFILE SCREEN (Overlay)
    
    private void createProfileScreen() {
        profileScreen = new VBox(20);
        profileScreen.setAlignment(Pos.CENTER);
        profileScreen.setStyle("-fx-background-color: rgba(0,0,0,0.5);"); 

        VBox card = new VBox(15);
        card.setMaxWidth(500);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #3498db; -fx-border-width: 2;");
        
        Label header = new Label("Customer Profile");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setTextFill(Color.web("#2c3e50"));

        lblProfileName = new Label();
        lblProfileEmail = new Label();
        lblProfileEmail.setTextFill(Color.GREY);
        
        lblProfileScore = new Label();
        lblProfileScore.setTextFill(Color.web("#3498db"));
        lblProfileScore.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button btnBack = new Button("Back to Dashboard");
        btnBack.setOnAction(e -> showScreen(customerScreen));

        card.getChildren().addAll(header, new Separator(), lblProfileName, lblProfileEmail, new Separator(), lblProfileScore, new Separator(), btnBack);
        profileScreen.getChildren().add(card);
    }

    private void updateAndShowProfile() {
        if (currentCustomer != null) {
            lblProfileName.setText("Name: " + currentCustomer.getName());
            lblProfileEmail.setText("Email: " + currentCustomer.getEmail());
            // Show Score using direct field access if public, or method if exists
            // Assuming field is accessible or you added the helper
            lblProfileScore.setText("Total Rent Score: " + currentCustomer.totalRent);
            showScreen(profileScreen);
        }
    }

    
    // LOGIC & CALCULATIONS
   
    private void calculateLiveQuote() {
        try {
            Vehicle v = customerTable.getSelectionModel().getSelectedItem();
            if (v != null && !tfDays.getText().isEmpty()) {
                int days = Integer.parseInt(tfDays.getText());
                
                double std = v.rentPricePerDay * days;
                double finalP = v.calculateRent(currentCustomer, days);
                
                lblStandardCost.setText(String.format("%.2f EGP", std));
                lblTotal.setText(String.format("%.2f EGP", finalP));
                lblDiscount.setText(String.format("-%.2f EGP", std - finalP));
            }
        } catch (NumberFormatException e) { }
    }

    private void confirmRentalLogic() {
        
        Vehicle v = customerTable.getSelectionModel().getSelectedItem();
        
        // Check if vehicle is selected and days field is not empty
        if (v != null && !tfDays.getText().isEmpty()) {
            
            currentCustomer.rentVehicle(v);
            
            // Make Vehicle Unavailable
            v.setAvailable(false); 
            
            // 3. UI Update
            lblStatus.setText("Booked! Your Score: " + currentCustomer.totalRent);
            lblStatus.setTextFill(Color.GREEN);
            refreshTables();
            
        } else {
            lblStatus.setText("Select vehicle & enter valid days.");
            lblStatus.setTextFill(Color.RED);
        }
    
    }

    private void addVehicleLogic() {
        try {
            // Parse ID first
            if (tfNewId.getText().isEmpty()) throw new Exception("ID cannot be empty");
            int id = Integer.parseInt(tfNewId.getText());

            // CHECK FOR DUPLICATE ID
            for (Vehicle v : vehicleList) {
                if (v.vehicleId == id) {
                    throw new DuplicateVehicleIdException("Vehicle with ID " + id + " already exists!");
                }
            }

            // Parse other fields
            if (tfNewPrice.getText().isEmpty()) throw new Exception("Price cannot be empty");
            double p = Double.parseDouble(tfNewPrice.getText());
            
            String m = tfNewModel.getText();
            String brand = tfNewBrand.getText();
            String t = cmbNewType.getValue();
            
            if (m.isEmpty() || brand.isEmpty() || t == null) {
                throw new Exception("All fields (Brand, Model, Type) must be filled.");
            }

            // Create Vehicle
            Vehicle v;
            if ("Van".equals(t)) v = new Van(id, brand, m, p, 1000, 500, 0.1);
            else if ("Bike".equals(t)) v = new Bike(id, brand, m, p, 2, 500, false, 0.05);
            else v = new Car(id, brand, m, p, 4, "Sedan", 0.2);
            
            // Add and Clear
            vehicleList.add(v);
            refreshTables();
            tfNewId.clear(); tfNewModel.clear(); tfNewPrice.clear(); tfNewBrand.clear();
            
            // Success Message (Optional)
            lblManagerStatus.setText("Vehicle Added Successfully!");
            lblManagerStatus.setTextFill(Color.GREEN);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "ID and Price must be valid numbers.");
        } catch (DuplicateVehicleIdException e) {
            showAlert("Duplicate ID Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }
    
    // Displaying message when clicking repair
    private void handleRepairLogic() {
        Vehicle v = managerTable.getSelectionModel().getSelectedItem();
        if(v != null) {
            v.performMaintenance();
            refreshTables();
            
            // Show GUI Message
            showAlert("Maintenance Status", 
                    "Maintenance performed successfully on:\n" + 
                    v.companyName + " " + v.model + 
                    "\nStatus is now: AVAILABLE");
        } else {
            lblManagerStatus.setText("Select a vehicle first!");
        }
    }

    
    // HELPERS
    
    private void showScreen(javafx.scene.Node screen) {
        loginScreen.setVisible(false);
        customerScreen.setVisible(false);
        managerScreen.setVisible(false);
        profileScreen.setVisible(false);
        screen.setVisible(true);
    }
    
    private void logout() {
        currentCustomer = null;
        tfDays.clear();
        // resetting prices after logging out as a customer or vip customer
        lblStandardCost.setText("0.00 EGP");
        lblDiscount.setText("-0.00 EGP");
        lblTotal.setText("0.00 EGP");
        lblStatus.setText("");
        showScreen(loginScreen);
    }

    private void refreshTables() {
        customerTable.refresh();
        refreshManagerTable();
        managerCustomerTable.refresh(); // Refresh customer list too
    }
    
    private void refreshManagerTable() {
         if (chkShowAvailable.isSelected()) {
            managerTable.setItems(vehicleList.filtered(v -> v.checkAvailability()));
        } else {
            managerTable.setItems(vehicleList);
        }
         managerTable.refresh();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setupTable(TableView<Vehicle> table, boolean isManager) {
        TableColumn<Vehicle, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().vehicleId));
        
        // NEW: Brand Column
        TableColumn<Vehicle, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().companyName));

        TableColumn<Vehicle, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().model));
        
        TableColumn<Vehicle, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTypeName()));
        
        TableColumn<Vehicle, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().rentPricePerDay));

        table.getColumns().addAll(idCol, brandCol, modelCol, typeCol, priceCol);
        
        if (isManager) {
             TableColumn<Vehicle, String> statusCol = new TableColumn<>("Available?");
             statusCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().checkAvailability() ? "YES" : "NO"));
             table.getColumns().add(statusCol);
        }
        
        table.setItems(vehicleList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    // Setup Customer Table
    private void setupCustomerTable(TableView<Customer> table) {
        TableColumn<Customer, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()));
        
        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        
        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEmail()));
        
        TableColumn<Customer, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue() instanceof VipCustomer ? "VIP" : "Regular"
        ));

        table.getColumns().addAll(idCol, nameCol, emailCol, typeCol);
        table.setItems(customerList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initializeData() {
        if(vehicleList.isEmpty()){
            vehicleList.add(new Car(101, "Honda", "Civic", 100.0, 4, "Sedan", 0.2));
            vehicleList.add(new Van(103, "Toyota", "HiAce", 200.0, 1000.0, 500.0, 0.1));
            vehicleList.add(new Bike(102, "Yamaha", "R1", 50.0, 2, 1000, true, 0.05));
        }
        // Initializing some customers for the Manager to see
        if(customerList.isEmpty()) {
            customerList.add(new VipCustomer(101, "Samy (VIP)", "samy@vip.com", "999"));
            customerList.add(new Customer(102, "Ramy (Regular)", "ramy@email.com", "111"));
        }
    }
}