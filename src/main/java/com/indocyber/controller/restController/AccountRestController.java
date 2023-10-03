package com.indocyber.controller.restController;

import com.indocyber.dto.RegisterDto;
import com.indocyber.dto.RequestRefreshTokenDTO;
import com.indocyber.dto.RequestTokenDTO;
import com.indocyber.dto.ResponseTokenDTO;
import com.indocyber.entity.Account;
import com.indocyber.security.JwtToken;
import com.indocyber.service.AccountService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        System.out.println(token);
        authenticationManager.authenticate(token);
        System.out.println(token);

        String refreshToken = jwtToken.generateRefreshToken(
                dto.getSubject(),
                dto.getUsername()
        );

        String accessToken = jwtToken.generateToken(
                dto.getSubject(),
                dto.getUsername(),
                dto.getRole(),
                dto.getAudience()
        );

        accountService.saveRefreshToken(refreshToken);

        return new ResponseTokenDTO(dto.getUsername(), dto.getRole(),
                accessToken,
                refreshToken
        );
    }

    @PostMapping("/refreshToken")
    public ResponseTokenDTO refreshToken(@RequestBody RequestRefreshTokenDTO dto, HttpServletRequest request) {


        Account account = accountService.getAccount();

        String authorization = request.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");

        String username = "";
        String role = "";
        String refreshToken = "";
        String accessToken = "";

        if (jwtToken.validateRefreshToken(dto.getRefreshToken(), account)) {

            Claims claims = jwtToken.getClaims(token);

            username = jwtToken.getUsername(token);
            role = claims.get("role", String.class);
//            refreshToken = jwtToken.generateRefreshToken(
//                    claims.getSubject(),
//                    account.getUsername()
//            );

            refreshToken = dto.getRefreshToken();

            accessToken = jwtToken.generateToken(
                    claims.getSubject(),
                    account.getUsername(),
                    account.getRole(),
                    claims.getAudience()
            );

            accountService.saveRefreshToken(refreshToken);
        }

        return new ResponseTokenDTO(
                username,
                role,
                accessToken,
                refreshToken
        );
    }

}
