package com.example.demo.entity;

import org.hibernate.annotations.Formula;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
@Entity
@Table(name = "Account")
public class Account {

	@Column(name = "id", columnDefinition = "unsigned int")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 800)
	private String password;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Column(name = "role",  columnDefinition = "default 'EMPLOYEE'")
	@Enumerated(EnumType.STRING)
	private Role roleAcc;// string

	@Formula(" concat(first_name, ' ', last_name) ")
	private String fullname;

	@ManyToOne()
	@JoinColumn(name = "department_id")
	private Department department;

	public enum Role {
		ADMIN, EMPLOYEE, MANAGER
	}

	

}
