package org.example.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import org.example.model.Client;
import org.example.model.Clients;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientsView {
    private final StackPane root;
    private final Canvas canvas;
    private final Clients model;
    private Button addButton, updateButton, deleteButton;
    private TextField surnameField, nameField, patronymicField, phoneField, usernameField, passwordField, roleField;
    private TableView<Client> tableView;

    public ClientsView(Clients model) {
        this.model = model;
        this.root = new StackPane();
        this.canvas = new Canvas(600, 400);
        this.surnameField = new TextField();
        this.nameField = new TextField();
        this.patronymicField = new TextField();
        this.phoneField = new TextField();
        this.usernameField = new TextField();
        this.passwordField = new TextField();
        this.roleField = new TextField();
        this.addButton = new Button("Добавить клиента");
        this.updateButton = new Button("Обновить клиента");
        this.deleteButton = new Button("Удалить клиента");


        tableView = new TableView<>();
        TableColumn<Client, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("cid"));

        TableColumn<Client, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Client, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Client, String> patronymicColumn = new TableColumn<>("Patronymic");
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

        TableColumn<Client, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Client, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Client, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Client, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(idColumn, surnameColumn, nameColumn, patronymicColumn, phoneColumn, usernameColumn, passwordColumn, roleColumn);
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(400);

        model.refreshTable(tableView);

        Label surnameLabel = new Label("Surname:");
        Label nameLabel = new Label("Name:");
        Label patronymicLabel = new Label("Patronymic:");
        Label phoneLabel = new Label("Phone:");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        grid.add(surnameLabel, 0, 0);
        grid.add(surnameField, 1, 0);
        grid.add(nameLabel, 2, 0);
        grid.add(nameField, 3, 0);
        grid.add(patronymicLabel, 0, 1);
        grid.add(patronymicField, 1, 1);
        grid.add(phoneLabel, 2, 1);
        grid.add(phoneField, 3, 1);
        grid.add(usernameLabel, 0, 2);
        grid.add(usernameField, 1, 2);
        grid.add(passwordLabel, 2, 2);
        grid.add(passwordField, 3, 2);
        grid.add(roleLabel, 0, 3);
        grid.add(roleField, 1, 3);


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setSpacing(10);


        VBox vbox = new VBox(10, tableView, grid, buttonBox);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        root.getChildren().add(vbox);
    }

    public StackPane getView() {
        return root;
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() {
        surnameField.clear();
        nameField.clear();
        patronymicField.clear();
        phoneField.clear();
        usernameField.clear();
        passwordField.clear();
        roleField.clear();
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public TableView<Client> getTableView() {
        return tableView;
    }

    public TextField getSurnameField() {
        return surnameField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getPatronymicField() {
        return patronymicField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getRoleField() {
        return roleField;
    }
}
