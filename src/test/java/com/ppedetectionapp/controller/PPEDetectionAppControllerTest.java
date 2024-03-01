package com.ppedetectionapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppedetectionapp.entity.Building;
import com.ppedetectionapp.entity.Floor;
import com.ppedetectionapp.entity.Picture;
import com.ppedetectionapp.entity.Wing;
import com.ppedetectionapp.service.DynamoDBConnectionService;
import com.ppedetectionapp.service.RekognitionConnectionService;
import com.ppedetectionapp.service.S3ConnectionService;

@WebMvcTest(value = PPEDetectionAppController.class)
class PPEDetectionAppControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	RekognitionConnectionService rekognitionConnectionService;

	@MockBean
	DynamoDBConnectionService dynamoDBConnectionService;

	@MockBean
	S3ConnectionService s3ConnectionService;

	@Test
	public void testInsertBuildingStructure() {

		Wing mockWing = new Wing();
		mockWing.setWingName("WingA");
		mockWing.setPictureList(new ArrayList<Picture>());

		Floor mockFloor = new Floor();
		mockFloor.setFloorName("GroundFloor");
		List<Wing> mockWingList = new ArrayList<Wing>();
		mockWingList.add(mockWing);
		mockFloor.setWingList(mockWingList);

		Building mockBuilding = new Building();
		mockBuilding.setId(1);
		mockBuilding.setBuildingName("Yajurveda");
		List<Floor> mockFloorList = new ArrayList<Floor>();
		mockFloorList.add(mockFloor);
		mockBuilding.setFloorList(mockFloorList);

		List<Building> mockBuildingList = new ArrayList<>();
		mockBuildingList.add(mockBuilding);

		ObjectMapper objectMapper = new ObjectMapper();

		String inputJson = null;

		try {
			inputJson = objectMapper.writeValueAsString(mockBuildingList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String URI = "/building";

		Mockito.when(dynamoDBConnectionService.insertBuildingStructure(Mockito.anyList()))
				.thenReturn("New Building Record Inserted Successfully!");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = null;
		;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MockHttpServletResponse response = result.getResponse();

		String output = null;
		try {
			output = response.getContentAsString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		assertEquals(output, "New Building Record Inserted Successfully!");
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetResultsForAllBuildings() {

		Wing mockWing1 = new Wing();
		mockWing1.setWingName("WingA");
		mockWing1.setPictureList(new ArrayList<Picture>());

		Floor mockFloor1 = new Floor();
		mockFloor1.setFloorName("GroundFloor");
		List<Wing> mockWingList1 = new ArrayList<Wing>();
		mockWingList1.add(mockWing1);
		mockFloor1.setWingList(mockWingList1);

		Building mockBuilding1 = new Building();
		mockBuilding1.setId(1);
		mockBuilding1.setBuildingName("Yajurveda");

		Wing mockWing2 = new Wing();
		mockWing2.setWingName("WingB");
		mockWing2.setPictureList(new ArrayList<Picture>());

		Floor mockFloor2 = new Floor();
		mockFloor2.setFloorName("FirstFloor");
		List<Wing> mockWingList2 = new ArrayList<Wing>();
		mockWingList2.add(mockWing2);
		mockFloor2.setWingList(mockWingList2);

		Building mockBuilding2 = new Building();
		mockBuilding2.setId(2);
		mockBuilding2.setBuildingName("Atharvaveda");

		List<Floor> mockFloorList2 = new ArrayList<Floor>();
		mockFloorList2.add(mockFloor2);
		mockBuilding2.setFloorList(mockFloorList2);

		List<Building> mockBuildingList = new ArrayList<>();
		mockBuildingList.add(mockBuilding1);
		mockBuildingList.add(mockBuilding2);

		Mockito.when(rekognitionConnectionService.getResultsForAllBuildings(Mockito.anyList()))
				.thenReturn(mockBuildingList);

		String URI = "/building";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = null;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();

		String expectedJson = null;
		try {
			expectedJson = objectMapper.writeValueAsString(mockBuildingList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String outputInJson = null;
		try {
			outputInJson = result.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		assertEquals(expectedJson, outputInJson);

	}

}
