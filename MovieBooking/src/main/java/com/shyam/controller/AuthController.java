package com.shyam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.LoginRequestDTO;
import com.shyam.dto.LoginResponseDTO;
import com.shyam.dto.RegisterRequestDTO;
import com.shyam.entity.User;
import com.shyam.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/registernormaluser")
	public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO){
		return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
		return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
	}
}
