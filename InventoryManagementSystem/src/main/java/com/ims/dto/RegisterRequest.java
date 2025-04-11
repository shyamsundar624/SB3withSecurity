package com.ims.dto;

import com.ims.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;
	
	private UserRole role;
}
