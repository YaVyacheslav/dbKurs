package org.example.controller;

import org.example.model.Service1;
import org.example.model.Services1;
import org.example.view.Service1View;

import java.sql.Date;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Observable;
import java.util.Observer;

public class Service1Controller implements Observer {
    private final Services1 model;
    private final Service1View view;

    public Service1Controller(Services1 model, Service1View view) {
        this.model = model;
        this.view = view;

        initializeButtons();
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
        Date returnDate = Date.valueOf(view.getReturnDateField().getValue());


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
            Date returnDate = Date.valueOf(view.getReturnDateField().getValue());


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
