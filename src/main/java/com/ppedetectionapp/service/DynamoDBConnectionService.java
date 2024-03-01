package com.ppedetectionapp.service;

import java.util.ArrayList;

/*
 * Name 	: DynamoDBConnectionService
 * Purpose 	: This class is used for Inserting, Updating an record from DynamoDB
 */

import java.util.List;

import com.ppedetectionapp.connection.DynamoDBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.entity.Floor;
import com.ppedetectionapp.entity.Picture;
import com.ppedetectionapp.entity.Wing;

@Service
public class DynamoDBConnectionService {

	@Autowired
    DynamoDBConnection ddbConn;

	public String insertBuildingStructure(List<Building> list) {

		for (Building building : list) {
			ddbConn.putRecord(building);
		}

		return "New Building Record Inserted Successfully!";
	}

	public List<Building> getAllItems() {
		List<Building> list = ddbConn.getAllItems();
		return list;
	}

	public String addFloorsToBuilding(int id, String floorName) {

		Building building = ddbConn.getItem(id);

		Floor floor = new Floor();
		floor.setFloorName(floorName);
		floor.setWingList(new ArrayList<Wing>());
		Wing wing = new Wing();
		wing.setWingName("Wing A");
		wing.setPictureList(new ArrayList<Picture>());
		floor.getWingList().add(wing);
		building.getFloorList().add(floor);

		ddbConn.updateRecord(building);

		return "New Floor Inserted Successfully to Existing Building Record!";

	}

	public String addWingsToFloor(int id, String floorName, String wingName) {
		Building building = ddbConn.getItem(id);

		for (Floor floor : building.getFloorList()) {

			if (floor.getFloorName().equals(floorName)) {
				Wing wing = new Wing();
				wing.setWingName(wingName);
				wing.setPictureList(new ArrayList<Picture>());
				floor.getWingList().add(wing);
				break;
			}

		}

		ddbConn.updateRecord(building);

		return "New Wing Inserted Successfully to existing building record !";
	}

	public String deleteProcessedPictures() {

		List<Building> buildings = ddbConn.getAllItems();

		for (Building building : buildings) {
			for (Floor floor : building.getFloorList()) {
				for (Wing wing : floor.getWingList()) {

					List<Picture> updatedPictures = new ArrayList<>();

					for (Picture picture : wing.getPictureList()) {

						if (picture.getNoOfImproperMask() == 0 && picture.getNoOfNoMask() == 0
								&& picture.getNoOfProperMask() == 0) {
							updatedPictures.add(picture);
						}

					}
					wing.setPictureList(updatedPictures);
				}
			}
		}

		return "Processed pictures deleted successfully!";

	}

	public Building getAnItemById(int id) {
		return ddbConn.getItem(id);
	}

	public String deleteWingFromFloor(int id, String floorName, String wingName) {

		Building building = ddbConn.getItem(id);

		for (Floor floor : building.getFloorList()) {

			if (floor.getFloorName().equals(floorName)) {

				List<Wing> newWingList = new ArrayList<Wing>();

				for (Wing wing : floor.getWingList()) {

					if (!wing.getWingName().equals(wingName)) {
						newWingList.add(wing);
					}

				}
				floor.setWingList(newWingList);
				break;
			}

		}
		ddbConn.updateRecord(building);
		return " Wing deleted Successfully to existing building record !";
	}

	public String deleteFloorFromBuilding(int id, String floorName) {

		Building building = ddbConn.getItem(id);

		List<Floor> newFloorList = new ArrayList<Floor>();

		for (Floor floor : building.getFloorList()) {

			if (!floor.getFloorName().equals(floorName)) {
				newFloorList.add(floor);
			}

		}

		building.setFloorList(newFloorList);

		ddbConn.updateRecord(building);

		return " Floor deleted Successfully from existing building record !";

	}

	public String deleteBuilding(int id) {
		Building building = ddbConn.getItem(id);
		ddbConn.deleteBuildingRecord(building);
		return "Building " + building.getBuildingName() + " is deleted successfully!";
	}

	public List<String> getWingNamesOfFloor(int id, String floorName) {
		
		Building building = ddbConn.getItem(id);
		
		for (Floor floor : building.getFloorList()) {
			if(floor.getFloorName().equals(floorName)) {
				List<String> wingNames = new ArrayList<>();
				for (Wing wing : floor.getWingList()) {
					wingNames.add(wing.getWingName());
				}
				return wingNames;
			}
		}
		
		return null;
	}

}
