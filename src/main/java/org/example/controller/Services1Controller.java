package org.example.controller;

import org.example.model.Service1;
import org.example.model.Services1;
import org.example.view.Services1View;

import java.sql.Date;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Observable;
import java.util.Observer;

public class Services1Controller implements Observer {
    private final Services1 model;
    private final Services1View view;

    public Services1Controller(Services1 model, Services1View view) {
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


        view.getClientIdField().getStyleClass().add("text-field");
        view.getServiceTypeIdField().getStyleClass().add("text-field");
        view.getBranchIdField().getStyleClass().add("text-field");
        view.getRequestNumberField().getStyleClass().add("text-field");
        view.getAcceptanceDateField().getStyleClass().add("date-picker");
        view.getReturnDateField().getStyleClass().add("date-picker");

        view.getTableView().getStyleClass().add("table-view");
    }

    private void initializeTableSelection() {

        view.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Service1 selectedService = view.getTableView().getSelectionModel().getSelectedItem();
                populateFields(selectedService);
            }
        });
    }

    private void populateFields(Service1 selectedService) {

        view.getClientIdField().setText(String.valueOf(selectedService.getClientId()));
        view.getServiceTypeIdField().setText(String.valueOf(selectedService.getServiceTypeId()));
        view.getBranchIdField().setText(String.valueOf(selectedService.getBranchId()));
        view.getRequestNumberField().setText(String.valueOf(selectedService.getRequestNumber()));
        view.getAcceptanceDateField().setValue(selectedService.getAcceptanceDate().toLocalDate());


        if (selectedService.getReturnDate() != null) {
            view.getReturnDateField().setValue(selectedService.getReturnDate().toLocalDate());
        } else {
            view.getReturnDateField().setValue(null);
        }

        view.getComplexityField().setText(selectedService.getComplexity() != null ? String.valueOf(selectedService.getComplexity()) : "");
        view.getWorkloadField().setText(selectedService.getWorkload() != null ? String.valueOf(selectedService.getWorkload()) : "");
        view.getUrgencyField().setText(selectedService.getUrgency() != null ? String.valueOf(selectedService.getUrgency()) : "");
    }


    private class AddButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            addService();
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            updateService();
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            deleteService();
        }
    }

    private void addService() {
        int clientId = Integer.parseInt(view.getClientIdField().getText());
        int serviceTypeId = Integer.parseInt(view.getServiceTypeIdField().getText());
        int branchId = Integer.parseInt(view.getBranchIdField().getText());
        int requestNumber = Integer.parseInt(view.getRequestNumberField().getText());
        Date acceptanceDate = Date.valueOf(view.getAcceptanceDateField().getValue());
        Date returnDate = view.getReturnDateField().getValue() != null ? Date.valueOf(view.getReturnDateField().getValue()) : null;

        Integer complexity = parseInteger(view.getComplexityField().getText());
        Double workload = parseDouble(view.getWorkloadField().getText());
        Integer urgency = parseInteger(view.getUrgencyField().getText());

        try {
            model.addService(clientId, serviceTypeId, branchId, requestNumber, acceptanceDate, returnDate, complexity, workload, urgency);
            view.clearFields();
        } catch (SQLException e) {
            view.showError("Ошибка при добавлении услуги: " + e.getMessage());
        }
    }

    private void updateService() {
        Service1 selectedService = view.getTableView().getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            int clientId = Integer.parseInt(view.getClientIdField().getText());
            int serviceTypeId = Integer.parseInt(view.getServiceTypeIdField().getText());
            int branchId = Integer.parseInt(view.getBranchIdField().getText());
            int requestNumber = Integer.parseInt(view.getRequestNumberField().getText());
            Date acceptanceDate = Date.valueOf(view.getAcceptanceDateField().getValue());
            Date returnDate = view.getReturnDateField().getValue() != null ? Date.valueOf(view.getReturnDateField().getValue()) : null;

            Integer complexity = parseInteger(view.getComplexityField().getText());
            Double workload = parseDouble(view.getWorkloadField().getText());
            Integer urgency = parseInteger(view.getUrgencyField().getText());

            try {
                model.updateService(selectedService.getServiceId(), clientId, serviceTypeId, branchId, requestNumber, acceptanceDate, returnDate, complexity, workload, urgency);
                view.clearFields();
            } catch (SQLException e) {
                view.showError("Ошибка при обновлении услуги: " + e.getMessage());
            }
        } else {
            view.showError("Не выбрана услуга для обновления.");
        }
    }


    private void deleteService() {
        Service1 selectedService = view.getTableView().getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            try {
                model.removeService(selectedService.getServiceId());
            } catch (SQLException e) {
                view.showError("Ошибка при удалении услуги: " + e.getMessage());
            }
        } else {
            view.showError("Не выбрана услуга для удаления.");
        }
    }

    private Integer parseInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(text);
    }

    private Double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return Double.parseDouble(text);
    }
}
