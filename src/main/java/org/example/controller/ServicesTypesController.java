package org.example.controller;

import org.example.model.ServiceType;
import org.example.model.ServiceTypes;
import org.example.view.ServicesTypesView;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Observable;
import java.util.Observer;

public class ServicesTypesController implements Observer {
    private final ServiceTypes model;
    private final ServicesTypesView view;

    public ServicesTypesController(ServiceTypes model, ServicesTypesView view) {
        this.model = model;
        this.view = view;

        initializeButtons();
        initializeTableSelection();
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        model.refreshTable(view.getTableView());
    }

    private void initializeButtons() {
        view.getAddButton().setOnAction(new AddButtonListener());
        view.getUpdateButton().setOnAction(new UpdateButtonListener());
        view.getDeleteButton().setOnAction(new DeleteButtonListener());
        view.getAddButton().getStyleClass().add("button");
        view.getUpdateButton().getStyleClass().add("button");
        view.getDeleteButton().getStyleClass().add("button");

        view.getNameField().getStyleClass().add("text-field");
        view.getTypeField().getStyleClass().add("text-field");

        view.getTableView().getStyleClass().add("table-view");
    }

    private void initializeTableSelection() {
        view.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                ServiceType selectedServiceType = view.getTableView().getSelectionModel().getSelectedItem();
                populateFields(selectedServiceType);
            }
        });
    }

    private void populateFields(ServiceType selectedServiceType) {
        view.getNameField().setText(selectedServiceType.getName());
        view.getTypeField().setText(selectedServiceType.getType());
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            addServiceType();
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            updateServiceType();
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            deleteServiceType();
        }
    }

    private void addServiceType() {
        String name = view.getNameField().getText();
        String type = view.getTypeField().getText();

        try {
            model.addServiceType(name, type);
            view.clearFields();
        } catch (SQLException e) {
            view.showError("Ошибка при добавлении типа услуги: " + e.getMessage());
        }
    }

    private void updateServiceType() {
        ServiceType selectedServiceType = view.getTableView().getSelectionModel().getSelectedItem();
        String name = view.getNameField().getText();
        String type = view.getTypeField().getText();

        if (selectedServiceType != null) {
            try {
                model.updateServiceType(selectedServiceType.getStid(), name, type);
                view.clearFields();
            } catch (SQLException e) {
                view.showError("Ошибка при обновлении типа услуги: " + e.getMessage());
            }
        } else {
            view.showError("Не выбран тип услуги для обновления.");
        }
    }

    private void deleteServiceType() {
        ServiceType selectedServiceType = view.getTableView().getSelectionModel().getSelectedItem();
        if (selectedServiceType != null) {
            try {
                model.removeServiceType(selectedServiceType.getStid());
            } catch (SQLException e) {
                view.showError("Ошибка при удалении типа услуги: " + e.getMessage());
            }
        } else {
            view.showError("Не выбран тип услуги для удаления.");
        }
    }
}
