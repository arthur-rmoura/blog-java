package com.api.core.appl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Component
public class UtilVariables {

	@Value("${picturesBucket.accessKey}")
	private String accessKey;
	
	@Value("${picturesBucket.secretKey}")
	private String secretKey;
	
	@Value("${picturesBucket.name}")
	private String picturesBucketName;

	public UtilVariables(String accessKey, String secretKey) {
		super();
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}
	
	public UtilVariables() {
		
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public StaticCredentialsProvider getStaticCredentialsProvider() {
		
		/*The user attached to this credentials is only able to access a specific s3 bucket*/
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(this.accessKey, this.secretKey));
            
		return staticCredentialsProvider;
	}


	public String getPicturesBucketName() {
		return picturesBucketName;
	}


	public void setPicturesBucketName(String picturesBucketName) {
		this.picturesBucketName = picturesBucketName;
	}
	
}
