package com.example.demo.form.DepartmentForm;



import org.hibernate.validator.constraints.Length;

import com.example.demo.Validation.department.DepartmentIdExist;
import com.example.demo.Validation.department.DepartmentIdNotExist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateDepartmentForm {

	
	private int id;
	@NotBlank(message = "{Department.form.name.NotBlank}")
	@Length(max = 50, message = "{Department.form.name.Length}")	
	private String name;
	
	@Pattern(regexp = "Dev|Test|PM|ScrumMaster", message = "{Department.form.name.TypeDepartment}")
	@NotBlank(message = "{Department.form.name.TypeDepartment.NotBlank}")
	private String type;
	
}
