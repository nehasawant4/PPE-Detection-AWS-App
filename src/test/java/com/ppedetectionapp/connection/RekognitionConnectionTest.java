package com.ppedetectionapp.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.entity.Floor;
import com.ppedetectionapp.entity.Picture;
import com.ppedetectionapp.entity.Wing;
import com.ppedetectionapp.service.DynamoDBConnectionService;
import com.ppedetectionapp.service.RekognitionConnectionService;
import com.ppedetectionapp.service.S3ConnectionService;

@SpringBootTest
class RekognitionConnectionTest{

	@Autowired
	S3ConnectionService s3Service;

	@Autowired
	RekognitionConnectionService rekognitionService;

	@Autowired
	DynamoDBConnectionService dynamoDbService;

	

	@Test
	void rekognitionCheck() {

		List<Building> list = rekognitionService.getResultsForAllBuildings(dynamoDbService.getAllItems());
		
		for (Building building : list) {
			if (building.getId() == 1)
			{
				for (Floor floor : building.getFloorList()) {
					if (floor.getFloorName().equals("GroundFloor"))
					{
						for (Wing wing : floor.getWingList()) {
							if (wing.getWingName().equals("WingB"))
							{
								for (Picture picture : wing.getPictureList()) {
									if (picture.getPictureName().equals("kid.jpg")) 
									{
										assertEquals(picture.getNoOfImproperMask(),4);
										assertEquals(picture.getNoOfNoMask(),1);
										assertEquals(picture.getNoOfProperMask(),2);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
