package com.indocyber.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CompareValidator implements ConstraintValidator<Compare, Object> {

    private String firstField;
    private String secondField;

    @Override
    public void initialize(Compare constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        if(beanWrapper.getPropertyValue(secondField) == null) {

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Confirm Password is required!")
                    .addConstraintViolation();

            return false;

        } else if (beanWrapper.getPropertyValue(firstField) != null &&
                beanWrapper.getPropertyValue(secondField) != null){

            String firstValue = Objects.requireNonNull(beanWrapper.getPropertyValue(firstField)).toString();
            String secondValue = Objects.requireNonNull(beanWrapper.getPropertyValue(secondField)).toString();
            return (firstValue.equals(secondValue));
        }

        return true;
    }
}
