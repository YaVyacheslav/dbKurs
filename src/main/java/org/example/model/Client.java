package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final IntegerProperty cid = new SimpleIntegerProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty patronymic = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty();

    public Client() {}

    public Client(int cid, String surname, String name, String patronymic, String phone, String username, String password, String role) {
        this.cid.set(cid);
        this.surname.set(surname);
        this.name.set(name);
        this.patronymic.set(patronymic);
        this.phone.set(phone);
        this.username.set(username);
        this.password.set(password);
        this.role.set(role);
    }

    public int getCid() {
        return cid.get();
    }

    public void setCid(int cid) {
        this.cid.set(cid);
    }

    public IntegerProperty cidProperty() {
        return cid;
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
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

    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public static Client fromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setCid(rs.getInt("cid"));
        client.setSurname(rs.getString("surname"));
        client.setName(rs.getString("name"));
        client.setPatronymic(rs.getString("patronymic"));
        client.setPhone(rs.getString("phone"));
        client.setUsername(rs.getString("username"));
        client.setPassword(rs.getString("password"));
        client.setRole(rs.getString("role"));
        return client;
    }
}
