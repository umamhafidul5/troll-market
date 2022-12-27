package com.indocyber.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "BuyerUsername")
    private Account buyer;

    public Cart () {}

    public Cart(Account buyer) {
        this.buyer = buyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }
}
