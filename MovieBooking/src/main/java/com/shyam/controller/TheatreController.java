package com.shyam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.TheatreDTO;
import com.shyam.entity.Theatre;
import com.shyam.service.TheatreService;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

	@Autowired
	private TheatreService theatreService;

	@PostMapping("/addtheatre")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Theatre> addTheatre(@RequestBody TheatreDTO theatre) {
		return ResponseEntity.ok(theatreService.addTheatre(theatre));
	}

	@GetMapping("/gettheatrebylocation")
	public ResponseEntity<List<Theatre>> getTheatreByLocation(@RequestParam String location) {
		return ResponseEntity.ok(theatreService.getTheatreByLocation(location));
	}

	@PutMapping("/updatetheatre/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Theatre> updateTheatre(@PathVariable Long id, @RequestBody TheatreDTO theatreDTO) {
		return ResponseEntity.ok(theatreService.updateTheatre(id, theatreDTO));
	}

public ResponseEntity<Void> deleteTheatre(@PathVariable Long id){
	theatreService.deleteTheatre(id);
	return ResponseEntity.ok().build();
}

}
