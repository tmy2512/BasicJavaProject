package com.example.demo.controller;

import java.security.Provider.Service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Validation.account.AccountIdExist;
import com.example.demo.Validation.department.DepartmentIdNotExist;
import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.form.AccountForm.AccountFilterForm;
import com.example.demo.form.AccountForm.CreateAccountForm;
import com.example.demo.form.AccountForm.DeleteAccountForm;
import com.example.demo.form.AccountForm.CreateAccountForAdminForm;
import com.example.demo.form.AccountForm.CreateAccountForRegistrationAccountForm;
import com.example.demo.form.AccountForm.UpdateAccountForm;
import com.example.demo.service.AcountService.IAccountService;

import jakarta.*;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "api/v1/accounts")
@Validated
public class AccountController {

	@Autowired
	private IAccountService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<AccountDTO> getAllAccounts(Pageable pageable,
			@RequestParam(value = "sort", required = false, defaultValue = "desc") String sortType,
			@RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
			@RequestParam(value = "searchField", required = false) String search,
			AccountFilterForm filterForm) {

		Page<Account> entityAccounts = service.getAllAccounts(pageable, sortType, sortField, search, filterForm);
		List<AccountDTO> dtos = modelMapper.map(entityAccounts.getContent(), new TypeToken<List<AccountDTO>>() {
		}.getType());
		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityAccounts.getTotalElements());
		
		return dtoPages;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createNewAccount(@RequestBody @Valid CreateAccountForRegistrationAccountForm accountForm) {
		
		 service.createAccount(accountForm);
		 return  new ResponseEntity("create successfuly", HttpStatus.OK);
	}
	
	@PostMapping("/admin")
	public ResponseEntity<?> createNewAccount(@RequestBody @Valid CreateAccountForAdminForm accountForm) {
		
		 service.createAccountAdmin(accountForm);
		 return  new ResponseEntity("create successfuly", HttpStatus.OK);
	}

	@PutMapping
	public void updateAccount( @RequestParam(value = "idU") @AccountIdExist  int idU, @Valid @RequestBody UpdateAccountForm accountForm) {		
		accountForm.setId(idU);
		service.updateAccount(accountForm);
	}

	@DeleteMapping("/deleteListAccounts")
	public void deleteAccount(@RequestParam(value = "idL")  List<@AccountIdExist Integer> idL) {		
		service.deleteListAccount(idL);
	}
	
	@DeleteMapping()
	public void deleteAccount(@RequestParam(value = "id")  Integer id) {
		
		service.deleteAccount(id);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("findById")
	public ResponseEntity<?> findAccount(@RequestParam(value = "idL") @AccountIdExist  Integer idL) {
		
		return new ResponseEntity(service.isAccountExistById(idL), HttpStatus.OK);
	}
	

}
