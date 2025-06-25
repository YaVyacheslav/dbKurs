
package org.example.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Services1 extends Observable {
    private final List<Service1> services = new ArrayList<>();
    private Connection connection;

    public Services1() {}

    public void load(Connection connection) throws SQLException {
        this.connection = connection;
        this.reload();
    }

    private void reload() throws SQLException {
        services.clear();
        String sql = "SELECT service_id, client_id, service_type_id, branch_id, request_number, acceptance_date, return_date, complexity, workload, urgency FROM services1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                services.add(Service1.fromResultSet(rs));
            }
        }
        SingletonResult.getService1Instance().setResult(new ArrayList<>(services));

        setChanged();
        notifyObservers();
    }

    public void addService(int clientId, int serviceTypeId, int branchId, int requestNumber, Date acceptanceDate, Date returnDate, Integer complexity, Double workload, Integer urgency) throws SQLException {
        String sql = "INSERT INTO services1 (client_id, service_type_id, branch_id, request_number, acceptance_date, return_date, complexity, workload, urgency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, clientId);
            ps.setInt(2, serviceTypeId);
            ps.setInt(3, branchId);
            ps.setInt(4, requestNumber);
            ps.setDate(5, acceptanceDate);
            ps.setDate(6, returnDate);


            if (complexity != null) {
                ps.setInt(7, complexity);
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            if (workload != null) {
                ps.setDouble(8, workload);
            } else {
                ps.setNull(8, Types.DOUBLE);
            }

            if (urgency != null) {
                ps.setInt(9, urgency);
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    Service1 newService = new Service1(keys.getInt(1), clientId, serviceTypeId, branchId, requestNumber, acceptanceDate, returnDate, complexity, workload, urgency);
                    services.add(newService);
                    setChanged();
                    notifyObservers();
                }
            }
        }
        reload();
    }

    public void removeService(int serviceId) throws SQLException {
        String sql = "DELETE FROM services1 WHERE service_id = ?";

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


    public void updateService(int serviceId, int clientId, int serviceTypeId, int branchId, int requestNumber, Date acceptanceDate, Date returnDate, Integer complexity, Double workload, Integer urgency) throws SQLException {
        String sql = "UPDATE services1 SET client_id = ?, service_type_id = ?, branch_id = ?, request_number = ?, acceptance_date = ?, return_date = ?, complexity = ?, workload = ?, urgency = ? WHERE service_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ps.setInt(2, serviceTypeId);
            ps.setInt(3, branchId);
            ps.setInt(4, requestNumber);
            ps.setDate(5, acceptanceDate);
            ps.setDate(6, returnDate);


            if (complexity != null) {
                ps.setInt(7, complexity);
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            if (workload != null) {
                ps.setDouble(8, workload);
            } else {
                ps.setNull(8, Types.DOUBLE);
            }

            if (urgency != null) {
                ps.setInt(9, urgency);
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            ps.setInt(10, serviceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                for (Service1 service : services) {
                    if (service.getServiceId() == serviceId) {
                        service.setClientId(clientId);
                        service.setServiceTypeId(serviceTypeId);
                        service.setBranchId(branchId);
                        service.setRequestNumber(requestNumber);
                        service.setAcceptanceDate(acceptanceDate);
                        service.setReturnDate(returnDate);
                        service.setComplexity(complexity);
                        service.setWorkload(workload);
                        service.setUrgency(urgency);
                        break;
                    }
                }
                setChanged();
                notifyObservers();
            }
        }
        reload();
    }

    public void refreshTable(TableView<Service1> tableView) {
        List<Service1> list = (List<Service1>) SingletonResult.getService1Instance().getResult();
        ObservableList<Service1> items = list == null
                ? FXCollections.emptyObservableList()
                : FXCollections.observableArrayList(list);
        tableView.setItems(items);
    }
}
