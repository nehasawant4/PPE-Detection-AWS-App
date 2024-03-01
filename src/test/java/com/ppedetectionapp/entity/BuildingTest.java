package com.ppedetectionapp.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BuildingTest {
	
	@Test
	public void contextLoad()
	{
		Building building = new Building();
		assertThat(building).isNotNull();	// Check building object is not null
	}
	
	@Test
	public void test_SetId()	// Test case for setId and getId function 
	{
		Building building = new Building();
		building.setId(3);
		assertEquals(3,building.getId());
	}
	
	@Test
	public void test_SetBuildingName()	//Test case for setBuildingName and getBuildingName
	{
		Building building = new Building();
		building.setBuildingName("Rigveda");
		assertEquals("Rigveda",building.getBuildingName());
	}
	
	@Test
	public void test_setNoOfProperMaskInBuilding()
	{
		Building building = new Building();
		building.setNoOfProperMaskInBuilding(2);
		assertEquals(2, building.getNoOfProperMaskInBuilding());
	}
	
	@Test
	public void test_setNoOfImproperMaskInBuilding()
	{
		Building building = new Building();
		building.setNoOfImproperMaskInBuilding(5);
		assertEquals(5, building.getNoOfImproperMaskInBuilding());
	}
	
	@Test
	public void test_setNoOfNoMaskInBuilding()
	{
		Building building = new Building();
		building.setNoOfNoMaskInBuilding(7);
		assertEquals(7, building.getNoOfNoMaskInBuilding());
	}

}
