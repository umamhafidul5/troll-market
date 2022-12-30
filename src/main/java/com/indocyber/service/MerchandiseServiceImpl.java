package com.indocyber.service;

import com.indocyber.dto.MerchandiseDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.Merchandise;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MerchandiseServiceImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository, AccountRepository accountRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Merchandise> getAllMerchandiseBy(String name, String category, String description) {
        return merchandiseRepository.getMerchandisesByNameAndCategoryAndDescription(name, category, description);
    }

    @Override
    public List<Merchandise> searchMerchandises(String name, String category, String description) {
        return merchandiseRepository.searchMerchandises(name, category, description);
    }

    @Override
    public List<Merchandise> getAllMerchandise() {
        return merchandiseRepository.findAll();
    }

    @Override
    public void saveProduct(MerchandiseDto merchandiseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = this.accountRepository.findById(authentication.getName());
        Account account1 = account.get();
        Merchandise merchandise = null;
//        boolean value = merchandiseDto.getIsDiscontinue().equals("1") ? true : false;
        if(merchandiseDto.getId() == 0){
            merchandise = new Merchandise(account1, merchandiseDto.getName(),
                    merchandiseDto.getCategory(), merchandiseDto.getDescription(), merchandiseDto.getPrice(),
                    merchandiseDto.getIsDiscontinue());
            merchandiseRepository.save(merchandise);
        }else {
            merchandise = merchandiseRepository.findById(merchandiseDto.getId()).get();
            System.out.println(merchandiseDto.getId());
            merchandise.setId(merchandiseDto.getId());
            merchandise.setName(merchandiseDto.getName());
            merchandise.setCategory(merchandiseDto.getCategory());
            merchandise.setDescription(merchandiseDto.getDescription());
            merchandise.setPrice(merchandiseDto.getPrice());
            merchandise.setIsDiscontinue(merchandiseDto.getIsDiscontinue());
            merchandise.setSeller(account1);
            merchandiseRepository.save(merchandise);
        }
    }

    @Override
    public void saveMerchandise(Merchandise merchandise) {
        merchandiseRepository.save(merchandise);
    }

    @Override
    public void deleteProduct(int id) {
        merchandiseRepository.deleteById(id);
    }

    @Override
    public Merchandise findById(int i) {
        Optional<Merchandise> merchandise = merchandiseRepository.findById(i);
        Merchandise tempMerchandise = null;
        if(merchandise.isPresent()){
            tempMerchandise = merchandise.get();
        }
        return tempMerchandise;
    }

    @Override
    public MerchandiseDto findProduct(int i) {
        Optional<Merchandise> merchandise = merchandiseRepository.findById(i);
        MerchandiseDto merchandiseDto = null;
        if(merchandise.isPresent()){
            merchandiseDto = new MerchandiseDto(merchandise.get().getId(), merchandise.get().getName(),
                    merchandise.get().getCategory(), merchandise.get().getDescription(),
                    merchandise.get().getPrice(), merchandise.get().getIsDiscontinue());
        }
        return merchandiseDto;
    }
}
