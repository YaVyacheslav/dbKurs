
package org.example.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ServiceTypes extends Observable {
    private final List<ServiceType> serviceTypes = new ArrayList<>();
    private Connection connection;

    public ServiceTypes() {}

    public void load(Connection connection) throws SQLException {
        this.connection = connection;
        this.reload();
    }

    private void reload() throws SQLException {
        serviceTypes.clear();
        String sql = "SELECT stid, name, type FROM service_types";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                serviceTypes.add(ServiceType.fromResultSet(rs));
            }
        }
        SingletonResult.getServiceTypeInstance().setResult(new ArrayList<>(serviceTypes));

        setChanged();
        notifyObservers();
    }

    public void addServiceType(String name, String type) throws SQLException {
        String sql = "INSERT INTO service_types (name, type) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    ServiceType newServiceType = new ServiceType(keys.getInt(1), name, type);
                    serviceTypes.add(newServiceType);
                    setChanged();
                    notifyObservers();
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        reload();
    }

    public void removeServiceType(int stid) throws SQLException {
        String sql = "DELETE FROM service_types WHERE stid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, stid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (int i = 0; i < serviceTypes.size(); i++) {
                    if (serviceTypes.get(i).getStid() == stid) {
                        serviceTypes.remove(i);
                        break;
                    }
                }
                setChanged();
                notifyObservers();
            }
        } catch (SQLException e) {
            throw e;
        }
        reload();
    }

    public void updateServiceType(int stid, String name, String type) throws SQLException {
        String sql = "UPDATE service_types SET name = ?, type = ? WHERE stid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setInt(3, stid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (ServiceType serviceType : serviceTypes) {
                    if (serviceType.getStid() == stid) {
                        serviceType.setName(name);
                        serviceType.setType(type);
                        break;
                    }
                }
                setChanged();
                notifyObservers();
            }
        } catch (SQLException e) {
            throw e;
        }
        reload();
    }

    public void refreshTable(TableView<ServiceType> tableView) {
        List<ServiceType> list = (List<ServiceType>) SingletonResult.getServiceTypeInstance().getResult();
        ObservableList<ServiceType> items = list == null
                ? FXCollections.emptyObservableList()
                : FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
