package com.shyam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long>{

	Optional<List<Show>> findByMovieId(Long movieId);

	Optional<List<Show>> findByTheatreId(Long theatreId);

}
