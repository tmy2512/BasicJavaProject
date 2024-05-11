package com.example.demo.form.AccountForm;


import org.hibernate.validator.constraints.Length;

import com.example.demo.Validation.account.AccountNameNotExist;
import com.example.demo.Validation.department.DepartmentIdExist;
import com.example.demo.Validation.department.DepartmentIdNotExist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAccountForAdminForm {

	@NotBlank(message = "{Account.form.username.NotBlank}")
	@Length(max = 50, message = "{Account.form.username.Length}")
	@AccountNameNotExist
	private String username;
	
	@NotBlank(message = "{Account.form.firstname.NotBlank}")
	@Length(max = 50, message = "{Account.form.firstname.Length}")
	private String firstName;
	
	@NotBlank(message = "{Account.form.lastname.NotBlank}")
	@Length(max = 50, message = "{Account.form.lastname.Length}")
	private String lastName;

	
	@NotBlank(message = "{Account.form.password.NotBlank}")
	@Length(max = 800, message = "{Account.form.password.Length}")
	private String password;

	@Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER", message = "{Account.form.role}")
	@NotBlank(message = "{Account.form.role.NotBlank}")
	private String roleAcc;
	
	@DepartmentIdNotExist
	private int departmentId;

}
