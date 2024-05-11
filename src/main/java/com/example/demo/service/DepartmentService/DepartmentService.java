package com.example.demo.service.DepartmentService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.example.demo.Validation.department.DepartmentIdNotExist;
import com.example.demo.entity.Account;
import com.example.demo.entity.Department;
import com.example.demo.entity.Department.TypeDepartment;
import com.example.demo.form.DepartmentForm.CreateDepartmentForm;
import com.example.demo.form.DepartmentForm.DepartmentFilterForm;
import com.example.demo.form.DepartmentForm.UpdateDepartmentForm;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.service.AcountService.IAccountService;
import com.example.demo.specification.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentRepository repository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IAccountRepository repositoryAccount;

	@Override
	public Page<Department> getAllDepartments(Pageable pageable, String sortField, String sort, String searchValue, DepartmentFilterForm filterForm) {
		Specification<Department> where = DepartmentSpecification.buildWhere(searchValue, filterForm);
//		Sort sort = Sort.by("name");

		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				sort.equalsIgnoreCase("asc") ? (Sort.by(sortField).ascending()) : (Sort.by(sortField).descending()));

		return repository.findAll(where, pageable);
	}

	@Transactional
	public void createDepartment(CreateDepartmentForm departmentForm) {
		
		LocalDate localDate = LocalDate.now();
		// convert form to entity
		Department departmentEntity = modelMapper.map(departmentForm, Department.class);
		departmentEntity.setCreatedDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		Department department = repository.save(departmentEntity);
//		int tmp = 0;
//		List<Account> accountEntities = departmentEntity.getLstAccounts();
//
//			for (Account account : accountEntities) {
//
//					account.setDepartment(department);
//					++tmp;
//			}
//			repositoryAccount.saveAll(accountEntities);
////		}
//		// update totalMember
//		departmentEntity.setTotalMember(tmp);
	}

	public void deleteListDepartment(List< Integer> departmentIdList) {
		// get department list from Department
//		List<Integer> depList =  (List<Integer>) modelMapper.map(departmentIdList, Department.class); 
		List<Department> dep = repository.findByIdIn(departmentIdList);
		if (dep.size() == departmentIdList.size()) {
			repository.deleteAllById(departmentIdList);
		}
		else {
//			throw new 
		}
		
	}

	@Override
	public void updateDepartment(UpdateDepartmentForm form) {
		Optional<Department> department = repository.findById(form.getId());
		Department de = department.get();
		de.setName(form.getName());
		de.setTypeDepartment(TypeDepartment.valueOf(form.getType()));
		repository.save(de);
		//update name trong account?

	}

	@Override
	public boolean isDepartmentExistsByName(String name) {
		// TODO Auto-generated method stub
		
		return repository.findByName(name);
	}
	
	@Override
	public boolean isDepartmentExistsById(Integer id) {
		// TODO Auto-generated method stub
		
		return repository.existsById(id);
	}

	@Override
	public void deleteDepartment(Integer id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

}
