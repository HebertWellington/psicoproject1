package com.project.psicoproject1.service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.project.psicoproject1.config.ValidPassword;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}

   

}