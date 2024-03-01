package com.ppedetectionapp.service;

/*
 * Name		: RekognitionConnectionService 
 * Purpose	: This is responsible for calling Rekognition API of AWS 
 */

import java.util.List;

import com.ppedetectionapp.connection.DynamoDBConnection;
import com.ppedetectionapp.connection.RekognitionConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppedetectionapp.entity.Building;

@Service
public class RekognitionConnectionService {
	
	@Autowired
    RekognitionConnection rekognitionConn;
	
	@Autowired
    DynamoDBConnection ddbConn;

	public List<Building> getResultsForAllBuildings(List<Building> list) {
		
		List<Building> buildings = rekognitionConn.analyzePicturesForAll("photosformaskdetection", list);
		
		for (Building building : buildings) {
			ddbConn.updateRecord(building);
		}
		
		return buildings;
		
	}

	public Building getResultsForOneBuilding(Building building) {

		Building processedBuilding = rekognitionConn.analyzePicturesForOne("photosformaskdetection", building);

		ddbConn.updateRecord(processedBuilding);

		return processedBuilding;

	}
}
