package com.bridgelabz.bookstore.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearch {

	// Kalpesh Review: Code has to be in-line avoid unnecessary variable use.
	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		RestHighLevelClient rest = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
		return rest;

	}

}
