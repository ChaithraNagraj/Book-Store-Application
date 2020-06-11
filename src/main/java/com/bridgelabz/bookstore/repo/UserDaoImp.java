package com.bridgelabz.bookstore.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.utils.DateUtility;

@Repository
@Transactional
public class UserDaoImp implements UserRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private WebSecurityConfig encrypt;

	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public User findByUserId(Long id) {
		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	public User getusersByemail(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query<?> q = session.createQuery("from User where email=:email");
		q.setParameter("email", email);
		return (User) q.uniqueResult();
	}

	public List<User> getUser() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User";
		Query query = session.createQuery(hql);
		List results = query.list();
		return results;
	}

	public User update(User val, Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		user.setCity(val.getCity());
		user.setFirstName(val.getFirstName());
		session.update(user);
		return user;
	}

	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = findByUserId(id);
		session.delete(user);
	}

	@Override
	public List<User> findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User E WHERE E.email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		return query.list();
		
	}

	@Override
	public void updatePassword(Long id, String password) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		user.setPassword((encrypt.bCryptPasswordEncoder().encode(password)));
		session.update(user);
	}

	@Override
	public void updateDateTime(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setUpdateDateTime(DateUtility.today());
		session.update(user);
	}

	@Override
	public void verify(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		user.setVerify(true);
		session.update(user);
	}

}