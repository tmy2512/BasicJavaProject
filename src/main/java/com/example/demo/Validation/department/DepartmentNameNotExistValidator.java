package com.example.demo.Validation.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.service.DepartmentService.IDepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentNameNotExistValidator implements ConstraintValidator<DepartmentNameNotExist, String>{

	@Autowired
	private IDepartmentService service;
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(name)) {
			return true;
		}
		return !service.isDepartmentExistsByName(name);
	}

}
