package com.shyam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.entity.DateAndTime;
import com.shyam.service.DateAndTimeService;

@RestController
public class DateAndTimeController {
	@Autowired
	private DateAndTimeService service;

	@PostMapping("/save")
	public ResponseEntity<?> saveDateAndTime(@RequestBody DateAndTime dateAndTime){
		return service.saveDate(dateAndTime);
	}
}
