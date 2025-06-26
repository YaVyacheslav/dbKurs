package org.example.model;

import javafx.beans.property.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service2 {
    private final IntegerProperty serviceId = new SimpleIntegerProperty();
    private final ObjectProperty<Integer> price = new SimpleObjectProperty<>();
    private final IntegerProperty discount = new SimpleIntegerProperty();

    public Service2() {}

    public Service2(int serviceId, Integer price, int discount) {
        this.serviceId.set(serviceId);
        this.price.set(price);
        this.discount.set(discount);
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

    public Integer getPrice() {
        return price.get();
    }

    public void setPrice(Integer price) {
        this.price.set(price);
    }

    public ObjectProperty<Integer> priceProperty() {
        return price;
    }

    public int getDiscount() {
        return discount.get();
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public static Service2 fromResultSet(ResultSet rs) throws SQLException {
        Service2 service = new Service2();
        service.setServiceId(rs.getInt("service_id"));


        Integer price = rs.getObject("price") != null ? rs.getInt("price") : null;
        service.setPrice(price);

        service.setDiscount(rs.getInt("discount"));
        return service;
    }
}
