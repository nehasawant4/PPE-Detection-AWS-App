package com.ppedetectionapp.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PictureTest {
	
	@Test
	public void contextLoad()
	{
		Picture picture = new Picture();
		assertThat(picture).isNotNull();
	}
	
	@Test
	public void setPictureName()
	{
		Picture picture = new Picture();
		picture.setPictureName("half.jpg");
		assertEquals("half.jpg", picture.getPictureName());
	}
	
	@Test
	public void setNoOfProperMask()
	{
		Picture picture = new Picture();
		picture.setNoOfProperMask(7);
		assertEquals(7, picture.getNoOfProperMask());
	}
	
	@Test
	public void setNoOfImproperMask()
	{
		Picture picture = new Picture();
		picture.setNoOfImproperMask(9);
		assertEquals(9, picture.getNoOfImproperMask());
	}
	
	@Test
	public void setNoOfNoMask()
	{
		Picture picture = new Picture();
		picture.setNoOfNoMask(16);
		assertEquals(16, picture.getNoOfNoMask());
	}

}
