package com.ppedetectionapp.connection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/*
 * Name		: RekognitionConnection 
 * Purpose	: Class responsible for analysis of images
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.entity.Floor;
import com.ppedetectionapp.entity.Picture;
import com.ppedetectionapp.entity.Wing;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectProtectiveEquipmentRequest;
import software.amazon.awssdk.services.rekognition.model.DetectProtectiveEquipmentResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.ProtectiveEquipmentSummarizationAttributes;

@Component
public class RekognitionConnection {

	@Autowired
	S3Connection s3Conn;

	private RekognitionClient rekognitionClient;
	private ProtectiveEquipmentSummarizationAttributes summaryAttributes; // Specify which types of PPE to summarizez

	@Autowired
	public RekognitionConnection(@Value("${ppe.type}") String ppeType) {

		this.rekognitionClient = RekognitionClient.builder().region(Region.AP_SOUTH_1).build();
		this.summaryAttributes = ProtectiveEquipmentSummarizationAttributes.builder().minConfidence(80F)
				.requiredEquipmentTypesWithStrings(ppeType).build(); // PersonsWithRequiredEquipment such as
																		// HEAD_COVER,HAND_COVER
	}

	public Building analyzePicturesForOne(String bucketName, Building building) {

		for (Floor floor : building.getFloorList()) {
			for (Wing wing : floor.getWingList()) {
				for (Picture picture : wing.getPictureList()) {
					if (picture.getNoOfImproperMask() == 0 && picture.getNoOfProperMask() == 0
							&& picture.getNoOfNoMask() == 0) {

						String picName = picture.getPictureName();

						byte[] imageBytes = s3Conn.downloadObject(bucketName, picName);
						InputStream is = new ByteArrayInputStream(imageBytes);
						SdkBytes sourceBytes = SdkBytes.fromInputStream(is);
						Image imageObject = Image.builder().bytes(sourceBytes).build();

						DetectProtectiveEquipmentRequest request = DetectProtectiveEquipmentRequest.builder()
								.image(imageObject).summarizationAttributes(summaryAttributes).build();

						DetectProtectiveEquipmentResponse result = rekognitionClient.detectProtectiveEquipment(request);

						int noOfProperMask = result.summary().personsWithRequiredEquipment().size();
						picture.setNoOfProperMask(noOfProperMask);

						int noOfImproperMask = result.summary().personsIndeterminate().size();
						picture.setNoOfImproperMask(noOfImproperMask);

						int noOfNoMask = result.summary().personsWithoutRequiredEquipment().size();
						picture.setNoOfNoMask(noOfNoMask);

						wing.setNoOfProperMaskOnWing(wing.getNoOfProperMaskOnWing() + noOfProperMask);
						wing.setNoOfImproperMaskOnWing(wing.getNoOfImproperMaskOnWing() + noOfImproperMask);
						wing.setNoOfNoMaskOnWing(wing.getNoOfNoMaskOnWing() + noOfNoMask);

						floor.setNoOfProperMaskOnFloor(floor.getNoOfProperMaskOnFloor() + noOfProperMask);
						floor.setNoOfImproperMaskOnFloor(floor.getNoOfImproperMaskOnFloor() + noOfImproperMask);
						floor.setNoOfNoMaskOnFloor(floor.getNoOfNoMaskOnFloor() + noOfNoMask);

						building.setNoOfProperMaskInBuilding(building.getNoOfProperMaskInBuilding() + noOfProperMask);
						building.setNoOfImproperMaskInBuilding(
								building.getNoOfImproperMaskInBuilding() + noOfImproperMask);
						building.setNoOfNoMaskInBuilding(building.getNoOfNoMaskInBuilding() + noOfNoMask);

					}

				}

			}

		}

		return building;

	}

	public List<Building> analyzePicturesForAll(String bucketName, List<Building> list) {

		List<Building> processedBuildings = new ArrayList<>();

		for (Building building : list) {
			processedBuildings.add(analyzePicturesForOne(bucketName, building));
		}

		return processedBuildings;

	}

}
