package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.User;

public interface AdminService {
	
	public List<User> getBuyers();
	public List<User> getSellers();
	boolean verifyBook(long bookId, String staus, String token);
}
