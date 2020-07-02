package com.bridgelabz.bookstore.repo;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Order;

@Repository

public class OrderDaoImpl implements OrderRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addOrder(Order order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}

}
