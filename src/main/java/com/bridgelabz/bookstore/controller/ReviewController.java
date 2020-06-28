package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired 
	private ReviewService reviewService;
	
	@PostMapping("/review/{bookId}")
	public ResponseEntity<Response> rating(@PathVariable("bookId") long bookId,@RequestHeader("token") String token, @RequestBody ReviewDTO reviewDTO) {
		reviewService.addRating(token,bookId, reviewDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(AdminConstants.BOOK_FOUND, HttpStatus.OK.value(), reviewDTO));
	}
}
