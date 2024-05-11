package com.example.demo.Validation.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.service.AcountService.IAccountService;
import com.example.demo.service.DepartmentService.IDepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNameNotExistValidator implements ConstraintValidator<AccountNameNotExist, String>{

	@Autowired
	private IAccountService service;
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {

		return !service.isAccountExistByName(name);
	}

}
