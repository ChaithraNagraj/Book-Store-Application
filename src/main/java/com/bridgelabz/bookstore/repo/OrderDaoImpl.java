//package com.bridgelabz.bookstore.repo;
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.hibernate.query.Query;
//
//import com.bridgelabz.bookstore.model.Book;
//import com.bridgelabz.bookstore.model.MyOrder;
//import com.bridgelabz.bookstore.model.MyOrderList;
//
//@Repository
//public class OrderDaoImpl implements OrderRepo {
//
//	@Autowired
//	private SessionFactory sessionFactory;
//
//	@Transactional
//	public void save(MyOrder order) {
//		Session currentSession = sessionFactory.getCurrentSession();
//		currentSession.saveOrUpdate(order);
//	}
//	
////	@Transactional
////	public void saveOrders(MyOrderList order) {
////		Session currentSession = sessionFactory.getCurrentSession();
////		currentSession.saveOrUpdate(order);
////	}
//
////	@Override
////	@Transactional
////	public List<MyOrderList> findOrderByUserId(long userId) {
////		Session session=sessionFactory.getCurrentSession();
////		Query<MyOrderList> query=session.createQuery("From myorderlist where user_id=:userId");
////		query.setParameter("userId", userId);
////		return query.getResultList();
////	}
//
////	@Override
////	public List<MyOrderList> findOrderByUserId(long id) {
////		Session session = sessionFactory.getCurrentSession();
////		Query query = session.createQuery("From myorderlist where user_id=:id");
////		query.setParameter("id", id);
////		return query.getResultList();
////	}
//
//	
//}