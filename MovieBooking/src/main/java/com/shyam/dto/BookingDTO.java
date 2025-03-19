package com.shyam.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.shyam.entity.BookingStatus;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class BookingDTO {

	private Integer numberOfSeats;
	private LocalDateTime bookingTime;
	private Double price;
	private BookingStatus bookingStatus;
	
	private List<String> seatNumbers;

	private Long  userId;

	private Long showId;
}
