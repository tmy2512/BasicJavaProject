package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {

	private Integer id;
	private String name;
	private Integer totalMember;
	private String typeDepartment;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private List<AccountDTO> lstAccounts;

	@Data
	@NoArgsConstructor
	static class AccountDTO {
		private int id;
		private String username;
	}
}
