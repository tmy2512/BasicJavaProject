package com.example.demo.Validation.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.service.DepartmentService.IDepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentIdNotExistValidator implements ConstraintValidator<DepartmentIdNotExist, Integer>{

	@Autowired
	private IDepartmentService service;
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(id)) {
			return true;
		}
		return service.isDepartmentExistsById(id);// true
	}
// kiem tra id khong ton tai phai khong
}
