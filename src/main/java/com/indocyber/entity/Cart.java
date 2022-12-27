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

    @ManyToMany
    @JoinTable(
            name = "Cart_Merchandise",
            joinColumns = @JoinColumn(name = "CartId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "MerchandiseId", referencedColumnName = "id")
    )
    private List<Merchandise> merchandise;

    public Cart () {}

    public Cart(Account buyer, List<Merchandise> merchandise) {
        this.buyer = buyer;
        this.merchandise = merchandise;
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

    public List<Merchandise> getMerchandise() {
        return merchandise;
    }

    public void addMerchandise(Merchandise merchandise) {
        this.merchandise.add(merchandise);
    }

    public void setMerchandise(List<Merchandise> merchandise) {
        this.merchandise = merchandise;
    }
}
