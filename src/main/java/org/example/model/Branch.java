
package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.example.SingletonResult;

import java.sql.*;
import java.util.List;

public class Branch {
    private final IntegerProperty bid = new SimpleIntegerProperty();
    private final StringProperty address = new SimpleStringProperty();

    public Branch() { }

    public Branch(int bid, String address) {
        this.bid.set(bid);
        this.address.set(address);
    }

    public int getBid() {
        return bid.get();
    }

    public void setBid(int bid) {
        this.bid.set(bid);
    }

    public IntegerProperty bidProperty() {
        return bid;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public String toString() {
        return address.get();
    }

    public static Branch fromResultSet(ResultSet rs) throws SQLException {
        Branch branch = new Branch();
        branch.setBid(rs.getInt("bid"));
        branch.setAddress(rs.getString("address"));
        return branch;
    }
}
