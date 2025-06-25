package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Service2Model {
    private final IntegerProperty serviceId = new SimpleIntegerProperty();
    private final DoubleProperty  price     = new SimpleDoubleProperty();
    private final IntegerProperty discount  = new SimpleIntegerProperty();

    public Service2Model(int serviceId, double price, int discount) {
        this.serviceId.set(serviceId);
        this.price.set(price);
        this.discount.set(discount);
    }

    // serviceId
    public int getServiceId()           { return serviceId.get(); }
    public void setServiceId(int value) { serviceId.set(value); }
    public IntegerProperty serviceIdProperty() { return serviceId; }

    // price
    public double getPrice()           { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }

    // discount
    public int getDiscount()           { return discount.get(); }
    public void setDiscount(int value) { discount.set(value); }
    public IntegerProperty discountProperty() { return discount; }
}
