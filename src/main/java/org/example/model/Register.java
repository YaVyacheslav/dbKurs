package org.example.model;

import java.sql.*;

public class Register {
    private Connection connection;

    public Register() {}

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void register(String surname,
                         String name,
                         String patronymic,
                         String phone,
                         String username,
                         String password,
                         String role) throws SQLException {
        try {
            connection.setAutoCommit(false);

            String sqlClient = "INSERT INTO clients (surname, name, patronymic, phone, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            int clientId;
            try (PreparedStatement ps = connection.prepareStatement(sqlClient, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, surname);
                ps.setString(2, name);
                ps.setString(3, patronymic.isBlank() ? null : patronymic);
                ps.setString(4, phone);
                ps.setString(5, username);
                ps.setString(6, password);
                ps.setString(7, role);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) throw new SQLException("Не удалось получить client_id");
                    clientId = rs.getInt(1);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
