package org.example.controller;

import org.example.model.Register;
import org.example.view.RegisterView;

import java.sql.SQLException;

public class RegisterController {
    private final Register model;
    private final RegisterView view;

    public RegisterController(Register model, RegisterView view) {
        this.model = model;
        this.view = view;

        view.getRegisterButton().getStyleClass().add("button");
        view.getSurnameField().getStyleClass().add("text-field");
        view.getNameField().getStyleClass().add("text-field");
        view.getPatronymicField().getStyleClass().add("text-field");
        view.getPhoneField().getStyleClass().add("text-field");
        view.getUsernameField().getStyleClass().add("text-field");
        view.getPasswordField().getStyleClass().add("text-field");

        view.getRegisterButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                String surname = view.getSurname();
                String name = view.getName();
                String patronymic = view.getPatronymic();
                String phone = view.getPhone();
                String username = view.getUsername();
                String password = view.getPassword();

                if (surname.isEmpty() || name.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    view.showError("Все поля обязательны к заполнению.");
                    return;
                }

                try {
                    model.register(surname, name, patronymic, phone, username, password, "user");
                    view.showSuccess();
                } catch (SQLException ex) {
                    view.showError("Ошибка при доступе к БД:\n" + ex.getMessage());
                }
            }

        });
    }
}
