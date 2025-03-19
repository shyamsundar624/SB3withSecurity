package com.shyam.test;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeTest {
public static void main(String[] args) {
	ZonedDateTime dateTime=ZonedDateTime.now();
	System.out.println(dateTime);

	ZoneId of = ZoneId.of("America/New_York");
	System.out.println(of);

	OffsetDateTime usa=OffsetDateTime.now(of);
	System.out.println(usa);
}
}
