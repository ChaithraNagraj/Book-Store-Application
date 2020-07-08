package com.bridgelabz.bookstore.repo;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.User;


@Repository
@Transactional
@SuppressWarnings("unchecked")
public class AddressRepoImpl implements AddressRepo{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public boolean  save(Address add) {
		sessionFactory.getCurrentSession().saveOrUpdate(add);
		return true;
	}
	@Override
	public boolean  save(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		return true;
	}
//	@Override
//	public Address findAddressByType(String addressType, String token) {
//
//		Session session = sessionFactory.getCurrentSession();
//		@Query(value = "select * from Address where user_id=? and address_type =?", nativeQuery = true)
//
//		Query<Cart> query = session.createQuery("From Adress where user_id=:userId and address_type=");
//		query.setParameter("userId", id);
//		return query.uniqueResultOptional();
//		return null;
//		Session session = entityManager.unwrap(Session.class);
//		Query<Book> q = session.createQuery("From Book where book_id=:value");
//		q.setParameter("value", bookId);
//		return q.uniqueResult();
//		
//	}
	@Override
	public Address findAddressByType(String addressType, long userId) {
		Session session=sessionFactory.getCurrentSession();
		Query<Address> query=session.createQuery("From Address where user_id=:userId and address_type=:addressType");
		query.setParameter("userId", userId);
		query.setParameter("addressType", addressType);
		
		return query.uniqueResult();
	}
	public List<Address> findAddressByTypea(String addressType, long userId) {
		Session session=sessionFactory.getCurrentSession();
		Query<Address> query=session.createQuery("From Address where user_id=:userId and address_type=:addressType");
		query.setParameter("userId", userId);
		query.setParameter("addressType", addressType);
		
		return query.getResultList();
	}
}

