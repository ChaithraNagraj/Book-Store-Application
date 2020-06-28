package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Cart;

@Repository

public class CartRepoImpl implements CartRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public boolean saveToCart(Cart cart) {
		sessionFactory.getCurrentSession().saveOrUpdate(cart);
		return true;
	}

	@Override
	public Optional<Cart> findByUserId(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query<Cart> query = session.createQuery("From Cart where user_id=:userId");
		query.setParameter("userId", id);
		return query.uniqueResultOptional();
	}

}
