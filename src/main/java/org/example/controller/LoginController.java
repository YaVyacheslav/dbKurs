package org.example.controller;

import org.example.model.*;
import org.example.view.*;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import javafx.stage.Stage;

public class LoginController implements Observer {
    private final Login model;
    private final LoginView view;

    public LoginController(Login model, LoginView view) {
        this.model = model;
        this.view = view;

        this.model.addObserver(this);

        view.getLoginButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                model.authenticate(view.getUsername(), view.getPassword());
            }
        });

        view.getRegisterButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                model.openRegistrationWindow();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        String role = model.getRole();
        System.out.println("Role after authentication: " + role);
        if (role != null) {
            view.showSuccess(role);
            try {
                Stage primaryStage = model.getPrimaryStage();
                if ("admin".equals(role)) {
                    System.out.println("Navigating to admin dashboard");
                    model.showAdminDashboard(primaryStage);
                } else {
                    System.out.println("Navigating to user profile");
                    model.showUserProfile(primaryStage);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            view.showFailure();
        }
    }
}
