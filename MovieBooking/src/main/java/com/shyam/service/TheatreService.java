package com.shyam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.dto.TheatreDTO;
import com.shyam.entity.Theatre;
import com.shyam.repository.TheatreRepository;

@Service
public class TheatreService {
	@Autowired
	private TheatreRepository theatreRepository;

	public Theatre addTheatre(TheatreDTO theatreDto) {
		Theatre theatre = new Theatre();
		theatre.setTheatreName(theatreDto.getTheatreName());
		theatre.setTheatreLocation(theatreDto.getTheatreLocation());
		theatre.setTheatreCapacity(theatreDto.getTheatreCapacity());
		theatre.setTheatreScreenType(theatreDto.getTheatreScreenType());

		return theatreRepository.save(theatre);
	}

	public List<Theatre> getTheatreByLocation(String location) {
		Optional<List<Theatre>> optional = theatreRepository.findByTheatreLocation(location);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RuntimeException("No Theatre Found for the location entered " + location);
		}
	}

	public Theatre updateTheatre(Long id, TheatreDTO theatreDto) {
		Theatre theatre = theatreRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No Theatre Found for the Id " + id));

		theatre.setTheatreName(theatreDto.getTheatreName());
		theatre.setTheatreLocation(theatreDto.getTheatreLocation());
		theatre.setTheatreCapacity(theatreDto.getTheatreCapacity());
		theatre.setTheatreScreenType(theatreDto.getTheatreScreenType());

		return theatreRepository.save(theatre);
	}

	public void deleteTheatre(Long id) {
		theatreRepository.deleteById(id);
	}

}
