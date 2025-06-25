package org.example.controller;

import org.example.Main;
import org.example.model.Service1;
import org.example.model.Service2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceController {

    public ServiceController() {
        // Драйвер и соединение инициализируются в Main
    }

    // === CRUD для services1 ===

    public List<Service1> getAllService1() {
        List<Service1> list = new ArrayList<>();
        String sql = "SELECT service_id, client_id, service_type_id, branch_id, request_number, acceptance_date, return_date, complexity, workload, urgency FROM services1";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service1 svc = new Service1(
                        rs.getInt("service_id"),
                        rs.getInt("client_id"),
                        rs.getInt("service_type_id"),
                        rs.getInt("branch_id"),
                        rs.getInt("request_number"),
                        rs.getDate("acceptance_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getInt("complexity"),
                        rs.getDouble("workload"),
                        rs.getInt("urgency")
                );
                list.add(svc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service1 createService1(int clientId, int serviceTypeId, int branchId,
                                   int requestNumber, LocalDate acceptanceDate, LocalDate returnDate,
                                   int complexity, double workload, int urgency) {
        String sql = "INSERT INTO services1 (client_id, service_type_id, branch_id, request_number, acceptance_date, return_date, complexity, workload, urgency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, clientId);
            ps.setInt(2, serviceTypeId);
            ps.setInt(3, branchId);
            ps.setInt(4, requestNumber);
            ps.setDate(5, Date.valueOf(acceptanceDate));
            if (returnDate != null) ps.setDate(6, Date.valueOf(returnDate));
            else ps.setNull(6, Types.DATE);
            ps.setInt(7, complexity);
            ps.setDouble(8, workload);
            ps.setInt(9, urgency);

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("No rows affected.");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new Service1(id, clientId, serviceTypeId, branchId, requestNumber,
                            acceptanceDate, returnDate, complexity, workload, urgency);
                } else {
                    throw new SQLException("Failed to retrieve ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateService1(int serviceId, int clientId, int serviceTypeId, int branchId,
                                  int requestNumber, LocalDate acceptanceDate, LocalDate returnDate,
                                  int complexity, double workload, int urgency) {
        String sql = "UPDATE services1 SET client_id=?, service_type_id=?, branch_id=?, request_number=?, acceptance_date=?, return_date=?, complexity=?, workload=?, urgency=? WHERE service_id=?";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ps.setInt(2, serviceTypeId);
            ps.setInt(3, branchId);
            ps.setInt(4, requestNumber);
            ps.setDate(5, Date.valueOf(acceptanceDate));
            if (returnDate != null) ps.setDate(6, Date.valueOf(returnDate));
            else ps.setNull(6, Types.DATE);
            ps.setInt(7, complexity);
            ps.setDouble(8, workload);
            ps.setInt(9, urgency);
            ps.setInt(10, serviceId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteService1(int serviceId) {
        String sql = "DELETE FROM services1 WHERE service_id=?";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === CRUD для services2 ===

    public List<Service2> getAllService2() {
        List<Service2> list = new ArrayList<>();
        String sql = "SELECT service_id, price, discount FROM services2";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Service2(
                        rs.getInt("service_id"),
                        rs.getDouble("price"),
                        rs.getInt("discount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service2 createService2(int serviceId, double price, int discount) {
        String sql = "INSERT INTO services2 (service_id, price, discount) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ps.setDouble(2, price);
            ps.setInt(3, discount);

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("No rows affected.");
            return new Service2(serviceId, price, discount);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateService2(int serviceId, double price, int discount) {
        String sql = "UPDATE services2 SET price=?, discount=? WHERE service_id=?";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, discount);
            ps.setInt(3, serviceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteService2(int serviceId) {
        String sql = "DELETE FROM services2 WHERE service_id=?";
        try (PreparedStatement ps = Main.connection.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
