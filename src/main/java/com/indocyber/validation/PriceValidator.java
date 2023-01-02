package com.indocyber.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<Price, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext constraintValidatorContext) {

        if (price != null) {

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Invalid price!").addConstraintViolation();

            return price.compareTo(new BigDecimal(0)) >= 0;
        }

        return false;
    }
}
