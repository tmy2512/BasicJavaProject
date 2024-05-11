package com.example.demo.service.DepartmentService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm.CreateDepartmentForm;
import com.example.demo.form.DepartmentForm.DepartmentFilterForm;
import com.example.demo.form.DepartmentForm.UpdateDepartmentForm;

public interface IDepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String sortField, String sortType, String searchValue, DepartmentFilterForm filterForm);

	public void createDepartment(CreateDepartmentForm departmentForm);

	public void deleteListDepartment(List<Integer> idList);
	
	public void deleteDepartment(Integer id);

	public void updateDepartment(UpdateDepartmentForm form);

	public boolean isDepartmentExistsByName(String name);
	
	public boolean isDepartmentExistsById(Integer id);
	
	
}
