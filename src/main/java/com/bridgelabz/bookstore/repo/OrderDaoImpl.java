package com.bridgelabz.bookstore.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Order;

@Repository
@SuppressWarnings("unchecked")
public class OrderDaoImpl implements OrderRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addOrder(Order order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}

	@Override
	@Transactional
	public List<Order> findMyOrder(Long buyerId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Order> q = session.createQuery("from userorder where userid= :buyerId");
	    q.setParameter("buyerId", buyerId);
		return q.getResultList();
	}
}
