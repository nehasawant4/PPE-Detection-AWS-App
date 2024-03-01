package com.ppedetectionapp.connection;

/*
 * Name		: DynamoDBConnection
 * Purpose	: Class responsible for Insert, Update, Get item by Id and Get all items 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ppedetectionapp.entity.Building;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Component
public class DynamoDBConnection {
	private Region region;
	private DynamoDbClient ddb;
	private DynamoDbEnhancedClient enhancedClient;
	Logger logger;

	public DynamoDBConnection() {
		region = Region.AP_SOUTH_1;
		ddb = DynamoDbClient.builder().region(region).build();
		enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
		logger = LoggerFactory.getLogger(S3Connection.class);

	}

	// Insert new record into DynamoDB
	public void putRecord(Building building) {
		try {

			// Create a DynamoDbTable object
			DynamoDbTable<Building> buildingTable = enhancedClient.table("Building",
					TableSchema.fromBean(Building.class));

			buildingTable.putItem(building);

		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
	}

	// Update existing record into DynamoDB
	public void updateRecord(Building building) {
		try {

			// Create a DynamoDbTable object
			DynamoDbTable<Building> buildingTable = enhancedClient.table("Building",
					TableSchema.fromBean(Building.class));

			buildingTable.updateItem(building);

		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
	}

	// Return particular record based on primary key as id
	public Building getItem(int id) {

		Building building = null;

		try {
			// Create a DynamoDbTable object
			DynamoDbTable<Building> mappedTable = enhancedClient.table("Building",
					TableSchema.fromBean(Building.class));

			// Create a KEY object
			Key key = Key.builder().partitionValue(id).build();

			// Get the item by using the key
			building = mappedTable.getItem(r -> r.key(key));

		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
		return building;
	}

	// Return all record from DynamoDB
	public List<Building> getAllItems() {

		List<Building> list = new ArrayList<>();

		try {

			// Create a DynamoDbTable object
			DynamoDbTable<Building> buildingTable = enhancedClient.table("Building",
					TableSchema.fromBean(Building.class));

			Iterator<Building> results = buildingTable.scan().items().iterator();

			while (results.hasNext()) {
				list.add(results.next());
			}

		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		return list;
	}

	// Delete building from DynamoDB
	public void deleteBuildingRecord(Building building) {
		try {
			DynamoDbTable<Building> buildingTable = enhancedClient.table("Building",
					TableSchema.fromBean(Building.class));
			buildingTable.deleteItem(building);
		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
	}

}
