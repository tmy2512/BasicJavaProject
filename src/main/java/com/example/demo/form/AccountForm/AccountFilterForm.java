package com.example.demo.form.AccountForm;


import com.example.demo.Validation.account.AccountNameNotExist;
import com.example.demo.entity.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

	@Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER", message = "The role's type must be ADMIN, EMPLOYEE or MANAGER")
	private String roleAcc;
	
	@AccountNameNotExist
	private String departmentName;
	
}
