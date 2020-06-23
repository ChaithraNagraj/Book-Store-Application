package com.bridgelabz.bookstore.repo;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.model.Role;

@Repository
public class RoleRepositoryImp implements RoleRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public void save(Role roleEntity) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(roleEntity);
	}

	@Transactional
	public Role getRoleByName(String name) {
		Session session = entityManager.unwrap(Session.class);
		Query<Role> q = session.createQuery("From Role where role_name=:name");
		q.setParameter("name", name);

		return (Role) q.uniqueResult();

	}

	@Transactional
	public Role getRoleById(int rid) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("From Role where role_id=:id");
		q.setParameter("id", rid);
		return (Role) q.uniqueResult();
	}

}
