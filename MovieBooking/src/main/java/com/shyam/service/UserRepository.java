package com.shyam.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
