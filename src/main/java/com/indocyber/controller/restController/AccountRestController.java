package com.indocyber.controller.restController;

import com.indocyber.dto.RegisterDto;
import com.indocyber.dto.RequestTokenDTO;
import com.indocyber.dto.ResponseTokenDTO;
import com.indocyber.security.ApplicationUserDetails;
import com.indocyber.security.JwtToken;
import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @PostMapping("/registerAccount")
    public ResponseEntity<String> addAccount(@Valid @RequestBody RegisterDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().toString(), HttpStatus.CREATED);
        }

        if(dto.getRole().equals("Buyer")){
            accountService.registerBuyer(dto);
            return new ResponseEntity<>("Register Buyer Success", HttpStatus.CREATED);
        } else if (dto.getRole().equals("Seller")) {
            accountService.registerSeller(dto);
            return new ResponseEntity<>("Register Seller Success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("failed registration, mismatched roles", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticate")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto) {

        System.out.println(dto.toString());

        GrantedAuthority role = dto::getRole;

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(role);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword(), authorities);


        authenticationManager.authenticate(token);

        return new ResponseTokenDTO(dto.getUsername(), dto.getRole(),
                jwtToken.generateToken(
                        dto.getSubject(),
                        dto.getUsername(),
                        dto.getSecretKey(),
                        dto.getRole(),
                        dto.getAudience()
                ));
    }

}
