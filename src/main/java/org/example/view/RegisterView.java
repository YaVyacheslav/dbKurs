package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.model.Register;

public class RegisterView {
    private final TextField surnameField    = new TextField();
    private final TextField nameField       = new TextField();
    private final TextField patronymicField = new TextField();
    private final TextField phoneField      = new TextField();
    private final TextField usernameField   = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button registerButton     = new Button("Зарегистрироваться");

    private final GridPane root = new GridPane();

    public RegisterView(Register model) {

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        int row = 0;
        root.add(new Label("Фамилия:"),    0, row); root.add(surnameField,    1, row++);
        root.add(new Label("Имя:"),        0, row); root.add(nameField,       1, row++);
        root.add(new Label("Отчество:"),   0, row); root.add(patronymicField, 1, row++);
        root.add(new Label("Телефон:"),    0, row); root.add(phoneField,      1, row++);
        root.add(new Separator(),          0, row++, 2, 1);
        root.add(new Label("Логин:"),      0, row); root.add(usernameField,   1, row++);
        root.add(new Label("Пароль:"),     0, row); root.add(passwordField,   1, row++);
        root.add(registerButton,           1, row);
    }

    public Parent getView() {
        return root;
    }

    public String getSurname()    { return surnameField.getText().trim(); }
    public String getName()       { return nameField.getText().trim(); }
    public String getPatronymic() { return patronymicField.getText().trim(); }
    public String getPhone()      { return phoneField.getText().trim(); }
    public String getUsername()   { return usernameField.getText().trim(); }
    public String getPassword()   { return passwordField.getText().trim(); }
    public Button getRegisterButton() { return registerButton; }

    public void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Ошибка при регистрации");
        a.showAndWait();
    }

    public void showSuccess() {
        Alert a = new Alert(Alert.AlertType.INFORMATION,
                "Регистрация прошла успешно!", ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
