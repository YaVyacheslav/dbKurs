
package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class UserProfile extends Observable {
    private Connection connection;
    private int clientId;

    private Client client;
    private List<Order> orders = new ArrayList<>();
    private List<ServiceType> serviceTypes = new ArrayList<>();
    private List<Branch> branches = new ArrayList<>();

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void loadProfile() throws SQLException {
        String sql = "SELECT cid, surname, name, patronymic, phone, username, password, role FROM clients WHERE cid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    client = new Client(
                            rs.getInt("cid"),
                            rs.getString("surname"),
                            rs.getString("name"),
                            rs.getString("patronymic"),
                            rs.getString("phone"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        }

        setChanged();
        notifyObservers();
    }

    public void loadServiceTypes() throws SQLException {
        String sql = "SELECT stid, name, type FROM service_types";
        serviceTypes.clear();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    serviceTypes.add(new ServiceType(rs.getInt("stid"), rs.getString("name"), rs.getString("type")));
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        setChanged();
        notifyObservers();
    }

    public void loadBranches() throws SQLException {
        String sql = "SELECT bid, address FROM branches";
        branches.clear();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    branches.add(new Branch(rs.getInt("bid"), rs.getString("address")));
                }
            }
        }

        setChanged();
        notifyObservers();
    }


    public void loadOrders() throws SQLException {
        orders.clear();
        String sql = "SELECT s1.service_id, st.name AS service_name, b.address AS branch_address, " +
                "s1.acceptance_date, s1.return_date, s1.workload, s2.price, s2.discount " +
                "FROM services1 s1 " +
                "JOIN service_types st ON s1.service_type_id = st.stid " +
                "JOIN branches b ON s1.branch_id = b.bid " +
                "LEFT JOIN services2 s2 ON s1.service_id = s2.service_id " +
                "WHERE s1.client_id = ?";

        System.out.println("Executing SQL query to load orders for client ID: " + clientId);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);

            try (ResultSet rs = ps.executeQuery()) {
                int rowCount = 0;
                while (rs.next()) {
                    rowCount++;
                    Integer price = rs.getObject("price") == null ? null : rs.getInt("price");
                    orders.add(new Order(
                            rs.getString("service_name"),
                            rs.getString("branch_address"),
                            rs.getDate("acceptance_date"),
                            rs.getDate("return_date"),
                            price,
                            rs.getObject("discount") == null ? null : rs.getInt("discount")
                    ));
                }
                if (rowCount == 0) {
                }
            }
        }

        setChanged();
        notifyObservers();
    }

    public void updateProfile(String surname, String name, String patronymic, String phone, String username, String password) throws SQLException {
        String sql = "UPDATE clients SET surname=?, name=?, patronymic=?, phone=?, username=?, password=? WHERE cid=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setString(3, patronymic);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setInt(7, clientId);
            ps.executeUpdate();
        }


        client.setSurname(surname);
        client.setName(name);
        client.setPatronymic(patronymic);
        client.setPhone(phone);
        client.setUsername(username);
        client.setPassword(password);

        setChanged();
        notifyObservers();
    }


    public void createOrder(int serviceTypeId, int branchId, Date acceptanceDate) throws SQLException {
        int newRequestNumber = 1;
        int discount = 0;


        String sqlMaxRequestNumber = "SELECT MAX(request_number) FROM services1 WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlMaxRequestNumber)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    newRequestNumber = rs.getInt(1) + 1;
                }
            }
        }

        if (newRequestNumber > 2) {
            discount = 3;
        }

        String sql1 = "INSERT INTO services1 (client_id, service_type_id, branch_id, request_number, acceptance_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        int newServiceId;
        try (PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, clientId);
            ps.setInt(2, serviceTypeId);
            ps.setInt(3, branchId);
            ps.setInt(4, newRequestNumber);
            ps.setDate(5, acceptanceDate);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("Не удалось получить service_id");
                newServiceId = keys.getInt(1);
            }
        }

        String sql2 = "INSERT INTO services2 (service_id, price, discount) VALUES (?, NULL, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql2)) {
            ps.setInt(1, newServiceId);
            ps.setObject(2, discount == 0 ? null : discount, Types.INTEGER);
            ps.executeUpdate();
        }

        loadOrders();
    }


    public Client getClient() {
        return client;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
