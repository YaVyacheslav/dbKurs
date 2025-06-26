package org.example.controller;

import org.example.model.*;
import org.example.view.UserProfileView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class UserProfileController implements Observer {
    private final UserProfile model;
    private final UserProfileView view;

    public UserProfileController(UserProfile model, UserProfileView view) throws SQLException {
        this.model = model;
        this.view = view;
        model.addObserver(this);

        view.getSaveProfileButton().getStyleClass().add("button");
        view.getCreateOrderButton().getStyleClass().add("button");

        view.getUsernameField().getStyleClass().add("text-field");
        view.getPasswordField().getStyleClass().add("text-field");
        view.getSurnameField().getStyleClass().add("text-field");
        view.getNameField().getStyleClass().add("text-field");
        view.getPatronymicField().getStyleClass().add("text-field");
        view.getPhoneField().getStyleClass().add("text-field");

        view.getTableView().getStyleClass().add("table-view");

        model.loadProfile();

        view.getSaveProfileButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                onSaveProfile();
            }
        });

        view.getCreateOrderButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                onCreateOrder();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        Client c = model.getClient();
        if (c != null) {
            view.setProfile(c);
        }

        List<Order> orders = model.getOrders();
        System.out.println("Setting orders for view: " + orders.size());


        view.setOrders(orders);
    }

    private void onSaveProfile() {
        try {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                view.showError("Логин и пароль не могут быть пустыми");
                return;
            }

            model.updateProfile(
                    view.getSurnameField().getText().trim(),
                    view.getNameField().getText().trim(),
                    view.getPatronymicField().getText().trim(),
                    view.getPhoneField().getText().trim(),
                    username,
                    password
            );
            view.showInfo("Данные сохранены");
        } catch (SQLException ex) {
            view.showError("Ошибка при сохранении: " + ex.getMessage());
        }
    }

    private void onCreateOrder() {
        try {
            ServiceType st = view.getTypeCombo().getValue();
            Branch branch = view.getBranchCombo().getValue();
            Date date = Date.valueOf(view.getDatePicker().getValue());

            model.createOrder(
                    st.getStid(),
                    branch.getBid(),
                    date
            );
            view.showInfo("Заказ успешно создан");
        } catch (SQLException ex) {
            view.showError("Ошибка при создании заказа: " + ex.getMessage());
        } catch (NullPointerException npe) {
            view.showError("Пожалуйста, заполните все поля для заказа.");
        }
    }
}
