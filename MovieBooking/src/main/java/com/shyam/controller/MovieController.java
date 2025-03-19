package com.shyam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.MovieDTO;
import com.shyam.entity.Movie;
import com.shyam.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@PostMapping("/addmovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDto) {
		return ResponseEntity.ok(movieService.addMovie(movieDto));
	}

	@GetMapping("/getallmovies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return ResponseEntity.ok(movieService.getAllMovies());
	}

	@GetMapping("/getmoviebygenre")
	public ResponseEntity<List<Movie>> getMovieByGenre(@RequestParam String genre) {
		return ResponseEntity.ok(movieService.getMovieByGenre(genre));
	}

	@GetMapping("/getmoviebylanguage")
	public ResponseEntity<List<Movie>> getMovieByLanguage(@RequestParam String language) {
		return ResponseEntity.ok(movieService.getMovieByLanguage(language));
	}
	
	@GetMapping("/getmoviebytitle")
	public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title) {
		return ResponseEntity.ok(movieService.getMovieByTitle(title));
	}

	@GetMapping("/getmoviebyname")
	public ResponseEntity<Movie> getMovieByName(@RequestParam String name){
		return ResponseEntity.ok(movieService.getMovieByName(name));
	}
	@PutMapping("/updatemovie/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO){
		return ResponseEntity.ok(movieService.updateMovie(id,movieDTO));
	}
	
	@DeleteMapping("/deletemovie/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
		movieService.deleteMovie(id);
		return ResponseEntity.ok().build();
	}
}
