package com.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByusername(String username);
}
