package org.example.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Branch;
import org.example.model.Branchs;

public class BranchsView {
    private final VBox root;
    private final TableView<Branch> tableView;
    private final TextField addressField;
    private final Button addButton, updateButton, deleteButton;
    private final Branchs model;

    public BranchsView(Branchs model) {
        this.model = model;
        this.root = new VBox(10);
        this.addressField = new TextField();
        this.addButton = new Button("Добавить филиал");
        this.updateButton = new Button("Обновить филиал");
        this.deleteButton = new Button("Удалить филиал");

        addButton.getStyleClass().add("button");
        updateButton.getStyleClass().add("button");
        deleteButton.getStyleClass().add("button");
        addressField.getStyleClass().add("text-field");

        tableView = new TableView<>();
        TableColumn<Branch, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bid"));
        TableColumn<Branch, String> addrCol = new TableColumn<>("Адрес");
        addrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.getColumns().addAll(idCol, addrCol);

        model.refreshTable(tableView);

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(10, tableView, new Label("Адрес филиала:"), addressField, buttonBox);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        root.getChildren().add(vbox);
    }

    public VBox getView() {
        return root;
    }

    public Button getAddButton() { return addButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getDeleteButton() { return deleteButton; }
    public TableView<Branch> getTableView() { return tableView; }
    public TextField getAddressField() { return addressField; }

    public void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
