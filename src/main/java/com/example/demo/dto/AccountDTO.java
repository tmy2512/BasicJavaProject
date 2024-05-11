package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	private Integer id;
	private String username;
	private String fullname;
	@JsonProperty("role")
	private String roleAcc;
	private String departmentName;
}
