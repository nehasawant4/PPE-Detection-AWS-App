package com.ppedetectionapp.service;

import java.io.IOException;

/*
 * Name 	: S3ConnectionService
 * Purpose	: It is used call S3Connection class.Provide functionality as 
 * 						1. List of Bucket object
 * 						2. List of bucket names
 * 						3. Upload files/images to S3 
 */

import com.ppedetectionapp.connection.DynamoDBConnection;
import com.ppedetectionapp.connection.S3Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.entity.Floor;
import com.ppedetectionapp.entity.Picture;
import com.ppedetectionapp.entity.Wing;

@Service
public class S3ConnectionService {
	
	@Autowired
    S3Connection s3Conn;
	
	@Autowired
    DynamoDBConnection ddbConn;
	
	@Autowired
	RekognitionConnectionService rekognitionConnectionService;

	public String updloadImages(int id, String floorName, String wingName, MultipartFile[] images) {
		
		for (MultipartFile image : images) {
			
			try {
				if (!s3Conn.checkIfExistOrNot(image.getOriginalFilename(), "photosformaskdetection"))
					s3Conn.putS3Object("photosformaskdetection", image.getOriginalFilename(), image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Building building = ddbConn.getItem(id);

		for (Floor floor : building.getFloorList()) {

			if (floor.getFloorName().equals(floorName)) {
				for (Wing wing : floor.getWingList()) {

					if (wing.getWingName().equals(wingName)) {

						for (MultipartFile image : images) {
							Picture picture = new Picture();
							picture.setPictureName(image.getOriginalFilename());
							wing.getPictureList().add(picture);
						}

						break;
					}
				}
			}

		}

		rekognitionConnectionService.getResultsForOneBuilding(building);

		return "Images Uploaded in S3 and added in DynamoDB Successfully!";

	}

}
