package org.example.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import org.example.model.ServiceType;
import org.example.model.ServiceTypes;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServicesTypesView {
    private final StackPane root;
    private final Canvas canvas;
    private final ServiceTypes model;
    private Button addButton, updateButton, deleteButton;
    private TextField nameField, typeField;
    private TableView<ServiceType> tableView;

    public ServicesTypesView(ServiceTypes model) {
        this.model = model;
        this.root = new StackPane();
        this.canvas = new Canvas(600, 400);
        this.nameField = new TextField();
        this.typeField = new TextField();
        this.addButton = new Button("Добавить тип услуги");
        this.updateButton = new Button("Обновить тип услуги");
        this.deleteButton = new Button("Удалить тип услуги");

        tableView = new TableView<>();
        TableColumn<ServiceType, Integer> stidColumn = new TableColumn<>("ID");
        stidColumn.setCellValueFactory(new PropertyValueFactory<ServiceType, Integer>("stid"));

        TableColumn<ServiceType, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<ServiceType, String>("name"));

        TableColumn<ServiceType, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<ServiceType, String>("type"));

        tableView.getColumns().addAll(stidColumn, nameColumn, typeColumn);
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(400);

        model.refreshTable(tableView);

        Label nameLabel = new Label("Name:");
        Label typeLabel = new Label("Type:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(typeLabel, 0, 1);
        grid.add(typeField, 1, 1);

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
        nameField.clear();
        typeField.clear();
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

    public TableView<ServiceType> getTableView() {
        return tableView;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getTypeField() {
        return typeField;
    }
}
