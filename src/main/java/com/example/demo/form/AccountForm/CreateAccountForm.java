package com.example.demo.form.AccountForm;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateAccountForm {

	
	@NonNull
	private String username;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private String password;
	@NonNull
	private String roleAcc;
	@NonNull
	private String departmentId;

}
