package com.ppedetectionapp.controller;

/*
 * Name 	: PPEDetectionAppController
 * Purpose 	: Controller for Spring boot application
*/

import java.util.List;

import com.ppedetectionapp.service.DynamoDBConnectionService;
import com.ppedetectionapp.service.S3ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.service.RekognitionConnectionService;

@RestController
public class PPEDetectionAppController {

	@Autowired
    S3ConnectionService s3Service;

	@Autowired
	RekognitionConnectionService rekognitionService;

	@Autowired
    DynamoDBConnectionService dynamoDbService;

	// POST http://localhost:5000/building
	@PostMapping("/building")
	public String insertBuildingStructure(@RequestBody List<Building> list) {

		return dynamoDbService.insertBuildingStructure(list);

	}

	// GET http://localhost:5000/building
	@GetMapping("/building/{id}")
	public Building getABuildingById(@PathVariable("id") int id) {

		return dynamoDbService.getAnItemById(id);

	}

	// GET http://localhost:5000/building
	@GetMapping("/building")
	public List<Building> getResultsForAllBuildings() {

		return rekognitionService.getResultsForAllBuildings(dynamoDbService.getAllItems());

	}

	// POST http://localhost:5000/building/floor
	@PostMapping("/building/floor")
	public String addFloorsToBuilding(@RequestParam("id") int id, @RequestParam("floorName") String floorName) {
		
		return dynamoDbService.addFloorsToBuilding(id, floorName);

	}

	// POST http://localhost:5000/building/wing
	@PostMapping("/building/wing")
	public String addWingsToFloor(@RequestParam("id") int id, @RequestParam("floorName") String floorName,
			@RequestParam("wingName") String wingName) {

		return dynamoDbService.addWingsToFloor(id, floorName, wingName);

	}

	@GetMapping("/getwings/{id}/{floorName}")
	public List<String> getWingNamesOfFloor(@PathVariable("id") int id, @PathVariable("floorName") String floorName){

		return dynamoDbService.getWingNamesOfFloor(id, floorName.replaceAll("_", " "));
		
	}
	
	// Testing Endpoint getfloorcount
	@PostMapping("/getfloorcount/{id}")
	public int getFloorCount(@PathVariable("id") int building_id) {
		
		return dynamoDbService.getAnItemById(building_id).getFloorList().size();

	}

	// DELETE http://localhost:5000/building
	@DeleteMapping("/building")
	public String deleteProcessedPicturesFromAllBuildings() {
		return dynamoDbService.deleteProcessedPictures();
	}

	// DELETE http://localhost:5000/building/Floor 2/WingB
	@DeleteMapping("/building/{id}/{floorName}/{wingName}")
	public String deleteWingFromFloor(@PathVariable("id") int id, @PathVariable("floorName") String floorName,
			@PathVariable("wingName") String wingName) {
		return dynamoDbService.deleteWingFromFloor(id, floorName, wingName);
	}

	// DELETE http://localhost:5000/building/3/Floor 2
	@DeleteMapping("/building/{id}/{floorName}")
	public String deleteFloorFromBuilding(@PathVariable("id") int id, @PathVariable("floorName") String floorName) {
		return dynamoDbService.deleteFloorFromBuilding(id, floorName);
	}

	@DeleteMapping("/building/{id}")
	public String deleteBuilding(@PathVariable("id") int id) {
		return dynamoDbService.deleteBuilding(id);
	}

}
