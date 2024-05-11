package com.example.demo.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.form.AccountForm.LoginInfor;
import com.example.demo.service.AcountService.IAccountService;

@RestController
@RequestMapping(value = "api/v1/authen")
@CrossOrigin("*")
@Validated
public class AuthAccountController {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IAccountService service;
	
	@GetMapping("/login")
	public LoginInfor login(Principal principal) {
		String username = principal.getName();
		//convert entity and getInfor
		Account entity = service.getAccountByUsername(username);
		LoginInfor dto = modelMapper.map(entity, LoginInfor.class);
		return dto;
	}
}
