package com.ppedetectionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PPEDetectionApp {

	public static void main(String[] args) {
		SpringApplication.run(PPEDetectionApp.class, args);
		
//		Uncomment following code to turn on timer	
//		Timer timer = new Timer();
//		TimerTask task = new MyTask();
//		timer.schedule(task, 120000, 120000); // 2000 - delay (can set to 0 for immediate execution), 5000 is frequency
		
	}

}
