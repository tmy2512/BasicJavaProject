package com.example.demo.form.AccountForm;

import com.example.demo.Validation.account.AccountIdExist;
import com.example.demo.Validation.account.AccountNameNotExist;
import com.example.demo.entity.Account.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.example.demo.entity.Department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccountForm {


	private int id;
	
	@NotBlank(message = "{Account.form.username.NotBlank}")
	@Length(max = 50, message = "{Account.form.username.Length}")
	private String username;

	@NotBlank(message = "{Account.form.firstname.NotBlank}")
	@Length(max = 50, message = "{Account.form.firstname.Length}")
	private String firstName;

	@NotBlank(message = "{Account.form.lastname.NotBlank}")
	@Length(max = 50, message = "{Account.form.lastname.Length}")
	private String lastName;

	@Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER", message = "{Account.form.role}")
	@NotBlank(message = "{Account.form.role.NotBlank}")
	private String roleAcc;
	

}
