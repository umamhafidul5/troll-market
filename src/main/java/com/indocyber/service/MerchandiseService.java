package com.indocyber.service;

import com.indocyber.dto.MerchandiseDto;
import com.indocyber.entity.Merchandise;

import java.util.List;

public interface MerchandiseService {
    List<Merchandise> getAllMerchandiseBy(String name, String category, String description);

    List<Merchandise> getAllMerchandise();

    void saveProduct (MerchandiseDto merchandiseDto);

    void saveMerchandise (Merchandise merchandise);

    void deleteProduct(int id);

    Merchandise findById(int i);

}
