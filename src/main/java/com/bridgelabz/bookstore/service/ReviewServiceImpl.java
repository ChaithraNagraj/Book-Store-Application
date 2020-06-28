package com.bridgelabz.bookstore.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.constants.ReviewConstants;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Review;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.ReviewDTO;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private UserRepo userRepository;
	
	@Autowired 
	BookRepo bookRepository;
	
	
	public void addRating(String token, long bookId, ReviewDTO reviewDTO) {
		User user = userRepository.getUserById(Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"))).orElseThrow(() -> new UserNotFoundException(ReviewConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
				ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		Book book = bookRepository.getBookById(bookId).orElseThrow(() -> new UserNotFoundException(ReviewConstants.BOOK_NOT_FOUND,
				ReviewConstants.NOT_FOUND_RESPONSE_CODE));
		Review review = new Review();
		BeanUtils.copyProperties(reviewDTO, review);
		user.getReview().add(review);
		userRepository.addUser(user);
		book.getReview().add(review);
		bookRepository.save(book);		
	}

}
