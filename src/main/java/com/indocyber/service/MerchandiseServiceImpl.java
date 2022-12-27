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
    public List<Merchandise> getAllMerchandise() {
        return merchandiseRepository.findAll();
    }

    @Override
    public void saveProduct(MerchandiseDto merchandiseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = this.accountRepository.findById(authentication.getName());
        Account account1 = account.get();
        Merchandise merchandise = new Merchandise(account1, merchandiseDto.getName(),
                merchandiseDto.getCategory(), merchandiseDto.getDescription(), merchandiseDto.getPrice(),
                merchandiseDto.getIsDiscontinue());
        merchandiseRepository.save(merchandise);
    }
}
