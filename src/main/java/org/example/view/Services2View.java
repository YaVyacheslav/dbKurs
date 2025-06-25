package org.example.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import org.example.model.Service2;
import org.example.model.Services2;
import javafx.scene.control.cell.PropertyValueFactory;

public class Services2View {
    private final StackPane root;
    private final Canvas canvas;
    private final Services2 model;
    private Button addButton, updateButton, deleteButton;
    private TextField serviceIdField, priceField, discountField;
    private TableView<Service2> tableView;

    public Services2View(Services2 model) {
        this.model = model;
        this.root = new StackPane();
        this.canvas = new Canvas(600, 400);
        this.serviceIdField = new TextField();
        this.priceField = new TextField();
        this.discountField = new TextField();
        this.addButton = new Button("Добавить услугу");
        this.updateButton = new Button("Обновить услугу");
        this.deleteButton = new Button("Удалить услугу");

        tableView = new TableView<>();
        TableColumn<Service2, Integer> serviceIdColumn = new TableColumn<>("Service ID");
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<Service2, Integer>("serviceId"));

        TableColumn<Service2, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service2, Integer>("price"));

        TableColumn<Service2, Integer> discountColumn = new TableColumn<>("Discount");
        discountColumn.setCellValueFactory(new PropertyValueFactory<Service2, Integer>("discount"));

        tableView.getColumns().addAll(serviceIdColumn, priceColumn, discountColumn);
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(400);

        model.refreshTable(tableView);

        Label serviceIdLabel = new Label("Service ID:");
        Label priceLabel = new Label("Price:");
        Label discountLabel = new Label("Discount:");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        grid.add(serviceIdLabel, 0, 0);
        grid.add(serviceIdField, 1, 0);
        grid.add(priceLabel, 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(discountLabel, 0, 2);
        grid.add(discountField, 1, 2);


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setSpacing(10);


        VBox vbox = new VBox(10, tableView, grid, buttonBox);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        root.getChildren().add(vbox);
    }

    public StackPane getView() {
        return root;
    }

    public void clearFields() {
        serviceIdField.clear();
        priceField.clear();
        discountField.clear();
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

    public TableView<Service2> getTableView() {
        return tableView;
    }

    public TextField getServiceIdField() {
        return serviceIdField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public TextField getDiscountField() {
        return discountField;
    }
}