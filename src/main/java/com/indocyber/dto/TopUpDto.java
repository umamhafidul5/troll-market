package com.indocyber.dto;

import com.indocyber.validation.TopUp;

import java.math.BigDecimal;

public class TopUpDto {
    @TopUp(message = "Invalid Amount")
    private BigDecimal amountTopUp;

    public TopUpDto() {
    }

    public BigDecimal getAmountTopUp() {
        return amountTopUp;
    }

    public void setAmountTopUp(BigDecimal amountTopUp) {
        this.amountTopUp = amountTopUp;
    }
}
