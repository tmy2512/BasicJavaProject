package com.example.demo.Validation.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.service.AcountService.IAccountService;
import com.example.demo.service.DepartmentService.IDepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountIdExistValidator implements ConstraintValidator<AccountIdExist, Integer>{

	@Autowired
	private IAccountService service;
	

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(id)) {
			return false;
		}
		return service.isAccountExistById(id);
	}

}
