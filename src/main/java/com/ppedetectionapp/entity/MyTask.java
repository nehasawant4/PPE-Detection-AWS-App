package com.ppedetectionapp.entity;

import java.util.List;
import java.util.TimerTask;

import com.ppedetectionapp.connection.DynamoDBConnection;

public class MyTask extends TimerTask {
	public static int i = 0;

	public void run() {
		System.out.println("Hello, I'm timer, running iteration: " + ++i);
		DynamoDBConnection ddbConn = new DynamoDBConnection();
		List<Building> buildings = ddbConn.getAllItems();
		for (Building building : buildings) {
			for (Floor floor : building.getFloorList()) {
				for (Wing wing : floor.getWingList()) {
					wing.setNoOfImproperMaskOnWing(0);
					wing.setNoOfNoMaskOnWing(0);
					wing.setNoOfProperMaskOnWing(0);
				}
				floor.setNoOfImproperMaskOnFloor(0);
				floor.setNoOfNoMaskOnFloor(0);
				floor.setNoOfProperMaskOnFloor(0);
			}
			
			building.setNoOfImproperMaskInBuilding(0);
			building.setNoOfNoMaskInBuilding(0);
			building.setNoOfProperMaskInBuilding(0);
			
		}

		for (Building building : buildings) {
			ddbConn.updateRecord(building);
		}
	}
}
