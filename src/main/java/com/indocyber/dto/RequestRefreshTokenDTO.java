package com.indocyber.dto;

public class RequestRefreshTokenDTO {

    private String refreshToken;

    public RequestRefreshTokenDTO() {}

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}