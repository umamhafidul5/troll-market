package com.indocyber.validation;

import com.indocyber.dto.RegisterAdminDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegisterAdminDtoPasswordMatcherValidator implements ConstraintValidator<RegisterAdminDtoPasswordMatcher, RegisterAdminDto> {
    @Override
    public boolean isValid(RegisterAdminDto registerAdminDto, ConstraintValidatorContext constraintValidatorContext) {
        return !((registerAdminDto.getPasswordConfirmation() != null) &&
                (registerAdminDto.getPassword() != null) &&
                !(registerAdminDto.getPassword().equals(registerAdminDto.getPasswordConfirmation())));
    }
}
