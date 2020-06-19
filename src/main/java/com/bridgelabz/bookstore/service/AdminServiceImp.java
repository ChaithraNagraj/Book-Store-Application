package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JwtValidate jwt;
	
	@Autowired
	UserRepo repoUser;
	
	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public List<User> getBuyers() {
		
		return roleRepository.getRoleById(2).getUser();
	}
	

	
}
