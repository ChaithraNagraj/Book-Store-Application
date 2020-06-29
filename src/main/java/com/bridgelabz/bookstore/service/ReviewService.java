package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.dto.ReviewDTO;

public interface ReviewService {

	void addRating(String token, long bookId, ReviewDTO reviewDTO);

}
