package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequeridoValidador implements ConstraintValidator<Requerido,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //return !(s == null || s.isEmpty() || s.isBlank());
        return !(s== null || !StringUtils.hasText(s));
    }
}
