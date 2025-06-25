package org.example.controller;

import org.example.model.Client;
import org.example.model.Clients;
import org.example.view.ClientView;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Observable;
import java.util.Observer;

public class ClientController implements Observer {
    private final Clients model;
    private final ClientView view;

    public ClientController(Clients model, ClientView view) {
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
            addClient();
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            updateClient();
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            deleteClient();
        }
    }

    private void addClient() {
        String surname = view.getSurnameField().getText();
        String name = view.getNameField().getText();
        String patronymic = view.getPatronymicField().getText();
        String phone = view.getPhoneField().getText();
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String role = view.getRoleField().getText();

        try {
            model.addClient(surname, name, patronymic, phone, username, password, role);
            view.clearFields();
        } catch (SQLException e) {
            view.showError("Ошибка при добавлении клиента: " + e.getMessage());
        }
    }

    private void updateClient() {
        Client selectedClient = view.getTableView().getSelectionModel().getSelectedItem();
        String surname = view.getSurnameField().getText();
        String name = view.getNameField().getText();
        String patronymic = view.getPatronymicField().getText();
        String phone = view.getPhoneField().getText();
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String role = view.getRoleField().getText();

        if (selectedClient != null) {
            try {
                model.updateClient(selectedClient.getCid(), surname, name, patronymic, phone, username, password, role);
                view.clearFields();
            } catch (SQLException e) {
                view.showError("Ошибка при обновлении клиента: " + e.getMessage());
            }
        } else {
            view.showError("Не выбран клиент для обновления.");
        }
    }

    private void deleteClient() {
        Client selectedClient = view.getTableView().getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            try {
                model.removeClient(selectedClient.getCid());
            } catch (SQLException e) {
                view.showError("Ошибка при удалении клиента: " + e.getMessage());
            }
        } else {
            view.showError("Не выбран клиент для удаления.");
        }
    }
}
