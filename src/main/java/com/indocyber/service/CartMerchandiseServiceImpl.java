package com.indocyber.service;

import com.indocyber.dto.CartMerchandiseDto;
import com.indocyber.entity.*;
import com.indocyber.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartMerchandiseServiceImpl implements CartMerchandiseService{

    private final CartMerchandiseRepository cartMerchandiseRepository;

    private final CartRepository cartRepository;

    private final MerchandiseRepository merchandiseRepository;

    private final AccountRepository accountRepository;

    private final ShipmentRepository shipmentRepository;


    @Autowired
    public CartMerchandiseServiceImpl(CartMerchandiseRepository cartMerchandiseRepository, CartRepository cartRepository, MerchandiseRepository merchandiseRepository, AccountRepository accountRepository, ShipmentRepository shipmentRepository) {
        this.cartMerchandiseRepository = cartMerchandiseRepository;
        this.cartRepository = cartRepository;
        this.merchandiseRepository = merchandiseRepository;
        this.accountRepository = accountRepository;
        this.shipmentRepository = shipmentRepository;
    }


    @Override
    public void saveCartMerchandise(CartMerchandiseDto dto, Account account) {
        Optional<Cart> cart = cartRepository.findByBuyer(account);
        Optional<Merchandise> merchandise = merchandiseRepository.findById(dto.getId());
        Optional<Shipment> shipment = shipmentRepository.findByName(dto.getShipment());
        Cart tempCart = null;
        Merchandise tempMerchandise = null;
        Shipment tempShipment = null;
        if (cart.isPresent()&&merchandise.isPresent()&&shipment.isPresent()){
            tempCart = cart.get();
            tempMerchandise = merchandise.get();
            tempShipment = shipment.get();
            CartMerchandise cartMerchandise = new CartMerchandise(tempCart, tempMerchandise,
                    dto.getQuantity(), tempShipment);
            cartMerchandiseRepository.save(cartMerchandise);
        }

    }



}
