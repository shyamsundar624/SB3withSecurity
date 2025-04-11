package com.library.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.DTO.LoginRequestDTO;
import com.library.DTO.LoginResponseDTO;
import com.library.DTO.RegisterRequestDTO;
import com.library.entity.User;
import com.library.jwt.JWTService;
import com.library.repository.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTService jwtService;

	public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
		if (userRepository.findByusername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User ALready Register");
		}
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_USER");

		User user = new User();
		user.setEmail(registerRequestDTO.getEmail());
		user.setUsername(registerRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(roles);

		return userRepository.save(user);
	}

	public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
		if (userRepository.findByusername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User ALready Register");
		}
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");

		User user = new User();
		user.setEmail(registerRequestDTO.getEmail());
		user.setUsername(registerRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(roles);

		return userRepository.save(user);
	}

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

		User user = userRepository.findByusername(loginRequestDTO.getUsername())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		String token = jwtService.generateToken(user);

		return LoginResponseDTO.builder().token(token).username(user.getUsername()).roles(user.getRoles()).build();
	}
}
