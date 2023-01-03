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
    public List<Merchandise> getMerchandiseSeller(Account seller) {
        return merchandiseRepository.getAllBySeller(seller);
    }

    @Override
    public void saveProduct(MerchandiseDto merchandiseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> optAccount = this.accountRepository.findById(authentication.getName());
        Account account = optAccount.get();
        Merchandise merchandise = new Merchandise();
//        boolean value = merchandiseDto.getIsDiscontinue().equals("1") ? true : false;
        if(merchandiseDto.getId() == 0){
            merchandise.setSeller(account);
            merchandise.setName(merchandiseDto.getName());
            merchandise.setCategory(merchandiseDto.getCategory());
            merchandise.setDescription(merchandiseDto.getDescription());
            merchandise.setPrice(merchandiseDto.getPrice());
            merchandise.setIsDiscontinue(merchandiseDto.getIsDiscontinue());
        }else {
            Optional<Merchandise> optMerchandise= merchandiseRepository.findById(merchandiseDto.getId());
            if(optMerchandise.isPresent()){
                merchandise = optMerchandise.get();
                merchandise.setName(merchandiseDto.getName());
                merchandise.setCategory(merchandiseDto.getCategory());
                merchandise.setDescription(merchandiseDto.getDescription());
                merchandise.setPrice(merchandiseDto.getPrice());
            }
        }
        merchandiseRepository.save(merchandise);
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
