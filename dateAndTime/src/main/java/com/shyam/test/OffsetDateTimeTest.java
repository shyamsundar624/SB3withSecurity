package com.shyam.test;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.OffsetDateTime;

public class OffsetDateTimeTest {
public static void main(String[] args) {
	OffsetDateTime offsetDateTime=OffsetDateTime.now();
	
	System.out.println(offsetDateTime);
	DayOfWeek dayOfWeek = offsetDateTime.getDayOfWeek();
	System.out.println(dayOfWeek);
	Month month = offsetDateTime.getMonth();
	System.out.println(month);
	int dayOfMonth = offsetDateTime.getDayOfMonth();
	System.out.println(dayOfMonth);
}
}
