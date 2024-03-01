package com.ppedetectionapp.entity;

import java.util.List;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Wing 
{
	private String wingName;
	private int noOfProperMaskOnWing;
	private int noOfImproperMaskOnWing;
	private int noOfNoMaskOnWing;
	private List<Picture> pictureList;

	public String getWingName()
	{
		return wingName;
	}

	public void setWingName(String wingName) 
	{
		this.wingName = wingName;
	}

	public int getNoOfProperMaskOnWing()
	{
		return noOfProperMaskOnWing;
	}

	public void setNoOfProperMaskOnWing(int noOfProperMaskOnWing) 
	{
		this.noOfProperMaskOnWing = noOfProperMaskOnWing;
	}

	public int getNoOfImproperMaskOnWing() 
	{
		return noOfImproperMaskOnWing;
	}

	public void setNoOfImproperMaskOnWing(int noOfImproperMaskOnWing)
	{
		this.noOfImproperMaskOnWing = noOfImproperMaskOnWing;
	}

	public int getNoOfNoMaskOnWing() 
	{
		return noOfNoMaskOnWing;
	}

	public void setNoOfNoMaskOnWing(int noOfNoMaskOnWing) 
	{
		this.noOfNoMaskOnWing = noOfNoMaskOnWing;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	@Override
	public String toString() {
		return "Wing [wingName=" + wingName + ", noOfProperMaskOnWing=" + noOfProperMaskOnWing
				+ ", noOfImproperMaskOnWing=" + noOfImproperMaskOnWing + ", noOfNoMaskOnWing=" + noOfNoMaskOnWing
				+ ", pictureList=" + pictureList + "]";
	}

}
