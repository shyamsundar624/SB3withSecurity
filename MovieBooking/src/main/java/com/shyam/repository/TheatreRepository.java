package com.shyam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre,Long>{

	Optional<List<Theatre>> findByTheatreLocation(String location);

}
