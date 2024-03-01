package com.ppedetectionapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FloorTest {

	@Test
	public void setNoOfProperMaskOnFloor() {
		Floor floor = new Floor();
		floor.setNoOfProperMaskOnFloor(12);
		assertEquals(12, floor.getNoOfProperMaskOnFloor());
	}

	@Test
	public void setNoOfImproperMaskOnFloor() {
		Floor floor = new Floor();
		floor.setNoOfImproperMaskOnFloor(7);
		assertEquals(7, floor.getNoOfImproperMaskOnFloor());
	}

	@Test
	public void setNoOfNoMaskOnFloor() {
		Floor floor = new Floor();
		floor.setNoOfNoMaskOnFloor(19);
		assertEquals(19, floor.getNoOfNoMaskOnFloor());
	}

}
