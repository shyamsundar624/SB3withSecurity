package com.ims.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.dto.LoginRequest;
import com.ims.dto.RegisterRequest;
import com.ims.dto.Response;
import com.ims.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
		return ResponseEntity.ok(userService.registerUser(registerRequest));
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest loginRequest){
		return ResponseEntity.ok(userService.loginUser(loginRequest));
	}
	
	
}
