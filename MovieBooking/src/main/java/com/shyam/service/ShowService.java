package com.shyam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.shyam.dto.ShowDTO;
import com.shyam.entity.Booking;
import com.shyam.entity.Movie;
import com.shyam.entity.Show;
import com.shyam.entity.Theatre;
import com.shyam.repository.MovieRepository;
import com.shyam.repository.ShowRepository;
import com.shyam.repository.TheatreRepository;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TheatreRepository theatreRepository;

	public Show createShow(ShowDTO showDTO) {

		Movie movie = movieRepository.findById(showDTO.getMovieId())
				.orElseThrow(() -> new RuntimeException("No Movie Found"));
		Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
				.orElseThrow(() -> new RuntimeException("No Theatre Found"));

		Show show = new Show();
		show.setShowTime(showDTO.getShowTime());
		show.setPrice(showDTO.getPrice());
		show.setMovie(movie);
		show.setTheatre(theatre);

		return showRepository.save(show);

	}

	public List<Show> getAllshows() {
		// TODO Auto-generated method stub
		return showRepository.findAll();
	}

	public List<Show> getShowsByMovie(Long movieId) {
		Optional<List<Show>> showList = showRepository.findByMovieId(movieId);
		if (showList.isPresent()) {
			return showList.get();
		} else {
			throw new RuntimeException("No Show Available for Movie");
		}
	}

	public List<Show> getShowsByTheatre(Long theatreId) {
		Optional<List<Show>> theatreList = showRepository.findByTheatreId(theatreId);
		if (theatreList.isPresent()) {
			return theatreList.get();
		} else {
			throw new RuntimeException("No Show Available fot Theatre");
		}
	}

	public Show updateShow(Long id, ShowDTO showDTO) {
		Movie movie = movieRepository.findById(showDTO.getMovieId())
				.orElseThrow(() -> new RuntimeException("No Movie Found"));
		Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
				.orElseThrow(() -> new RuntimeException("No Theatre Found"));

		Show show = showRepository.findById(id).orElseThrow(()-> new RuntimeException("No Show Available for "+ id));
		show.setShowTime(showDTO.getShowTime());
		show.setPrice(showDTO.getPrice());
		show.setMovie(movie);
		show.setTheatre(theatre);

		return showRepository.save(show);
	}

	public void deleteShow(Long id) {
		if(!showRepository.existsById(id)) {
			throw new RuntimeException("No Show Available for Id "+id);
		}
		List<Booking> bookings=showRepository.findById(id).get().getBookings();
		if(bookings.isEmpty()) {
		showRepository.deleteById(id);
		}else {
			throw new RuntimeException("Can't Delete show with existing bookings");
		}
	}
}
