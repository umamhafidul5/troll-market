package com.indocyber.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class TopUpValidator implements ConstraintValidator<TopUp, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal amountTopUp, ConstraintValidatorContext constraintValidatorContext) {

        if (amountTopUp != null) {
            boolean minLimit = amountTopUp.compareTo(new BigDecimal(10000)) >= 0;
            boolean maxLimit = amountTopUp.compareTo(new BigDecimal(1000000)) <= 0;
            constraintValidatorContext.disableDefaultConstraintViolation();

            if (!minLimit) {
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Minimum limit Rp 10.000")
                        .addConstraintViolation();

                return false;
            } else if (!maxLimit) {
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Maximum limit Rp 1.000.000")
                        .addConstraintViolation();

                return false;
            }

            return true;

        }

        return false;
    }
}
