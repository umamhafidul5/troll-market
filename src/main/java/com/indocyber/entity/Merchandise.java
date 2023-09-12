package com.indocyber.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Merchandise")
public class Merchandise implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "SellerUsername")
    private Account seller;

    @Column(name = "Name")
    private String name;

    @Column(name = "Category")
    private String category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "IsDiscontinue")
    private boolean isDiscontinue;

    public Merchandise() {}

    public Merchandise(Account seller, String name, String category, String description, BigDecimal price, boolean isDiscontinue) {
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.isDiscontinue = isDiscontinue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getIsDiscontinue() {
        return isDiscontinue;
    }

    public void setIsDiscontinue(boolean discontinue) {
        isDiscontinue = discontinue;
    }
}
