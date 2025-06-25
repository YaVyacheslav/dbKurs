package org.example.controller;

import org.example.model.Service2;
import org.example.model.Services2;
import org.example.view.Services2View;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Observable;
import java.util.Observer;

public class Services2Controller implements Observer {
    private final Services2 model;
    private final Services2View view;

    public Services2Controller(Services2 model, Services2View view) {
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
        int serviceId = Integer.parseInt(view.getServiceIdField().getText());
        Integer price = parseInteger(view.getPriceField().getText());
        int discount = Integer.parseInt(view.getDiscountField().getText());

        try {
            model.addService(serviceId, price, discount);
            view.clearFields();
        } catch (SQLException e) {
            view.showError("Ошибка при добавлении услуги: " + e.getMessage());
        }
    }

    private void updateService() {
        Service2 selectedService = view.getTableView().getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            int serviceId = Integer.parseInt(view.getServiceIdField().getText());
            Integer price = parseInteger(view.getPriceField().getText());
            int discount = Integer.parseInt(view.getDiscountField().getText());

            try {
                model.updateService(selectedService.getServiceId(), price, discount);
                view.clearFields();
            } catch (SQLException e) {
                view.showError("Ошибка при обновлении услуги: " + e.getMessage());
            }
        } else {
            view.showError("Не выбрана услуга для обновления.");
        }
    }

    private void deleteService() {
        Service2 selectedService = view.getTableView().getSelectionModel().getSelectedItem();
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
}
