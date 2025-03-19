package com.shyam.test;

import java.time.LocalTime;

public class LocalTimeTest {
	public static void main(String[] args) {
//input format: HH:mm:ss

		LocalTime now = LocalTime.now();
		System.out.println(now);
		
		LocalTime specific=LocalTime.of(13, 12,58);
		System.out.println(specific);
		
		LocalTime plusHours = now.plusHours(2);
		System.out.println(plusHours);

	}
}
