package com.indocyber.dto;

import com.indocyber.validation.Price;

import java.math.BigDecimal;

public class ShipmentDtoUpdate {

    private int id;

    @Price(message = "Price is required!")
    private BigDecimal price;

    public ShipmentDtoUpdate(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
