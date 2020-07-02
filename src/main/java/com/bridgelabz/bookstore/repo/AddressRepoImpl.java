package com.bridgelabz.bookstore.repo;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Address;
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
}

