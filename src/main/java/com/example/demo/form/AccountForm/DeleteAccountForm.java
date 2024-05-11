package com.example.demo.form.AccountForm;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class DeleteAccountForm {

	List<@Valid Integer> id;
}
