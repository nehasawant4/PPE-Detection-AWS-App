package com.ppedetectionapp.entity;

import java.util.List;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Floor
{
	private String floorName;
	private int noOfProperMaskOnFloor;
	private int noOfImproperMaskOnFloor;
	private int noOfNoMaskOnFloor;
	private List<Wing> wingList;

	public String getFloorName() 
	{
		return floorName;
	}

	public void setFloorName(String floorName) 
	{
		this.floorName = floorName;
	}

	public int getNoOfProperMaskOnFloor()
	{
		return noOfProperMaskOnFloor;
	}

	public void setNoOfProperMaskOnFloor(int noOfProperMaskOnFloor) 
	{
		this.noOfProperMaskOnFloor = noOfProperMaskOnFloor;
	}

	public int getNoOfImproperMaskOnFloor() 
	{
		return noOfImproperMaskOnFloor;
	}

	public void setNoOfImproperMaskOnFloor(int noOfImproperMaskOnFloor) 
	{
		this.noOfImproperMaskOnFloor = noOfImproperMaskOnFloor;
	}

	public int getNoOfNoMaskOnFloor() 
	{
		return noOfNoMaskOnFloor;
	}

	public void setNoOfNoMaskOnFloor(int noOfNoMaskOnFloor)
	{
		this.noOfNoMaskOnFloor = noOfNoMaskOnFloor;
	}

	public List<Wing> getWingList() {
		return wingList;
	}

	public void setWingList(List<Wing> wingList) {
		this.wingList = wingList;
	}

	@Override
	public String toString() {
		return "Floor [floorName=" + floorName + ", noOfProperMaskOnFloor=" + noOfProperMaskOnFloor
				+ ", noOfImproperMaskOnFloor=" + noOfImproperMaskOnFloor + ", noOfNoMaskOnFloor=" + noOfNoMaskOnFloor
				+ ", wingList=" + wingList + "]";
	}

}
