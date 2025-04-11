package com.ims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ims.entity.User;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
User user=userRepository.findByEmail(username).orElseThrow(()-> new NotFoundExcepption("User Not Found With "+username));

		return AuthUser.builder()
				.user(user)
				.build();
	}



}
