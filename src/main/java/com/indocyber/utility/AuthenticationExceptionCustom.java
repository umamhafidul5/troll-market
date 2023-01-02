package com.indocyber.utility;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionCustom extends AuthenticationException {
    public AuthenticationExceptionCustom(String msg) {
        super(msg);
    }
}
