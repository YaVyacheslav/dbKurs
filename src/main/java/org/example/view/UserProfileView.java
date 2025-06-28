package org.example.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.example.model.UserProfile;
import org.example.model.Order;

import java.sql.Date;
import java.sql.SQLException;

public class UserProfileView {
    private UserProfile model;

    private final TextField surnameField    = new TextField();
    private final TextField nameField       = new TextField();
    private final TextField patronymicField = new TextField();
    private final TextField phoneField      = new TextField();
    private final TextField usernameField   = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button saveProfileButton  = new Button("Сохранить данные");
    private final TableView<Order> ordersTable = new TableView<>();

    private final ComboBox<org.example.model.ServiceType> typeCombo   = new ComboBox<>();
    private final ComboBox<org.example.model.Branch>      branchCombo = new ComboBox<>();
    private final DatePicker                              datePicker   = new DatePicker();
    private final Spinner<Integer>                        complexitySpinner = new Spinner<>(1,10,1);
    private final Button                                  createOrderButton = new Button("Оформить заказ");

    private final VBox root = new VBox(20);

    public UserProfileView(UserProfile model) {
        this.model = model;
        root.setPadding(new Insets(20));

        try {
            model.loadServiceTypes();
            model.loadBranches();
            model.loadOrders();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadComboBoxData();

        GridPane profileGrid = createProfileGrid();
        GridPane orderGrid = createOrderGrid();

        root.getChildren().addAll(
                new TitledPane("Личные данные", profileGrid),
                new TitledPane("Мои заказы", ordersTable),
                new TitledPane("Новый заказ", orderGrid)
        );

        saveProfileButton.getStyleClass().add("button");
        createOrderButton.getStyleClass().add("button");

        setupOrderTableColumns();
        ordersTable.setPlaceholder(new Label("Нет заказов"));
    }

    private void setupOrderTableColumns() {
        TableColumn<Order, String> serviceNameColumn = new TableColumn<>("Услуга");
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Order, String> branchAddressColumn = new TableColumn<>("Филиал");
        branchAddressColumn.setCellValueFactory(new PropertyValueFactory<>("branchAddress"));

        TableColumn<Order, Date> acceptanceDateColumn = new TableColumn<>("Дата приёма");
        acceptanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceDate"));

        TableColumn<Order, Date> returnDateColumn = new TableColumn<>("Дата возврата");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<Order, Integer> priceColumn = new TableColumn<>("Цена");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Order, Integer> discountColumn = new TableColumn<>("Скидка");
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        ordersTable.getColumns().addAll(serviceNameColumn, branchAddressColumn, acceptanceDateColumn, returnDateColumn, priceColumn, discountColumn);
    }

    private GridPane createProfileGrid() {
        GridPane profileGrid = new GridPane();
        profileGrid.setHgap(10);
        profileGrid.setVgap(10);
        profileGrid.add(new Label("Фамилия:"),    0, 0); profileGrid.add(surnameField,    1, 0);
        profileGrid.add(new Label("Имя:"),        0, 1); profileGrid.add(nameField,       1, 1);
        profileGrid.add(new Label("Отчество:"),   0, 2); profileGrid.add(patronymicField, 1, 2);
        profileGrid.add(new Label("Телефон:"),    0, 3); profileGrid.add(phoneField,      1, 3);
        profileGrid.add(new Label("Логин:"),      2, 0); profileGrid.add(usernameField,    3, 0);
        profileGrid.add(new Label("Пароль:"),     2, 1); profileGrid.add(passwordField,    3, 1);
        profileGrid.add(saveProfileButton,        1, 6);
        return profileGrid;
    }

    private GridPane createOrderGrid() {
        GridPane orderGrid = new GridPane();
        orderGrid.setHgap(10);
        orderGrid.setVgap(10);

        orderGrid.add(new Label("Тип услуги:"), 0, 0); orderGrid.add(typeCombo,    1, 0);
        orderGrid.add(new Label("Филиал:"),     0, 1); orderGrid.add(branchCombo,  1, 1);
        orderGrid.add(new Label("Дата приёма:"),0, 2); orderGrid.add(datePicker,   1, 2);
        orderGrid.add(createOrderButton,       1, 4);
        return orderGrid;
    }

    private void loadComboBoxData() {
        if (model.getServiceTypes() != null) {
            typeCombo.setItems(FXCollections.observableArrayList(model.getServiceTypes()));
        } else {
            showError("Не удалось загрузить типы услуг.");
        }

        if (model.getBranches() != null) {
            branchCombo.setItems(FXCollections.observableArrayList(model.getBranches()));
        } else {
            showError("Не удалось загрузить филиалы.");
        }
    }

    public Parent getView() {
        return root;
    }

    public TextField getSurnameField()       { return surnameField; }
    public TextField getNameField()          { return nameField; }
    public TextField getPatronymicField()    { return patronymicField; }
    public TextField getPhoneField()         { return phoneField; }
    public TextField getUsernameField()      { return usernameField; }
    public TextField getPasswordField()      { return passwordField; }
    public Button getSaveProfileButton()     { return saveProfileButton; }
    public TableView<Order> getTableView() { return ordersTable; }
    public ComboBox<org.example.model.ServiceType> getTypeCombo()   { return typeCombo; }
    public ComboBox<org.example.model.Branch> getBranchCombo() { return branchCombo; }
    public DatePicker getDatePicker() { return datePicker; }
    public Button getCreateOrderButton() { return createOrderButton; }


    public void setOrders(java.util.List<Order> list) {
        System.out.println("Setting orders for view: " + list.size());
        ordersTable.setItems(FXCollections.observableArrayList(list));
    }

    public void setProfile(org.example.model.Client client) {
        surnameField.setText(client.getSurname());
        nameField.setText(client.getName());
        patronymicField.setText(client.getPatronymic());
        phoneField.setText(client.getPhone());
        usernameField.setText(client.getUsername());
        passwordField.setText(client.getPassword());
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }

    public void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
