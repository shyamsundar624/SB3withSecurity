package com.shyam.test;

import java.time.LocalDate;

public class LocalDateTest {
public static void main(String[] args) {
	//Input Format "yyyy-MM-dd"
	LocalDate today=LocalDate.now();
	System.out.println(today);
	
	LocalDate specific=LocalDate.of(2026, 05, 3);
	System.out.println(specific);
	
	LocalDate futureDate=today.plusDays(12);
	System.out.println(futureDate);
	
	LocalDate pastDate=today.minusYears(25);
	System.out.println(pastDate);
	
	boolean after = today.isAfter(pastDate);
	System.out.println(after);
}
}
