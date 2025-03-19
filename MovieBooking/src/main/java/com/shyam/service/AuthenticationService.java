package com.shyam.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shyam.dto.LoginRequestDTO;
import com.shyam.dto.LoginResponseDTO;
import com.shyam.dto.RegisterRequestDTO;
import com.shyam.entity.User;
import com.shyam.jwt.JwtService;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
		if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User Already Exist");
		}
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_USER");

		User user = new User();
		user.setUsername(registerRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setEmail(registerRequestDTO.getEmail());
		user.setRoles(roles);

		return userRepository.save(user);

	}

	public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
		if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User Already Exist");
		}
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");

		User user = new User();
		user.setUsername(registerRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setEmail(registerRequestDTO.getEmail());
		user.setRoles(roles);

		return userRepository.save(user);
	}

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByUsername(loginRequestDTO.getUsername())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		String token = jwtService.generateToken(user);

		return LoginResponseDTO.builder().jwtToken(token).username(user.getUsername()).roles(user.getRoles()).build();
	}

}
