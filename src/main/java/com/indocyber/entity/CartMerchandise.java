package com.indocyber.entity;

import javax.persistence.*;

@Entity
@Table(name = "CartMerchandise")
public class CartMerchandise {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "MerchandiseId")
    private Merchandise merchandise;

    @Column(name = "Quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "ShipmentId")
    private Shipment shipment;

    public CartMerchandise() {}

    public CartMerchandise(Cart cart, Merchandise merchandise, int quantity, Shipment shipment) {
        this.cart = cart;
        this.merchandise = merchandise;
        this.quantity = quantity;
        this.shipment = shipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
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
}
