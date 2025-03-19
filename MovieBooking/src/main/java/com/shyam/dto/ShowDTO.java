package com.shyam.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowDTO {

	private LocalDateTime showTime;
	private Double price;
	
	private Long movieId;
	private Long theatreId;
	
}
