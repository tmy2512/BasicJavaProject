package com.example.demo.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm.DepartmentFilterForm;

import jakarta.persistence.criteria.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class DepartmentSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Department> buildWhere(String searchValue, DepartmentFilterForm filterForm) {
		Specification<Department> where = null;
		CustomSpecificationDepartment search;
		if (!StringUtils.isEmpty(searchValue)) {
			searchValue = searchValue.trim();
			search = new CustomSpecificationDepartment("name", searchValue);
			where = Specification.where(search);
		}
		
		if (filterForm != null && filterForm.getTypeDepartment() != null) {
			search = new CustomSpecificationDepartment("typeDepartment", filterForm.getTypeDepartment());
			if (where == null) {
				where = search;
			}
			else {
				where = where.and(search);
			}
		}
		
		if (filterForm != null && filterForm.getMaxCreateDate() != null) {
			search = new CustomSpecificationDepartment("maxCreateDate", filterForm.getMaxCreateDate());
			if (where == null) {
				where = search;
			}
			else {
				where = where.and(search);
			}
			
		}
		
		if (filterForm != null && filterForm.getMinCreateDate() != null) {
			search = new CustomSpecificationDepartment("minCreateDate", filterForm.getMinCreateDate());
			if (where == null) {
				where = search;
			}
			else {
				where = where.and(search);
			}
			
		}
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecificationDepartment implements Specification<Department> {

	@NonNull
	private String field;
	@NonNull
	private Object value;
	
	
	@Override
	public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minCreateDate")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(java.sql.Date.class), (Date) value);
		}
		
		if (field.equalsIgnoreCase("maxCreateDate")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(java.sql.Date.class), (Date) value);
		}
		
		if (field.equalsIgnoreCase("typeDepartment")) {
			return criteriaBuilder.equal(root.get("typeDepartment"), value);
		}

		return null;
	}

}
