package com.indocyber.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author umamhafidul5
 * @Entity Modul
 */
@Entity
@Table(name = "TransactionTroll")
public class Transaction implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PurchaseDate")
    private LocalDate purchaseDate;

    @OneToOne
    @JoinColumn(name = "MerchandiseId")
    private Merchandise merchandise;

    @Column(name = "MerchandiseName")
    private String merchandiseName;

    @Column(name = "Quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "ShipmentId")
    private Shipment shipment;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @OneToOne
    @JoinColumn(name = "BuyerUsername")
    private Account buyer;

    public Transaction() {}

    public Transaction(LocalDate purchaseDate, Merchandise merchandise, String merchandiseName, int quantity, Shipment shipment, BigDecimal totalPrice, Account buyer) {
        this.purchaseDate = purchaseDate;
        this.merchandise = merchandise;
        this.merchandiseName = merchandiseName;
        this.quantity = quantity;
        this.shipment = shipment;
        this.totalPrice = totalPrice;
        this.buyer = buyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
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

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }
}
