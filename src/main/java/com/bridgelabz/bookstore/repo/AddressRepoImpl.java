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
public class AddressRepoImpl implements AddressRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean save(Address add) {
		sessionFactory.getCurrentSession().saveOrUpdate(add);
		return true;
	}

	@Override
	public boolean save(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		return true;
	}

	@Override
	public Address findAddressByType(String addressType, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Address> query = session.createQuery("From Address where user_id=:userId and address_type=:addressType");
		query.setParameter("userId", userId);
		query.setParameter("addressType", addressType);
		return query.uniqueResult();
	}

}
