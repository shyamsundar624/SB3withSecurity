package com.shyam.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DateAndTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate localDate;
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime localTime;
	private LocalDateTime localDateTime;
	private OffsetDateTime offsetDateTime;
	private ZonedDateTime zonedDateTime;
	private Date legacyDate;

}
