package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Department;

public interface IDepartmentRepository
		extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
	public boolean findByName(String name);
	
	List<Department> findByIdIn(List<Integer> idList);

}
