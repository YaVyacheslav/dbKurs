// src/main/java/org/example/model/ClientModel.java
package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {
    private final List<ClientRecord> clients = new ArrayList<>();

    public List<ClientRecord> getClients() {
        return clients;
    }

    public void loadAll() {
        clients.clear();
        String sql = "SELECT cid, surname, name, patronymic, phone FROM clients";
        try (Statement st = Main.connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new ClientRecord(
                        rs.getInt("cid"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronymic"),
                        rs.getString("phone")
                ));
            }
            setChanged();
            notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(ClientRecord rec) {
        String sql = "INSERT INTO clients(surname, name, patronymic, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setString(1, rec.getSurname());
            ps.setString(2, rec.getName());
            ps.setString(3, rec.getPatronymic());
            ps.setString(4, rec.getPhone());
            ps.executeUpdate();
            loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int cid) {
        String sql = "DELETE FROM clients WHERE cid = ?";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setInt(1, cid);
            ps.executeUpdate();
            loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class ClientRecord {
        private final int cid;
        private final String surname, name, patronymic, phone;

        public ClientRecord(int cid, String surname, String name, String patronymic, String phone) {
            this.cid = cid;
            this.surname = surname;
            this.name = name;
            this.patronymic = patronymic;
            this.phone = phone;
        }

        public int getCid() { return cid; }
        public String getSurname() { return surname; }
        public String getName() { return name; }
        public String getPatronymic() { return patronymic; }
        public String getPhone() { return phone; }
    }
}
