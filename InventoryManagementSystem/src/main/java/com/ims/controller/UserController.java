package com.ims.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.dto.LoginRequest;
import com.ims.dto.RegisterRequest;
import com.ims.dto.Response;
import com.ims.dto.UserDTO;
import com.ims.entity.User;
import com.ims.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllUser() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Response> loginUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.updateUser(id, userDTO));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}

	@GetMapping("/transactions/{id}")
	public ResponseEntity<Response> getUserTransactions(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.getUserTransactions(id));
	}

	@GetMapping("/current")
	public ResponseEntity<User> getCurrentUser(){
		return ResponseEntity.ok(userService.getCurrentLoggedInUser());
	}
}
