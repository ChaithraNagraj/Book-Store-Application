package com.bridgelabz.bookstore.repo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Cart;
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class CartRepoImpl implements CartRepo {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean saveToCart(Cart cart) {
		sessionFactory.getCurrentSession().saveOrUpdate(cart);
		return true;
	}

}
