package org.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Service1Model {
    private final IntegerProperty serviceId       = new SimpleIntegerProperty();
    private final IntegerProperty clientId        = new SimpleIntegerProperty();
    private final IntegerProperty serviceTypeId   = new SimpleIntegerProperty();
    private final IntegerProperty branchId        = new SimpleIntegerProperty();
    private final IntegerProperty requestNumber   = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> acceptanceDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> returnDate     = new SimpleObjectProperty<>();
    private final IntegerProperty complexity      = new SimpleIntegerProperty();
    private final DoubleProperty  workload        = new SimpleDoubleProperty();
    private final IntegerProperty urgency         = new SimpleIntegerProperty();

    public Service1Model(int serviceId,
                         int clientId,
                         int serviceTypeId,
                         int branchId,
                         int requestNumber,
                         LocalDate acceptanceDate,
                         LocalDate returnDate,
                         int complexity,
                         double workload,
                         int urgency) {
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

    // serviceId
    public int getServiceId()                  { return serviceId.get(); }
    public void setServiceId(int id)           { serviceId.set(id); }
    public IntegerProperty serviceIdProperty() { return serviceId; }

    // clientId
    public int getClientId()                   { return clientId.get(); }
    public void setClientId(int id)            { clientId.set(id); }
    public IntegerProperty clientIdProperty()  { return clientId; }

    // serviceTypeId
    public int getServiceTypeId()                  { return serviceTypeId.get(); }
    public void setServiceTypeId(int typeId)       { serviceTypeId.set(typeId); }
    public IntegerProperty serviceTypeIdProperty() { return serviceTypeId; }

    // branchId
    public int getBranchId()                 { return branchId.get(); }
    public void setBranchId(int id)          { branchId.set(id); }
    public IntegerProperty branchIdProperty(){ return branchId; }

    // requestNumber
    public int getRequestNumber()                  { return requestNumber.get(); }
    public void setRequestNumber(int reqNum)       { requestNumber.set(reqNum); }
    public IntegerProperty requestNumberProperty() { return requestNumber; }

    // acceptanceDate
    public LocalDate getAcceptanceDate()                    { return acceptanceDate.get(); }
    public void setAcceptanceDate(LocalDate date)           { acceptanceDate.set(date); }
    public ObjectProperty<LocalDate> acceptanceDateProperty(){ return acceptanceDate; }

    // returnDate
    public LocalDate getReturnDate()                    { return returnDate.get(); }
    public void setReturnDate(LocalDate date)           { returnDate.set(date); }
    public ObjectProperty<LocalDate> returnDateProperty(){ return returnDate; }

    // complexity
    public int getComplexity()                   { return complexity.get(); }
    public void setComplexity(int complexity)    { this.complexity.set(complexity); }
    public IntegerProperty complexityProperty()  { return complexity; }

    // workload
    public double getWorkload()                  { return workload.get(); }
    public void setWorkload(double workload)     { this.workload.set(workload); }
    public DoubleProperty workloadProperty()     { return workload; }

    // urgency
    public int getUrgency()                  { return urgency.get(); }
    public void setUrgency(int urgency)      { this.urgency.set(urgency); }
    public IntegerProperty urgencyProperty(){ return urgency; }
}
