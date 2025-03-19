package com.shyam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shyam.dto.MovieDTO;
import com.shyam.entity.Movie;
import com.shyam.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public Movie addMovie(MovieDTO movieDto) {
		Movie movie = new Movie();
		movie.setName(movieDto.getName());
		movie.setDescription(movieDto.getDescription());
		movie.setGenre(movieDto.getGenre());
		movie.setReleasedDate(movieDto.getReleasedDate());
		movie.setDuration(movieDto.getDuration());
		movie.setLanguage(movieDto.getLanguage());

		return movieRepository.save(movie);
	}

	public List<Movie> getAllMovies() {

		return movieRepository.findAll();
	}

	public List<Movie> getMovieByGenre(String genre) {
		// TODO Auto-generated method stub
		Optional<List<Movie>> byGenre = movieRepository.findByGenre(genre);

		if (byGenre.isPresent()) {
			return byGenre.get();
		} else {
			throw new RuntimeException("No Movies found for genre " + genre);
		}
	}

	public List<Movie> getMovieByLanguage(String language) {
		Optional<List<Movie>> list = movieRepository.findByLanguage(language);
		if (list.isPresent()) {
			return list.get();
		} else {
			throw new RuntimeException("No Movies found of Language " + language);
		}
	}

	public Movie getMovieByTitle(String title) {
		Optional<Movie> list = movieRepository.findByTitle(title);
		if (list.isPresent()) {
			return list.get();
		} else {
			throw new RuntimeException("No Movies Found of Title " + title);
		}
	}

	public Movie getMovieByName(String name) {
		Optional<Movie> movie = movieRepository.findByName(name);
		if (movie.isPresent()) {
			return movie.get();
		} else {
			throw new RuntimeException("No Movie Found of Name " + name);
		}
	}

	public Movie updateMovie(Long id, MovieDTO movieDTO) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No Movie found of ID " + id));

		movie.setName(movieDTO.getName());
		movie.setDescription(movieDTO.getDescription());
		movie.setGenre(movieDTO.getGenre());
		movie.setReleasedDate(movieDTO.getReleasedDate());
		movie.setDuration(movieDTO.getDuration());
		movie.setLanguage(movieDTO.getLanguage());

		return movieRepository.save(movie);
	}

	public void deleteMovie(Long id) {

		movieRepository.deleteById(id);
	}

}
