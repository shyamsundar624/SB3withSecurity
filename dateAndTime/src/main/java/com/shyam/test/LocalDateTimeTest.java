package com.shyam.test;

import java.time.LocalDateTime;

public class LocalDateTimeTest {
public static void main(String[] args) {
	//input Format: "yyyy-MM-dd'T'HH:mm:ss.nnn"
	LocalDateTime localDateTime=LocalDateTime.now();
	
	System.out.println(localDateTime);
	LocalDateTime plusDays = localDateTime.plusDays(2);
	System.out.println(plusDays);
}
}
