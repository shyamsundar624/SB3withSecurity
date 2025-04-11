package com.library.DTO;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class LoginResponseDTO {

	private String token;
	private String username;
	private Set<String> roles;
}
