package com.example.demo.service.AcountService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import com.example.demo.entity.Account;
import com.example.demo.entity.Account.Role;
import com.example.demo.entity.Department;
import com.example.demo.form.AccountForm.AccountFilterForm;
import com.example.demo.form.AccountForm.CreateAccountForm;
import com.example.demo.form.AccountForm.CreateAccountForAdminForm;
import com.example.demo.form.AccountForm.CreateAccountForRegistrationAccountForm;
import com.example.demo.form.AccountForm.UpdateAccountForm;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IDepartmentRepository repositoryDepartment;

	@Override
	public Page<Account> getAllAccounts(Pageable pageable, String sortType, String sortField, String searchField, AccountFilterForm filterForm) {
		// generate where condition command
		Specification<Account> where = AccountSpecification.buildWhere(filterForm, searchField);

		// sort is attribute's pageable => change pageable for sort using method's OF in
		// PageRequest
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				(sortType.equalsIgnoreCase("desc") ? (Sort.by(sortField).descending())
						: (Sort.by(sortField).ascending())));
		Page<Account> list = repository.findAll(where, pageable);
		return list;
	}


	
	public void createAccount(CreateAccountForRegistrationAccountForm accountForm) {
		
		// convert form to entity
		Account account = modelMapper.map(accountForm, Account.class);
//		account.
		account.setRoleAcc(Role.EMPLOYEE);
		repository.save(account);
	}
	@Override
	@Transactional
	public void createAccountAdmin(CreateAccountForAdminForm accountForm) {
		
		// ignore id field in departmentId
		TypeMap<CreateAccountForRegistrationAccountForm, Account> typeMap = modelMapper.getTypeMap(CreateAccountForRegistrationAccountForm.class, Account.class);
		if (typeMap == null) {
			modelMapper.addMappings(new PropertyMap<CreateAccountForAdminForm, Account>() {

				@Override
				protected void configure() {
					skip(destination.getId());
				}
				
			});
		}
		// convert form to entity
		Account account = modelMapper.map(accountForm, Account.class);
		// update totalDepartment
		Optional<Department> department = repositoryDepartment.findById(accountForm.getDepartmentId());
		int totalMember = department.get().getTotalMember()+1;
		department.get().setTotalMember(totalMember);
//		repositoryDepartment.save(department.get());
		repository.save(account);
	}

	
	public void deleteListAccount(List<Integer> idList) {
		//tim cac account trong list tuong ung voi List ban dau
		List<Account> listAccount =  repository.findByIdIn(idList);
		int totalMemberInDepartment;
		if (listAccount.size() == idList.size()) {
			repository.deleteAllById(idList);
		}
			updateTotal();
	}
	
	public void updateTotal() {
		List<Department> departmentList = repositoryDepartment.findAll();
		for (Department department : departmentList) {
			department.setTotalMember(department.getLstAccounts().size());
		}
		repositoryDepartment.saveAll(departmentList);
	}

	@Override
	public void updateAccount(UpdateAccountForm accountForm) {
//		Account account = modelMapper.map(accountForm, Account.class);
		// tim account theo id  findById
		//set casc gtri thay tu form vao account   get setter
		// acc.setUsername(form.getUsername));
		// save(account);
		Optional<Account> acc = repository.findById(accountForm.getId());
		if (!acc.isEmpty()) {
//			throw  ...
		}
		Account account = acc.get();
		account.setUsername(accountForm.getUsername());
		account.setFirstName(accountForm.getFirstName());
		account.setLastName(accountForm.getLastName());
		account.setRoleAcc(Role.valueOf(accountForm.getRoleAcc()));
		repository.save(account);

	}


	public boolean isAccountExistById(Integer id) {
		// TODO Auto-generated method stub
		return repository.existsById(id);
	}



	@Override
	public boolean isAccountExistByName(String username) {
		// TODO Auto-generated method stub
		return repository.existsByUsername(username);
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = repository.findByUsername(username);
		
		if (account == null) {
			throw new UsernameNotFoundException(username);
			
		}
		return new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRoleAcc().toString()));
	}



	@Override
	public Account getAccountByUsername(String username) {
		return repository.findByUsername(username);
	}



	@Override
	public void deleteAccount(Integer id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}

}
