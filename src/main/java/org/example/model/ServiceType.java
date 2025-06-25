
package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceType {
    private final IntegerProperty stid = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();

    public ServiceType() {}

    public ServiceType(int stid, String name, String type) {
        this.stid.set(stid);
        this.name.set(name);
        this.type.set(type);
    }

    public int getStid() {
        return stid.get();
    }

    public void setStid(int stid) {
        this.stid.set(stid);
    }

    public IntegerProperty stidProperty() {
        return stid;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public static ServiceType fromResultSet(ResultSet rs) throws SQLException {
        ServiceType serviceType = new ServiceType();
        serviceType.setStid(rs.getInt("stid"));
        serviceType.setName(rs.getString("name"));
        serviceType.setType(rs.getString("type"));
        return serviceType;
    }
}
