package com.indocyber.dto;

import com.indocyber.validation.Quantity;

import javax.validation.constraints.NotBlank;

public class CartMerchandiseDto {

    private int id;

    @Quantity(message = "Invalid quantity")
    private int quantity;

    @NotBlank(message = "Shipment is required!")
    private String shipment;

    public CartMerchandiseDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }
}
