package com.api.core.appl.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class UtilLibrary {
	
	@Value("${picturesBucket.accessKey}")
	private static String accessKey;
	
	@Value("${picturesBucket.secretKey}")
	private static String secretKey;
	
	public static String secondsToTime(Long totalSeconds) {
		
		long hours = totalSeconds / 3600;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = totalSeconds % 60;
		
		return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
	}
	
    public static byte[] getObjectBytes (S3Client s3, String bucketName, String keyName) {

        try {
            GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .key(keyName)
                .bucket(bucketName)
                .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            return objectBytes.asByteArray();
            
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return null;
        }
        
    }

	public static StaticCredentialsProvider getStaticCredentialsProvider() {
			/*The user attached to this credentials is only able to access a specific s3 bucket*/
           
            StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey));
            
            return staticCredentialsProvider;
	}

	public static long getDateTimestampF1(String date) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
		Instant instant = Instant.now();
		long dateTimestamp = localDateTime.toEpochSecond(ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		return dateTimestamp;
	}
}
