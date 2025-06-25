package org.example.model;

import javafx.beans.property.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service1 {
    private final IntegerProperty serviceId = new SimpleIntegerProperty();
    private final IntegerProperty clientId = new SimpleIntegerProperty();
    private final IntegerProperty serviceTypeId = new SimpleIntegerProperty();
    private final IntegerProperty branchId = new SimpleIntegerProperty();
    private final IntegerProperty requestNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Date> acceptanceDate = new SimpleObjectProperty<>();
    private final ObjectProperty<Date> returnDate = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> complexity = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> workload = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> urgency = new SimpleObjectProperty<>();

    public Service1() {}

    public Service1(int serviceId, int clientId, int serviceTypeId, int branchId, int requestNumber, Date acceptanceDate, Date returnDate, Integer complexity, Double workload, Integer urgency) {
        this.serviceId.set(serviceId);
        this.clientId.set(clientId);
        this.serviceTypeId.set(serviceTypeId);
        this.branchId.set(branchId);
        this.requestNumber.set(requestNumber);
        this.acceptanceDate.set(acceptanceDate);
        this.returnDate.set(returnDate);
        this.complexity.set(complexity);
        this.workload.set(workload);
        this.urgency.set(urgency);
    }

    public int getServiceId() {
        return serviceId.get();
    }

    public void setServiceId(int serviceId) {
        this.serviceId.set(serviceId);
    }

    public IntegerProperty serviceIdProperty() {
        return serviceId;
    }

    public int getClientId() {
        return clientId.get();
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
    }

    public int getServiceTypeId() {
        return serviceTypeId.get();
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId.set(serviceTypeId);
    }

    public IntegerProperty serviceTypeIdProperty() {
        return serviceTypeId;
    }

    public int getBranchId() {
        return branchId.get();
    }

    public void setBranchId(int branchId) {
        this.branchId.set(branchId);
    }

    public IntegerProperty branchIdProperty() {
        return branchId;
    }

    public int getRequestNumber() {
        return requestNumber.get();
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber.set(requestNumber);
    }

    public IntegerProperty requestNumberProperty() {
        return requestNumber;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate.get();
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate.set(acceptanceDate);
    }

    public ObjectProperty<Date> acceptanceDateProperty() {
        return acceptanceDate;
    }

    public Date getReturnDate() {
        return returnDate.get();
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate.set(returnDate);
    }

    public ObjectProperty<Date> returnDateProperty() {
        return returnDate;
    }

    public Integer getComplexity() {
        return complexity.get();
    }

    public void setComplexity(Integer complexity) {
        this.complexity.set(complexity);
    }

    public ObjectProperty<Integer> complexityProperty() {
        return complexity;
    }

    public Double getWorkload() {
        return workload.get();
    }

    public void setWorkload(Double workload) {
        this.workload.set(workload);
    }

    public ObjectProperty<Double> workloadProperty() {
        return workload;
    }

    public Integer getUrgency() {
        return urgency.get();
    }

    public void setUrgency(Integer urgency) {
        this.urgency.set(urgency);
    }

    public ObjectProperty<Integer> urgencyProperty() {
        return urgency;
    }

    public static Service1 fromResultSet(ResultSet rs) throws SQLException {
        Service1 service = new Service1();
        service.setServiceId(rs.getInt("service_id"));
        service.setClientId(rs.getInt("client_id"));
        service.setServiceTypeId(rs.getInt("service_type_id"));
        service.setBranchId(rs.getInt("branch_id"));
        service.setRequestNumber(rs.getInt("request_number"));
        service.setAcceptanceDate(rs.getDate("acceptance_date"));
        service.setReturnDate(rs.getDate("return_date"));
        service.setComplexity(rs.getObject("complexity") != null ? rs.getInt("complexity") : null);
        service.setWorkload(rs.getObject("workload") != null ? rs.getDouble("workload") : null);
        service.setUrgency(rs.getObject("urgency") != null ? rs.getInt("urgency") : null);
        return service;
    }
}
