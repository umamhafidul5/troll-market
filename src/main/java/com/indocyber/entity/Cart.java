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
    private List<Merchandise> merchandiseList;

    public Cart() {}

    public Cart(Account buyer, List<Merchandise> merchandiseList) {
        this.buyer = buyer;
        this.merchandiseList = merchandiseList;
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

    public List<Merchandise> getMerchandiseList() {
        return merchandiseList;
    }

    public void addMerchandise(Merchandise merchandise) {
        this.merchandiseList.add(merchandise);
    }

    public void setMerchandiseList(List<Merchandise> merchandiseList) {
        this.merchandiseList = merchandiseList;
    }
}
