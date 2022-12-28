package com.indocyber.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ShipmentDto {
    private int id;
    private String name;
    private BigDecimal price;
    private boolean service;

    public ShipmentDto () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }
}
