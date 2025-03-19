package com.shyam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shyam.entity.DateAndTime;
import com.shyam.repo.DateAndTimeRepository;

@Service
public class DateAndTimeService {

	@Autowired
	private DateAndTimeRepository repo;
	
	public ResponseEntity<?> saveDate(DateAndTime dateAndTime){
		repo.save(dateAndTime);
		return ResponseEntity.ok().build();
	}
}
