package com.indocyber.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "BuyerUsername")
    private Account buyer;


    @ManyToMany
//    @JoinTable
    private List<Merchandise> merchandise;
}
