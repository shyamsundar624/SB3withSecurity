package com.shyam.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {
	private String name;
	private String description;
	private String genre;
	private Integer duration;
	private LocalDate releasedDate;
	private String language;
}
