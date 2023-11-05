package com.api.core.appl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
public class UtilLibrary {
	
	
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
        	System.out.println(e.awsErrorDetails().errorMessage());
            return null;
        }
        
    }
    

	public static void putObjectBytes(S3Client s3, String picturesBucketName, String objectKey, byte[] data) {
	    try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putObject = PutObjectRequest.builder()
                .bucket(picturesBucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

            s3.putObject(putObject, RequestBody.fromBytes(data));

        } catch (S3Exception e) {
        	System.out.println(e.awsErrorDetails().errorMessage());
        }
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

	public static String encryptMD5(String password) {
		
		String encryptedPassword = null;  
		
        try {  
        	
            /* MessageDigest instance for MD5. */  
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            /* Add plain-text password bytes to digest using MD5 update() method. */  
            m.update(password.getBytes());  
              
            /* Convert the hash value into bytes */   
            byte[] bytes = m.digest();  
              
            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
              
            /* Complete hashed password in hexadecimal format */  
            encryptedPassword = s.toString();
            
            return encryptedPassword;
        }   
        catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();
            throw new RuntimeException("Error of password encryption!");
        }  
	}     
}
