package com.shyam.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTask {

	private int counter=0;
	
	//@Scheduled(fixedRate = 2000)
	 
	@Scheduled(cron = "1/5 * * * * ?")//after 5 sec
	
	private void nightlyCycle() {
		counter++;
		System.out.println("Nightly Cycle Executes "+counter);
	}
}
