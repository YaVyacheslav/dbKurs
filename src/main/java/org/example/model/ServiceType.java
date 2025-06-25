package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServiceTypeModel {
    private final IntegerProperty stid = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();

    public ServiceTypeModel(int stid, String name, String type) {
        this.stid.set(stid);
        this.name.set(name);
        this.type.set(type);
    }

    // stid
    public int getStid() { return stid.get(); }
    public void setStid(int value) { stid.set(value); }
    public IntegerProperty stidProperty() { return stid; }

    // name
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    // type
    public String getType() { return type.get(); }
    public void setType(String value) { type.set(value); }
    public StringProperty typeProperty() { return type; }
}
