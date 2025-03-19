package com.shyam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.shyam.dto.BookingDTO;
import com.shyam.entity.Booking;
import com.shyam.entity.BookingStatus;
import com.shyam.service.BookingServie;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private BookingServie bookingServie;

	@PostMapping("/createbooking")
	public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO){
		return ResponseEntity.ok(bookingServie.createBooking(bookingDTO));
	}
	
	@GetMapping("/getuserbookings/{id}")
	public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long id){
		return ResponseEntity.ok(bookingServie.getUserBooking(id));
	}


	@GetMapping("/getshowbooking/{id}")
	public ResponseEntity<List<Booking>> getShowBooking(@PathVariable Long id){
		return ResponseEntity.ok(bookingServie.getShowBooking(id));
	}
	
	@PutMapping("/{id}/confirm")
	public ResponseEntity<Booking> confirmBooking(@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingServie.confirmBooking(id));
	}
	
	@PutMapping("/{id}/cancel")
	public ResponseEntity<Booking> cancelBooking(@PathVariable Long id){
		return ResponseEntity.ok(bookingServie.cancelBooking(id));
	}
	
	@GetMapping("/getbookingbystatus/{bookingStatus}")
	public ResponseEntity<List<Booking>> getBookingByStatus(@PathVariable BookingStatus bookingStatus){
		return ResponseEntity.ok(bookingServie.getBookingByStatus(bookingStatus));
	}
}
