package com.ppedetectionapp.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WingTest {

	@Test
	public void contextLoad() {
		Wing wing = new Wing();
		assertThat(wing).isNotNull(); // Check building object is not null
	}

	@Test
	public void setWingName() {
		Wing wing = new Wing();
		wing.setWingName("WingA");
		assertEquals("WingA", wing.getWingName());
	}

	@Test
	public void setNoOfProperMaskOnWing() {
		Wing wing = new Wing();
		wing.setNoOfProperMaskOnWing(6);
		assertEquals(6, wing.getNoOfProperMaskOnWing());
	}

	@Test
	public void setNoOfImproperMaskOnWing() {
		Wing wing = new Wing();
		wing.setNoOfImproperMaskOnWing(9);
		assertEquals(9, wing.getNoOfImproperMaskOnWing());
	}

	@Test
	public void setNoOfNoMaskOnWing() {
		Wing wing = new Wing();
		wing.setNoOfNoMaskOnWing(18);
		assertEquals(18, wing.getNoOfNoMaskOnWing());
	}

}
