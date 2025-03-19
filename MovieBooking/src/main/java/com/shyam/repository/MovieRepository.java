package com.shyam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	Optional<List<Movie>> findByGenre(String genre);

	Optional<List<Movie>> findByLanguage(String language);

	Optional<Movie> findByTitle(String title);

	Optional<Movie> findByName(String name);

}
