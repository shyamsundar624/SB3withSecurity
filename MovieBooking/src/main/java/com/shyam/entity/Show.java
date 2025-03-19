package com.shyam.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="shows")
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime showTime;
	private Double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="movie_id",nullable = false)
	private Theatre theatre;

	@ManyToOne(fetch =  FetchType.EAGER)
	@JoinColumn(name = "theatre_id",nullable = false)
	private Movie movie;
	
	@OneToMany(mappedBy = "show",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Booking> bookings;
}
