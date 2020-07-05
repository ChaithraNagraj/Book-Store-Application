package com.bridgelabz.bookstore.repo;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.MyOrder;

@Repository
@SuppressWarnings("unchecked")
public class OrderDaoImpl implements OrderRepo {

	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Transactional
	public void save(MyOrder order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}
}
