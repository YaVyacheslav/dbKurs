package org.example.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Clients extends Observable {
    private Connection connection;
    private final List<Client> clients = new ArrayList<>();

    public Clients() {}

    public void load(Connection connection) throws SQLException {
        this.connection = connection;
        reload();
    }

    private void reload() throws SQLException {
        clients.clear();
        String sql = "SELECT cid, surname, name, patronymic, phone, username, password, role FROM clients";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(Client.fromResultSet(rs));
            }
        }


        SingletonResult.getClientInstance().setResult(new ArrayList<>(clients));

        setChanged();

        notifyObservers();
    }

    public void addClient(String surname, String name, String patronymic, String phone, String username, String password, String role) throws SQLException {
        if (surname == null || surname.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            throw new SQLException("Фамилия и имя не могут быть пустыми.");
        }

        String sql = "INSERT INTO clients (surname, name, patronymic, phone, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setString(3, patronymic);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setString(7, role);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    Client newClient = new Client(keys.getInt(1), surname, name, patronymic, phone, username, password, role);
                    clients.add(newClient);
                    setChanged();
                    notifyObservers(new ArrayList<>(clients));
                }
            }
        }
        reload();
    }

    public void removeClient(int cid) throws SQLException {
        String sql = "DELETE FROM clients WHERE cid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getCid() == cid) {
                        clients.remove(i);
                        break;
                    }
                }
                setChanged();
                notifyObservers(new ArrayList<>(clients));
            }
        }
        reload();
    }


    public void updateClient(int cid, String surname, String name, String patronymic, String phone, String username, String password, String role) throws SQLException {
        String sql = "UPDATE clients SET surname = ?, name = ?, patronymic = ?, phone = ?, username = ?, password = ?, role = ? WHERE cid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setString(3, patronymic);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setString(7, role);
            ps.setInt(8, cid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (Client client : clients) {
                    if (client.getCid() == cid) {
                        client.setSurname(surname);
                        client.setName(name);
                        client.setPatronymic(patronymic);
                        client.setPhone(phone);
                        client.setUsername(username);
                        client.setPassword(password);
                        client.setRole(role);
                        break;
                    }
                }
                setChanged();
                notifyObservers(new ArrayList<>(clients));
            }
        }
        reload();
    }

    public void refreshTable(TableView<Client> tableView) {
        List<Client> list = (List<Client>) SingletonResult.getClientInstance().getResult();
        ObservableList<Client> items = list == null
                ? FXCollections.emptyObservableList()
                : FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
