package com.ims.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ims.dto.LoginRequest;
import com.ims.dto.RegisterRequest;
import com.ims.dto.Response;
import com.ims.dto.UserDTO;
import com.ims.entity.User;
import com.ims.enums.UserRole;
import com.ims.exceptions.InvalidCredentialsExcepption;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.UserRepository;
import com.ims.security.JwtUtils;
import com.ims.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final JwtUtils jwtUtils;

	@Override
	public Response registerUser(RegisterRequest registerRequest) {
		UserRole role = UserRole.MANAGER;
		if (registerRequest.getRole() != null) {
			role = registerRequest.getRole();
		}
		User userToSave = User.builder()
				.name(registerRequest.getName())
				.email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(role)
				.phoneNumber(registerRequest.getPhoneNumber()).build();

		userRepository.save(userToSave);

		return Response.builder().status(200).message("User Created Successfully").build();
	}

	@Override
	public Response loginUser(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new NotFoundExcepption("User Not Found"));

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsExcepption("Password Does Not Match");
		}
		String token = jwtUtils.generateToken(user.getEmail());

		return Response.builder().status(200).message("User Login in Successfully").role(user.getRole()).token(token)
				.expirationTime("6 Month").build();
	}

	@Override
	public Response getAllUsers() {
		List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

		List<UserDTO> userDTO = modelMapper.map(users, new TypeToken<List<UserDTO>>() {
		}.getType());

		userDTO.forEach(user -> user.setTransactions(null));

		return Response.builder().status(200).message("success").users(userDTO).build();
	}

	@Override
	public User getCurrentLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundExcepption("User Not Found"));

		user.setTransactions(null);
		return user;
	}

	@Override
	public Response updateUser(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("User Not Found"));

		if(userDTO.getEmail()!=null) existingUser.setEmail(userDTO.getEmail());
		if(userDTO.getName()!=null) existingUser.setName(userDTO.getName());
		if(userDTO.getPhoneNumber()!=null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
		if(userDTO.getRole()!=null) existingUser.setRole(userDTO.getRole());
		
		if(userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty()) {
			existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		userRepository.save(existingUser);
		return Response.builder()
				.status(200)
				.message("User Successfully Updated")
				.build();
	}

	@Override
	public Response deleteUser(Long id) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(id);
		 return Response.builder()
				 .status(200)
				 .message("User Successfully Deleted")
				 .build();
	}

	@Override
	public Response getUserTransactions(Long id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("User Not Found"));

		UserDTO userDTO=modelMapper.map(existingUser,UserDTO.class);
		userDTO.getTransactions().forEach(obj->{
			obj.setUser(null);
			obj.setSupplier(null);
		});
		return Response.builder()
				.status(200)
				.message("Success")
				.user(userDTO)
				.build();
	}

}
