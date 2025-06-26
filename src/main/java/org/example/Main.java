package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.controller.*;
import org.example.model.*;
import org.example.view.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kurs",
                    "root", ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Login loginModel = new Login();
        LoginView loginView = new LoginView(loginModel);

        loginModel.setPrimaryStage(primaryStage);
        loginModel.setConnection(connection);

        new LoginController(loginModel, loginView);

        Scene loginScene = new Scene(loginView.getView(), 404, 200);

        loginScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
