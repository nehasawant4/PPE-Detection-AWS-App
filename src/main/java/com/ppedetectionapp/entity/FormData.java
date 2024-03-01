package com.ppedetectionapp.entity;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class FormData {
	private int id;
	private String wingName;
	private String floorName;
	private MultipartFile[] images;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWingName() {
		return wingName;
	}
	public void setWingName(String wingName) {
		this.wingName = wingName;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public MultipartFile[] getImages() {
		return images;
	}
	public void setImages(MultipartFile[] images) {
		this.images = images;
	}
	
	@Override
	public String toString() {
		return "FormData [id=" + id + ", wingName=" + wingName + ", floorName=" + floorName + ", images="
				+ Arrays.toString(images) + "]";
	}
	
	
	
}
