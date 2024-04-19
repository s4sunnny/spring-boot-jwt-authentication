package com.sunny.auth.authentication.user.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sunny.auth.authentication.utils.BaseClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "user_name") })
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseClass implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@NotEmpty(message = "Firstname cannot be empty")
	@Column(length = 15)
	@EqualsAndHashCode.Include
	@Size(max = 15, message = "First name should be upto 15 characters long")
	private String firstName;

	@Column(length = 15)
	@EqualsAndHashCode.Include
	@Size(max = 15, message = "Last name should be upto 15 characters long")
	private String lastName;

	@NotEmpty(message = "Username cannot be empty")
	@Column(length = 10, unique = true)
	@EqualsAndHashCode.Include
	@Size(max = 10, message = "Username should be upto 10 characters long")
	private String userName;

	@NotEmpty(message = "Password cannot be empty")
	@Column(length = 50)
	@EqualsAndHashCode.Include
	@Size(max = 50, message = "Password should be upto 50 characters long")
	private String password;

	@Email
	@NotEmpty(message = "Email cannot be empty")
	@Column(length = 50)
	@EqualsAndHashCode.Include
	@Size(max = 50, message = "Email should be upto 50 characters long")
	private String email;

	private Integer attempt;

	private Integer isLocked;

	private Integer isActive = 1;
}
