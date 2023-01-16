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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

        System.out.println(dto);

        List<String> role = userDetailsService.loadUserByUsername(dto.getUsername())
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if(role.contains(dto.getRole())){

            try {
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

                // UsernamePasswordAuthenticationToken is passed into the AuthenticationManager to be authenticated
                Authentication authentication = authenticationManager.authenticate(token);
                System.out.println("Is authenticate: " + authentication.isAuthenticated());
                Object thePrincipal = authentication.getPrincipal();
                System.out.println("thePrincipal: " + thePrincipal);

                ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) thePrincipal;
                System.out.println("Username: " + applicationUserDetails.getUsername());
                System.out.println("Password: " + applicationUserDetails.getPassword());
                System.out.println("isAccountNonExpired: " + applicationUserDetails.isAccountNonExpired());
                System.out.println("isAccountNonLocked: " + applicationUserDetails.isAccountNonLocked());
                System.out.println("isEnabled: " + applicationUserDetails.isEnabled());
                System.out.println("getAuthorities: " + applicationUserDetails.getAuthorities());

                System.out.println("credentials: " + authentication.getCredentials());
                System.out.println("name: " + authentication.getName());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not authenticate", e);
            }

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mismatched roles");
        }



//        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

        //String role = accountService.getAccountRole(dto.getUsername());
        String token = jwtToken.generateToken(
                dto.getSubject(),
                dto.getUsername(),
                dto.getSecretKey(),
//                role,
                dto.getRole(),
                dto.getAudience()
        );

        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(dto.getUsername(), dto.getRole(), token);

        return responseTokenDTO;
    }

}
