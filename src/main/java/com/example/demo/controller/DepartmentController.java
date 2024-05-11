package com.example.demo.controller;

import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Validation.department.DepartmentIdExist;
import com.example.demo.Validation.department.DepartmentIdNotExist;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm.CreateDepartmentForm;
import com.example.demo.form.DepartmentForm.DepartmentFilterForm;
import com.example.demo.form.DepartmentForm.UpdateDepartmentForm;
import com.example.demo.service.DepartmentService.IDepartmentService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/v1/departments")
@Validated
public class DepartmentController {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IDepartmentService service;// DepartmentService()
	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	public Page<DepartmentDTO> getAllDepartments(Pageable pageable,
			
			@RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
			@RequestParam(value = "sortField", required = false, defaultValue = "totalMember") String sortField,
			@RequestParam(value = "search", required = false) String searchValue,
			@Valid DepartmentFilterForm filterForm) {
		Page<Department> entityDepartments = service.getAllDepartments(pageable, sortField, sort, searchValue, filterForm);

		List<DepartmentDTO> dtos = modelMapper.map(entityDepartments.getContent(),
				new TypeToken<List<DepartmentDTO>>() {
				}.getType());
		Page<DepartmentDTO> dtoPage = new PageImpl<>(dtos, pageable, entityDepartments.getTotalElements());
		return dtoPage;
	}
	
	@PostMapping("/create")
	public void createNewDepartmemt(@RequestBody @Valid CreateDepartmentForm createForm) {
		service.createDepartment(createForm);
	}
	
	@PutMapping("/update")
	public void updateDepartment(@DepartmentIdNotExist @RequestParam(value = "id") int id,  
			@RequestBody @Valid UpdateDepartmentForm form) {
		form.setId(id);
		service.updateDepartment(form);
	}
	
	@DeleteMapping("/delete")
	public void deleteListDepartment(@RequestParam(value = "idList")  List<@DepartmentIdNotExist Integer> idList) {
		service.deleteListDepartment(idList);
	}
	
	@DeleteMapping("/delete_alacart")
	public void deleteDepartment(@DepartmentIdNotExist @RequestParam(value = "id")  Integer id) {
		service.deleteDepartment(id);
	}

	@GetMapping("/messages")
	public String getMessages(@RequestParam(value = "key") String key) {
		return messageSource.getMessage(key, null, "Default message", LocaleContextHolder.getLocale());
	}

	@GetMapping("/messages/vi")
	public String getMessagesVi(@RequestParam(value = "key") String key) {
		return messageSource.getMessage(key, null, "Default message", new Locale("vi", "VN"));
	}

	@GetMapping("/messages/en")
	public String getMessagesOther(@RequestParam(value = "key") String key) {
		return messageSource.getMessage(key, null, "Default message", Locale.US);
	}

}
