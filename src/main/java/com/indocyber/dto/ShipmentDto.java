package com.indocyber.dto;

import com.indocyber.validation.Price;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ShipmentDto {
    private int id;
    @NotBlank(message = "Name is required!")
    @NotNull(message = "Name is required!")
    private String name;
    @Price(message = "Price is required!")
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
