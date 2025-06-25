package org.example.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.SingletonResult;
import org.example.model.Branch;
import org.example.model.Branchs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BranchView {
    private final StackPane root;
    private final TableView<Branch> tableView;
    private final TextField addressField;
    private final Button addButton, updateButton, deleteButton;

    private final Branchs model;

    public BranchView(Branchs model) {
        this.model = model;
        this.root = new StackPane();
        this.addressField = new TextField();
        this.addButton = new Button("Добавить филиал");
        this.updateButton = new Button("Обновить филиал");
        this.deleteButton = new Button("Удалить филиал");

        tableView = new TableView<>();
        TableColumn<Branch, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bid"));
        TableColumn<Branch, String> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.getColumns().addAll(idCol, addrCol);

        model.refreshTable(tableView);

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        VBox vbox = new VBox(10,
                tableView,
                new Label("Адрес филиала:"),
                addressField,
                buttonBox
        );
        VBox.setVgrow(tableView, Priority.ALWAYS);
        root.getChildren().add(vbox);
    }

    public StackPane getView() { return root; }
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
