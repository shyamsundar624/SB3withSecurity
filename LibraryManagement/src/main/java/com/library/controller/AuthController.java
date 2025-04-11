package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.DTO.LoginRequestDTO;
import com.library.DTO.LoginResponseDTO;
import com.library.DTO.RegisterRequestDTO;
import com.library.entity.User;
import com.library.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/registernormaluser")
	public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
		return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
	}
}
