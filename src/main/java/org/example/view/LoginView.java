package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.model.Login;

public class LoginView {
    private final Login model;
    private final GridPane root;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Button registerButton;

    public LoginView(Login model) {
        this.model = model;

        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        usernameField   = new TextField();
        passwordField   = new PasswordField();
        loginButton     = new Button("Войти");
        registerButton  = new Button("Регистрация");

        root.add(new Label("Логин:"),    0, 0);
        root.add(usernameField,          1, 0);
        root.add(new Label("Пароль:"),   0, 1);
        root.add(passwordField,          1, 1);

        HBox buttons = new HBox(10, loginButton, registerButton);
        root.add(buttons, 1, 2);


        Stage primaryStage = new Stage();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public Parent getView() {
        return root;
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void showSuccess(String role) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно вошли как: " + role);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showFailure() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный логин или пароль.");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
