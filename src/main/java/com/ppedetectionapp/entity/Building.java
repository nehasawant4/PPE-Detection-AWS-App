package com.ppedetectionapp.entity;

import java.util.List;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Building 
{
	private int id;
	private String buildingName;
	private int noOfProperMaskInBuilding;
	private int noOfImproperMaskInBuilding;
	private int noOfNoMaskInBuilding;
	private List<Floor> floorList;

	//To declare this attribute as the Primary Key
	@DynamoDbPartitionKey
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getBuildingName() 
	{
		return buildingName;
	}

	public void setBuildingName(String buildingName) 
	{
		this.buildingName = buildingName;
	}

	public int getNoOfProperMaskInBuilding()
	{
		return noOfProperMaskInBuilding;
	}

	public void setNoOfProperMaskInBuilding(int noOfProperMaskInBuilding)
	{
		this.noOfProperMaskInBuilding = noOfProperMaskInBuilding;
	}

	public int getNoOfImproperMaskInBuilding() 
	{
		return noOfImproperMaskInBuilding;
	}

	public void setNoOfImproperMaskInBuilding(int noOfImproperMaskInBuilding)
	{
		this.noOfImproperMaskInBuilding = noOfImproperMaskInBuilding;
	}

	public int getNoOfNoMaskInBuilding() 
	{
		return noOfNoMaskInBuilding;
	}

	public void setNoOfNoMaskInBuilding(int noOfNoMaskInBuilding) 
	{
		this.noOfNoMaskInBuilding = noOfNoMaskInBuilding;
	}

	public List<Floor> getFloorList() {
		return floorList;
	}

	public void setFloorList(List<Floor> floorList) {
		this.floorList = floorList;
	}

	@Override
	public String toString() {
		return "Building [id=" + id + ", buildingName=" + buildingName + ", noOfProperMaskInBuilding="
				+ noOfProperMaskInBuilding + ", noOfImproperMaskInBuilding=" + noOfImproperMaskInBuilding
				+ ", noOfNoMaskInBuilding=" + noOfNoMaskInBuilding + ", floorList=" + floorList + "]";
	}
	
}
