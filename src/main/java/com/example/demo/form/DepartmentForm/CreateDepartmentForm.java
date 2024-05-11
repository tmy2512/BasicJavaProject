package com.example.demo.form.DepartmentForm;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.example.demo.Validation.department.DepartmentNameNotExist;
import com.example.demo.entity.Account;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@RequiredArgsConstructor
@NoArgsConstructor
public class CreateDepartmentForm {


	@NotBlank(message = "{Department.form.name.NotBlank}")
	@Length(max = 50, message = "{Department.form.name.Length}")
	@DepartmentNameNotExist
	private String name;


	@Pattern(regexp = "Dev|Test|PM|ScrumMaster", message = "{Department.form.name.TypeDepartment}")
	@NotBlank(message = "{Department.form.name.TypeDepartment.NotBlank}")
	private String typeDepartment;
	
//	@NonNull
//	private List<Account> lstAccounts;
//	
//	@Data
//	@NoArgsConstructor
//	public static class Account {
//		private int id;
//	}
}
