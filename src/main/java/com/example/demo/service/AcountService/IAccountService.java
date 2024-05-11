package com.example.demo.service.AcountService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.entity.Account;
import com.example.demo.form.AccountForm.AccountFilterForm;
import com.example.demo.form.AccountForm.CreateAccountForm;
import com.example.demo.form.AccountForm.CreateAccountForAdminForm;
import com.example.demo.form.AccountForm.CreateAccountForRegistrationAccountForm;
import com.example.demo.form.AccountForm.UpdateAccountForm;

public interface IAccountService extends UserDetailsService{

	public Page<Account> getAllAccounts(Pageable pagable, String sortType, String sortField, String searchValue, AccountFilterForm filterForm);

	public void createAccount(CreateAccountForRegistrationAccountForm accountForm);
	
	public void createAccountAdmin(CreateAccountForAdminForm accountForm);

	public void deleteListAccount(List<Integer> listID);
	
	public void deleteAccount(Integer id);

	public void updateAccount(UpdateAccountForm accountForm);
	
	public boolean isAccountExistById(Integer id);
	
	public boolean isAccountExistByName(String name);
	
	public Account getAccountByUsername(String username);
}
