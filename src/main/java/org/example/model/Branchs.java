package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class BranchModel {
    private final IntegerProperty bid   = new SimpleIntegerProperty();
    private final StringProperty name   = new SimpleStringProperty();

    public BranchModel(int bid, String name) {
        this.bid.set(bid);
        this.name.set(name);
    }

    // bid
    public int getBid()                   { return bid.get(); }
    public void setBid(int value)         { bid.set(value); }
    public IntegerProperty bidProperty()  { return bid; }

    // name
    public String getName()                   { return name.get(); }
    public void setName(String value)         { name.set(value); }
    public StringProperty nameProperty()      { return name; }
}
