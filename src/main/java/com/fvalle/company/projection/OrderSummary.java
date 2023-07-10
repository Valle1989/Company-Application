package com.fvalle.company.projection;

import java.time.LocalDate;

public interface OrderSummary {
    Integer getIdOrder();
    String getCustomerName();
    String getEmployeeFirstName();
    String getEmployeeLastName();
    String getShipperName();
    String getShipperPhone();
    LocalDate getOrderDate();
}
