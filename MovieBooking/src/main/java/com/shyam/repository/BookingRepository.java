package com.shyam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.Booking;
import com.shyam.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long>{

	List<Booking> findByUserId(Long userId);

	List<Booking> findByShowId(Long showId);

	List<Booking> findByBookingStatus(BookingStatus bookingStatus);

}
