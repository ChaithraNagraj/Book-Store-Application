package com.bridgelabz.bookstore.repo;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.Order;
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
	@Override
	public Address getAddress(Long id, String type)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<Address> q = session.createQuery("from address where user_id= :id and address_type=:type");
	    q.setParameter("id", id);
	    q.setParameter("type", type);
		return q.uniqueResult();
	}
}

