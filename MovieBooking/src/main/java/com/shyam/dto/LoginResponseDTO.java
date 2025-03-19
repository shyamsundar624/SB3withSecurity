package com.shyam.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

	private String jwtToken;
	private String username;
	private Set<String> roles;
}
