package com.ppedetectionapp.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Picture 
{
	private String pictureName;
	private int noOfProperMask;
	private int noOfImproperMask;
	private int noOfNoMask;

	public String getPictureName() 
	{
		return pictureName;
	}

	public void setPictureName(String pictureName) 
	{
		this.pictureName = pictureName;
	}

	public int getNoOfProperMask() 
	{
		return noOfProperMask;
	}

	public void setNoOfProperMask(int noOfProperMask) 
	{
		this.noOfProperMask = noOfProperMask;
	}

	public int getNoOfImproperMask()
	{
		return noOfImproperMask;
	}

	public void setNoOfImproperMask(int noOfImproperMask)
	{
		this.noOfImproperMask = noOfImproperMask;
	}

	public int getNoOfNoMask() 
	{
		return noOfNoMask;
	}

	public void setNoOfNoMask(int noOfNoMask)
	{
		this.noOfNoMask = noOfNoMask;
	}

	@Override
	public String toString() 
	{
		return "Picture [pictureName=" + pictureName + ", noOfProperMask=" + noOfProperMask + ", noOfImproperMask="
				+ noOfImproperMask + ", noOfNoMask=" + noOfNoMask + "]";
	}
	
}
