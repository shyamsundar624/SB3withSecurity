package com.shyam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.dto.BookingDTO;
import com.shyam.entity.Booking;
import com.shyam.entity.BookingStatus;
import com.shyam.entity.Show;
import com.shyam.entity.User;
import com.shyam.repository.BookingRepository;
import com.shyam.repository.ShowRepository;

@Service
public class BookingServie {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private UserRepository userRepository;

	public Booking createBooking(BookingDTO bookingDTO) {
		Show show = showRepository.findById(bookingDTO.getShowId())
				.orElseThrow(() -> new RuntimeException("No Show Found"));
		if (!isSeatsAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
			throw new RuntimeException("Not Enough Seat Are Available");
		}
		if (bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()) {
			throw new RuntimeException("Seat Numbers and Number of Seats must be Equal");
		}
		validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

		User user = userRepository.findById(bookingDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		Booking booking = new Booking();

		booking.setUser(user);
		booking.setShow(show);

		booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
		booking.setSeatNumbers(booking.getSeatNumbers());
		booking.setPrice(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
		booking.setBookingTime(bookingDTO.getBookingTime());
		booking.setBookingStatus(BookingStatus.PENDING);

		return bookingRepository.save(booking);
	}

	private Double calculateTotalAmount(Double price, Integer numberOfSeats) {
		return price * numberOfSeats;
	}

	public List<Booking> getUserBooking(Long userId) {
		// TODO Auto-generated method stub
		return bookingRepository.findByUserId(userId);
	}

	public List<Booking> getShowBooking(Long showId) {
		// TODO Auto-generated method stub
		return bookingRepository.findByShowId(showId);
	}

	public Booking confirmBooking(Long id) {
		Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking Not Found"));
		if (booking.getBookingStatus() != BookingStatus.PENDING) {
			throw new RuntimeException("Booking is not in pending status");
		}

		// Ask for Payment API
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		return bookingRepository.save(booking);
	}

	public Booking cancelBooking(Long id) {
		Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking Not Found"));

		validateCancellation(booking);
		booking.setBookingStatus(BookingStatus.CANCELLED);
		return bookingRepository.save(booking);
	}

	public List<Booking> getBookingByStatus(BookingStatus bookingStatus) {
		// TODO Auto-generated method stub
		return bookingRepository.findByBookingStatus(bookingStatus);
	}

	private boolean isSeatsAvailable(Long id, Integer numberOfSeats) {
		Show show = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not Found"));
		int bookedSeat = show.getBookings().stream().filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED)
				.mapToInt(Booking::getNumberOfSeats).sum();

		return (show.getTheatre().getTheatreCapacity() - bookedSeat) >= numberOfSeats;
	}

	private void validateDuplicateSeats(Long id, List<String> seatNumber) {
		Show show = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not Found"));

		Set<String> occupiesSeats = show.getBookings().stream()
				.filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED).flatMap(b -> b.getSeatNumbers().stream())
				.collect(Collectors.toSet());

		List<String> duplicateSeats = seatNumber.stream().filter(occupiesSeats::contains).collect(Collectors.toList());

		if (!duplicateSeats.isEmpty()) {
			throw new RuntimeException("Seats are already Books");
		}
	}

	private void validateCancellation(Booking booking) {

		LocalDateTime showTime = booking.getShow().getShowTime();

		LocalDateTime deadlineTime = showTime.minusHours(2);
		if (LocalDateTime.now().isAfter(deadlineTime)) {
			throw new RuntimeException("Can't Cancel the Booking");
		}

		if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
			throw new RuntimeException("Booking has Already Cancelled");
		}
	}

}
