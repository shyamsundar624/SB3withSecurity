package com.ims.service;

import com.ims.dto.LoginRequest;
import com.ims.dto.RegisterRequest;
import com.ims.dto.Response;
import com.ims.dto.UserDTO;
import com.ims.entity.User;

public interface UserService {

	Response registerUser(RegisterRequest registerRequest);

	Response loginUser(LoginRequest loginRequest);

	Response getAllUsers();

	User getCurrentLoggedInUser();

	Response updateUser(Long id, UserDTO userDTO);

	Response deleteUser(Long id);

	Response getUserTransactions(Long id);

}
