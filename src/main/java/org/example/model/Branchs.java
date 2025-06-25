package org.example.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Branchs extends Observable {
    private Connection connection;
    private final List<Branch> branches = new ArrayList<>();

    public Branchs() { }

    public void load(Connection connection) throws SQLException {
        this.connection = connection;
        reload();
    }

    private void reload() throws SQLException {
        branches.clear();
        String sql = "SELECT bid, address FROM branches";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                branches.add(Branch.fromResultSet(rs));
            }
        }
        SingletonResult.getBranchInstance().setResult(new ArrayList<>(branches));

        setChanged();
        notifyObservers();
    }

    public void addBranch(String address) throws SQLException {
        if (address == null || address.trim().isEmpty())
            throw new SQLException("Адрес не может быть пустым.");

        String sql = "INSERT INTO branches (address) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, address);
            ps.executeUpdate();
        }
        reload();
    }

    public void removeBranch(int bid) throws SQLException {
        String sql = "DELETE FROM branches WHERE bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bid);
            if (ps.executeUpdate() > 0) {
                reload();
            }
        }
    }

    public void updateBranch(int bid, String newAddress) throws SQLException {
        if (newAddress == null || newAddress.trim().isEmpty())
            throw new SQLException("Новый адрес не может быть пустым.");

        String sql = "UPDATE branches SET address = ? WHERE bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newAddress);
            ps.setInt(2, bid);
            if (ps.executeUpdate() > 0) {
                reload();
            }
        }
    }

    public void refreshTable(TableView<Branch> tableView) {
        List<Branch> list = (List<Branch>) SingletonResult.getBranchInstance().getResult();
        ObservableList<Branch> items = list == null
                ? FXCollections.emptyObservableList()
                : FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
