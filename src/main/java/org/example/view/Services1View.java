package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import org.example.model.Service1;
import org.example.model.Services1;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class Services1View {
    private final StackPane root;
    private final Canvas canvas;
    private final Services1 model;
    private Button addButton, updateButton, deleteButton;
    private TextField clientIdField, serviceTypeIdField, branchIdField, requestNumberField, complexityField, workloadField, urgencyField;
    private DatePicker acceptanceDateField, returnDateField;
    private TableView<Service1> tableView;

    public Services1View(Services1 model) {
        this.model = model;
        this.root = new StackPane();
        this.canvas = new Canvas(600, 400);

        this.clientIdField = new TextField();
        this.serviceTypeIdField = new TextField();
        this.branchIdField = new TextField();
        this.requestNumberField = new TextField();
        this.complexityField = new TextField();
        this.workloadField = new TextField();
        this.urgencyField = new TextField();
        this.acceptanceDateField = new DatePicker();
        this.returnDateField = new DatePicker();

        this.addButton = new Button("Добавить услугу");
        this.updateButton = new Button("Обновить услугу");
        this.deleteButton = new Button("Удалить услугу");

        tableView = new TableView<>();
        TableColumn<Service1, Integer> serviceIdColumn = new TableColumn<>("ID Услуги");
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));

        TableColumn<Service1, Integer> clientIdColumn = new TableColumn<>("ID Клиента");
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Service1, Integer> serviceTypeIdColumn = new TableColumn<>("ID Вида услуги");
        serviceTypeIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceTypeId"));

        TableColumn<Service1, Integer> branchIdColumn = new TableColumn<>("ID Филиала");
        branchIdColumn.setCellValueFactory(new PropertyValueFactory<>("branchId"));

        TableColumn<Service1, Integer> requestNumberColumn = new TableColumn<>("Номер обращения");
        requestNumberColumn.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));

        TableColumn<Service1, Date> acceptanceDateColumn = new TableColumn<>("Дата приёма");
        acceptanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceDate"));

        TableColumn<Service1, Date> returnDateColumn = new TableColumn<>("Дата возврата");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<Service1, Integer> complexityColumn = new TableColumn<>("Сложность");
        complexityColumn.setCellValueFactory(new PropertyValueFactory<>("complexity"));

        TableColumn<Service1, Double> workloadColumn = new TableColumn<>("Объём работ");
        workloadColumn.setCellValueFactory(new PropertyValueFactory<>("workload"));

        TableColumn<Service1, Integer> urgencyColumn = new TableColumn<>("Срочность");
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));

        tableView.getColumns().addAll(serviceIdColumn, clientIdColumn, serviceTypeIdColumn, branchIdColumn, requestNumberColumn, acceptanceDateColumn, returnDateColumn, complexityColumn, workloadColumn, urgencyColumn);
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(400);

        model.refreshTable(tableView);

        Label clientIdLabel = new Label("ID Клиента:");
        Label serviceTypeIdLabel = new Label("ID Вида услуги:");
        Label branchIdLabel = new Label("ID Филиала:");
        Label requestNumberLabel = new Label("Номер обращения:");
        Label complexityLabel = new Label("Сложность:");
        Label workloadLabel = new Label("Объём работ:");
        Label urgencyLabel = new Label("Срочность:");
        Label acceptanceDateLabel = new Label("Дата приёма:");
        Label returnDateLabel = new Label("Дата возврата:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(clientIdLabel, 0, 0);
        grid.add(clientIdField, 1, 0);
        grid.add(serviceTypeIdLabel, 2, 0);
        grid.add(serviceTypeIdField, 3, 0);

        grid.add(branchIdLabel, 0, 1);
        grid.add(branchIdField, 1, 1);
        grid.add(requestNumberLabel, 2, 1);
        grid.add(requestNumberField, 3, 1);

        grid.add(complexityLabel, 0, 2);
        grid.add(complexityField, 1, 2);
        grid.add(workloadLabel, 2, 2);
        grid.add(workloadField, 3, 2);

        grid.add(urgencyLabel, 0, 3);
        grid.add(urgencyField, 1, 3);
        grid.add(acceptanceDateLabel, 2, 3);
        grid.add(acceptanceDateField, 3, 3);

        grid.add(returnDateLabel, 0, 4);
        grid.add(returnDateField, 1, 4);

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(10, tableView, grid, buttonBox);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        root.getChildren().add(vbox);

        addButton.getStyleClass().add("button");
        updateButton.getStyleClass().add("button");
        deleteButton.getStyleClass().add("button");

        clientIdField.getStyleClass().add("text-field");
        serviceTypeIdField.getStyleClass().add("text-field");
        branchIdField.getStyleClass().add("text-field");
        requestNumberField.getStyleClass().add("text-field");
        complexityField.getStyleClass().add("text-field");
        workloadField.getStyleClass().add("text-field");
        urgencyField.getStyleClass().add("text-field");
        acceptanceDateField.getStyleClass().add("date-picker");
        returnDateField.getStyleClass().add("date-picker");
    }

    public StackPane getView() {
        return root;
    }

    public void clearFields() {
        clientIdField.clear();
        serviceTypeIdField.clear();
        branchIdField.clear();
        requestNumberField.clear();
        complexityField.clear();
        workloadField.clear();
        urgencyField.clear();
        acceptanceDateField.setValue(null);
        returnDateField.setValue(null);
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public TableView<Service1> getTableView() {
        return tableView;
    }

    public TextField getClientIdField() {
        return clientIdField;
    }

    public TextField getServiceTypeIdField() {
        return serviceTypeIdField;
    }

    public TextField getBranchIdField() {
        return branchIdField;
    }

    public TextField getRequestNumberField() {
        return requestNumberField;
    }

    public TextField getComplexityField() {
        return complexityField;
    }

    public TextField getWorkloadField() {
        return workloadField;
    }

    public TextField getUrgencyField() {
        return urgencyField;
    }

    public DatePicker getAcceptanceDateField() {
        return acceptanceDateField;
    }

    public DatePicker getReturnDateField() {
        return returnDateField;
    }
}
