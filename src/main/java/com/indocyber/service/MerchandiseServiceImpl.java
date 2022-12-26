package com.indocyber.service;

import com.indocyber.entity.Merchandise;
import com.indocyber.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MerchandiseServiceImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;

    @Autowired
    public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository) {
        this.merchandiseRepository = merchandiseRepository;
    }

    @Override
    public List<Merchandise> getAllMerchandiseBy(String name, String category, String description) {
        return merchandiseRepository.getMerchandisesByNameOrCategoryOrDescription(name, category, description);
    }

    @Override
    public List<Merchandise> getAllMerchandise() {
        return merchandiseRepository.findAll();
    }
}
