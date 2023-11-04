package com.api.core.appl.tests.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import com.api.core.appl.util.UtilLibrary;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

class S3OperationsTest {

	@Value("${pictureBucket.name}")
	private String picturesBucketName;
	
	@Test
	void test() {
		
		try {
			StaticCredentialsProvider staticCredentialsProvider = UtilLibrary.getStaticCredentialsProvider();
	        Region region = Region.SA_EAST_1;
	        S3Client s3 = S3Client.builder()
	            .region(region)
	            .credentialsProvider(staticCredentialsProvider)
	            .build();
	        
	        byte[] pictureData = UtilLibrary.getObjectBytes(s3, "pictures-blog-java", "0b51d184-532e-4dc0-8b59-9bd366eed4d9");
	        
	        System.out.println("Read successfully " + pictureData.length + " bytes");
		}
		catch(Exception e){
			e.printStackTrace();
			fail("An exception has ocurred");
		}
        
		return;
	}

}
