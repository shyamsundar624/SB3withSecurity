package com.ims.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ims.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Email is required")
	@Column(unique = true)
	private String email;
	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions;
	private final LocalDateTime createdAt = LocalDateTime.now();
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", role=" + role + ", createdAt=" + createdAt + "]";
	}



}
