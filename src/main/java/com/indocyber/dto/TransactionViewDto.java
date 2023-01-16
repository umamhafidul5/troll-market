package com.indocyber.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionViewDto {

    private LocalDate purchaseDate;

    private String merchandiseName;

    private int quantity;

    private String shipment;

    private BigDecimal totalPrice;

    public TransactionViewDto(){}

    public TransactionViewDto(LocalDate purchaseDate, String merchandiseName, int quantity, String shipment, BigDecimal totalPrice) {
        this.purchaseDate = purchaseDate;
        this.merchandiseName = merchandiseName;
        this.quantity = quantity;
        this.shipment = shipment;
        this.totalPrice = totalPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
