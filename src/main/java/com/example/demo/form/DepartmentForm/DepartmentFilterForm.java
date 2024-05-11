package com.example.demo.form.DepartmentForm;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentFilterForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date minCreateDate;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd" )
	private Date maxCreateDate;
	
	@Pattern(regexp = "Dev|Test|PM|ScrumMaster", message = "{Department.form.name.TypeDepartment}")
	private String typeDepartment;
	
}
