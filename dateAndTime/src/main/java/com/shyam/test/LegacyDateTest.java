package com.shyam.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LegacyDateTest {
public static void main(String[] args) {
	Date date=new Date();
	System.out.println(date);
	
	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
}
}
