package com.shyam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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
import org.springframework.web.service.annotation.GetExchange;

import com.shyam.dto.ShowDTO;
import com.shyam.entity.Show;
import com.shyam.service.ShowService;

@RestController
@RequestMapping("/api/show")
public class ShowController {

	@Autowired
	private ShowService showService;

	@PostMapping("/createshow")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO) {
		return ResponseEntity.ok(showService.createShow(showDTO));
	}

	@GetMapping("/getallshowd")
	public ResponseEntity<List<Show>> getAllShows(){
		return ResponseEntity.ok(showService.getAllshows());
	}
	
	@GetMapping("getshowsbymovie/{movieId}")
	public ResponseEntity<List<Show>> getShowsByMovis(@PathVariable Long movieId){
		return ResponseEntity.ok(showService.getShowsByMovie(movieId));
	}
	@GetMapping("getshowsbytheatre/{theatreId}")
	public ResponseEntity<List<Show>> getShowsByTheatre(@PathVariable Long theatreId){
		return ResponseEntity.ok(showService.getShowsByTheatre(theatreId));
	}
	@PutMapping("/updateshow/{id}")
	public ResponseEntity<Show> updateShow(@PathVariable Long id,@RequestBody ShowDTO showDTO){
		return ResponseEntity.ok(showService.updateShow(id,showDTO));
	}
	
	@DeleteMapping("/deleteshow/{id}")
	public ResponseEntity<Void> deleteShow(@PathVariable Long id){
		showService.deleteShow(id);
		
		return ResponseEntity.ok().build();
	}
}
