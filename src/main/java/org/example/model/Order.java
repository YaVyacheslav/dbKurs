package org.example.model;

import java.sql.Date;

public class Order {
    private final String serviceName;
    private final String branchAddress;
    private final Date acceptanceDate;
    private final Date returnDate;
    private final Integer price;
    private final Integer discount;

    public Order(String serviceName, String branchAddress, Date acceptanceDate,
                 Date returnDate, Integer price, Integer discount) {
        this.serviceName = serviceName;
        this.branchAddress = branchAddress;
        this.acceptanceDate = acceptanceDate;
        this.returnDate = returnDate;
        this.price = price;
        this.discount = discount;
    }


    public String getServiceName() {
        return serviceName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDiscount() {
        return discount;
    }
}
