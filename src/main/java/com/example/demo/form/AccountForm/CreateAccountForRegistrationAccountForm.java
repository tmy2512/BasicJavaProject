package com.example.demo.form.AccountForm;

import org.hibernate.validator.constraints.Length;

import com.example.demo.Validation.account.AccountNameNotExist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAccountForRegistrationAccountForm {


	@NotBlank(message = "{Account.form.username.NotBlank}")
	@Length(max = 50, message = "{Account.form.username.Length}")
	@AccountNameNotExist // check username exists
	private String username;
	
	
	@NotBlank(message = "{Account.form.firstname.NotBlank}")
	@Length(max = 50, message = "{Account.form.firstname.Length}")
	private String firstName;
	
	@NotBlank(message = "{Account.form.lastname.NotBlank}")
	@Length(max = 50, message = "{Account.form.lastname.Length}")
	private String lastName;
	
	@Email(message = "{Account.form.email.Format}")
	@NotBlank(message = "{Account.form.email.NotBlank}")
	private String email;
	
	@NotBlank(message = "{Account.form.password.NotBlank}")
	@Length(max = 800, message = "{Account.form.password.Length}")
	private String password;

}
