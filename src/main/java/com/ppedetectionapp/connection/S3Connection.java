package com.ppedetectionapp.connection;

import java.time.Instant;

/*
 * Name		: S3Connection
 * Purpose	: Class responsible for creating S3 connection
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Component
public class S3Connection {

	private Region region;
	private S3Client s3;
	Logger logger;
	public S3Connection() {
		{
			this.region = Region.AP_SOUTH_1;
			this.s3 = S3Client.builder().region(region).build();
			logger = LoggerFactory.getLogger(S3Connection.class);
		}
	}

	public List<String> listBucketObjects(String bucketName) {

		List<String> list = new ArrayList<String>();

		try {

			ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(bucketName).build();
			ListObjectsResponse res = s3.listObjects(listObjects);
			List<S3Object> objects = res.contents();

			for (S3Object s3Object : objects) {
				list.add(s3Object.key());
			}

		} catch (S3Exception e) {
			logger.error(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		return list;
	}

	public Boolean checkIfExistOrNot(String objectName, String bucketName) {

		List<String> objectList = listBucketObjects(bucketName);

		for (String name : objectList) {
			if (name.equals(objectName)) {
				
				logger.info("Image Already Exist! Skipping.....!");
				//System.out.println("Image Already Exist! Skipping.....!");
				return true;
			}
		}

		return false;

	}

	public List<String> listBucketNames() {

		List<String> list = new ArrayList<>();

		try {

			ListBucketsRequest bucketsRequest = ListBucketsRequest.builder().build();
			ListBucketsResponse bucketsResponse = s3.listBuckets(bucketsRequest);

			for (Bucket bucket : bucketsResponse.buckets()) {
				list.add(bucket.name());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	public String putS3Object(String bucketName, String objectKey, byte[] objectBytes) {

		try {

			s3.putObject(PutObjectRequest.builder().bucket(bucketName).key(objectKey).build(),
					RequestBody.fromBytes(objectBytes));

		} catch (S3Exception e) {
			
			logger.error(e.getMessage());
			System.exit(1);
		}

		return "Object Inserted Successfully";
	}

	public String autoDeleteOldObjects(String bucketName) {

		try {

			ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(bucketName).build();
			ListObjectsResponse res = s3.listObjects(listObjects);
			List<S3Object> objects = res.contents();

			for (S3Object s3Object : objects) {

				Instant lastDate = s3Object.lastModified();
				Instant currenttime = Instant.now();

				if (lastDate.isBefore(currenttime.minusSeconds(86400))) {
					deleteObject(bucketName, s3Object.key());
				}

			}

		} catch (S3Exception e) {
			logger.error(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		return "Old objects deleted successfully !";
	}

	public String deleteObject(String bucketName, String objectKey) {

		try {

			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey)
					.build();

			s3.deleteObject(deleteObjectRequest);

		} catch (S3Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		return "Object Deleted Successfully";
	}

	public byte[] downloadObject(String bucketName, String objectKey) {

		ResponseBytes<GetObjectResponse> objectResponse = null;
		
		try {

			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectKey).build();
			
			objectResponse = s3.getObjectAsBytes(getObjectRequest);

		} catch (S3Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}
		
		return objectResponse.asByteArray();

	}

}
