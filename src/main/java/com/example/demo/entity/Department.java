package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Department")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", columnDefinition = "unsigned int")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	@Column(name = "total_member", columnDefinition = "UNSIGNED int")
	private int totalMember;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeDepartment typeDepartment;// string

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdDate;// 01-01-2024

	@OneToMany(mappedBy = "department")
	private List<Account> lstAccounts;

	public enum TypeDepartment {
		Dev, Test, ScrumMaster, PM
	}

}
