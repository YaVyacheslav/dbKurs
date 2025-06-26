package org.example.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Services2 extends Observable {
    private final List<Service2> services = new ArrayList<>();
    private Connection connection;

    public Services2() {}

    public void load(Connection connection) throws SQLException {
        this.connection = connection;
        this.reload();
    }

    private void reload() throws SQLException {
        services.clear();
        String sql = "SELECT service_id, price, discount FROM services2";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                services.add(Service2.fromResultSet(rs));
            }
        }
        SingletonResult.getService2Instance().setResult(new ArrayList<>(services));

        setChanged();
        notifyObservers();
    }

    public void addService(int serviceId, Integer price, int discount) throws SQLException {
        String sql = "INSERT INTO services2 (service_id, price, discount) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, serviceId);

            if (price != null) {
                ps.setInt(2, price);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, discount);
            ps.executeUpdate();


            Service2 newService = new Service2(serviceId, price, discount);
            services.add(newService);
            setChanged();
            notifyObservers();
        }
        reload();
    }

    public void removeService(int serviceId) throws SQLException {
        String sql = "DELETE FROM services2 WHERE service_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (int i = 0; i < services.size(); i++) {
                    if (services.get(i).getServiceId() == serviceId) {
                        services.remove(i);
                        break;
                    }
                }
                setChanged();
                notifyObservers();
            }
        }
        reload();
    }

    public void updateService(int serviceId, Integer price, int discount) throws SQLException {
        String sql = "UPDATE services2 SET price = ?, discount = ? WHERE service_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            if (price != null) {
                ps.setInt(1, price);
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setInt(2, discount);
            ps.setInt(3, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {

                for (Service2 service : services) {
                    if (service.getServiceId() == serviceId) {
                        service.setPrice(price);
                        service.setDiscount(discount);
                        break;
                    }
                }
                setChanged();
                notifyObservers();
            }
        }
        reload();
    }

    public void refreshTable(TableView<Service2> tableView) {
        List<Service2> list = (List<Service2>) SingletonResult.getService2Instance().getResult();
        ObservableList<Service2> items = list == null
                ? FXCollections.emptyObservableList()
                : FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
