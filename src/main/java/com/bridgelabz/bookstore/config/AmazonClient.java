package com.bridgelabz.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonClient {
	// Kalpesh Review: If not using any part of code then remove it and try to avoid
	// comments until code is not complicated

//	@Value("${amazonProperties.endpointUrl}")
//	private String endpointUrl;
//	@Value("${amazonProperties.bucketName}")
//	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;
	@Value("${amazonProperties.region}")
	private String region;

//	public String fileUrl(String fileName) {
//		return endpointUrl + "/" + bucketName + "/" + fileName;
//	}

	@Bean
	public AmazonS3 awsS3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}
}