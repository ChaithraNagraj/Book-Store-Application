package com.bridgelabz.bookstore.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

//import com.bridgelabz.bookstore.model.MyOrder;
import com.bridgelabz.bookstore.model.Order;

@Repository
public class OrderDaoImpl implements OrderRepo {

	@Autowired
	private SessionFactory sessionFactory;

//	@Transactional
//	public void save(MyOrder order) {
//		Session currentSession = sessionFactory.getCurrentSession();
//		currentSession.saveOrUpdate(order);
//	}
	
	@Override
	@Transactional
	public void addOrder(Order order) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(order);
	}

//	@Override
//	@Transactional
//	public List<MyOrder> getOrders(Long id) {
//		Session session = sessionFactory.getCurrentSession();
//		Query<MyOrder> query = session.createQuery("From user where user_id=:userId");
//		query.setParameter("userId", id);
//		return query.getResultList();
//
//	}
//	@Override
//	public MyOrder getMyOrder( long userId) {
//		Session session=sessionFactory.getCurrentSession();
//		Query<MyOrder> query=session.createQuery("From myorder where user_id=:userId");
//		query.setParameter("userId", userId);
//		return query.uniqueResult();
//
//}
}