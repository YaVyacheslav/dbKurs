// File: org/example/model/LoginModel.java
package org.example.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public class Logins extends Observable {
    private String role;

    /**
     * Попытка аутентификации. По завершении оповещает Observer-ы.
     */
    public void authenticate(String username, String plainPassword) {
        String sql = "SELECT `password`, role FROM users WHERE username = ?";
        try (PreparedStatement ps = org.example.Main.connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && plainPassword.equals(rs.getString("password"))) {
                    role = rs.getString("role");
                } else {
                    role = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            role = null;
        }
        setChanged();
        notifyObservers();
    }

    public String getRole() {
        return role;
    }
}
