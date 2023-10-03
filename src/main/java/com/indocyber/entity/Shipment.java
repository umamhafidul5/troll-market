package com.indocyber.entity;


import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author umamhafidul5
 *
 */
@Entity
@Table(name = "Shipment")
public class Shipment implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Service")
    private boolean service;

    public Shipment() {}

    public Shipment(String name, BigDecimal price, boolean service) {
        this.name = name;
        this.price = price;
        this.service = service;
    }

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
