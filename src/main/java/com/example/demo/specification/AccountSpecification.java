package com.example.demo.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Account;
import com.example.demo.form.AccountForm.AccountFilterForm;

import jakarta.persistence.criteria.*;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class AccountSpecification {

	 

	public static Specification<Account> buildWhere(AccountFilterForm filterForm, String searchValue) {
		
		Specification<Account> where = null;
		 CustomSpecificationAccount search;
		 
		 if (!StringUtils.isEmpty(searchValue)) {
			 searchValue = searchValue.trim();
			search = new CustomSpecificationAccount("username", searchValue);
			CustomSpecificationAccount firstName = new CustomSpecificationAccount("firstName", searchValue);
			CustomSpecificationAccount lastName = new CustomSpecificationAccount("lastName", searchValue);
			where = Specification.where(search).or(firstName).or(lastName);
		}
		if (filterForm != null && filterForm.getRoleAcc() != null) {
			  search = new CustomSpecificationAccount("roleAcc", filterForm.getRoleAcc());
			if (where  == null) {
				where = search;
			}
			else {
				where = where.and(search);
			}
		}
		if (filterForm != null && filterForm.getDepartmentName() != null) {
			search = new CustomSpecificationAccount("departmentName", filterForm.getDepartmentName());
			if (where  == null) {
				where = search;
			}
			else {
				where = where.and(search);
			}
		}
		return where;// queryDSL

	}
}

@RequiredArgsConstructor
@SuppressWarnings("serial")
class CustomSpecificationAccount implements Specification<Account> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (field.equalsIgnoreCase("firstName")) {
			return criteriaBuilder.like(root.get("firstName"), "%" + value.toString() + "" + "%");// username like
																									// %value%
		}

		if (field.equalsIgnoreCase("username")) {
			return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("lastName")) {
			return criteriaBuilder.like(root.get("lastName"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("roleAcc")) {
			return criteriaBuilder.equal(root.get("roleAcc"), value);
		}
		if (field.equalsIgnoreCase("departmentName")) {
			return criteriaBuilder.like(root.get("department").get("name"), "%"+value.toString()+"%");
		}

		return null;
	}

}
