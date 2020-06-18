package com.bridgelabz.bookstore.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Book;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BookDaoImple implements BookRepo {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Book> findAllBooks() {
		return sessionFactory.getCurrentSession().createSQLQuery("select * from book;").addEntity(Book.class).list();
	}

	@Override
	public List<Book> findBookByAuthorName(String authorName) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where authorName=:name");
		q.setParameter("name", authorName);
		return q.getResultList();
	}

	@Override
	public List<Book> findBookByTitle(String bookName) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where bookName=:name");
		q.setParameter("name", bookName);
		return q.getResultList();
	}

	@Override
	public List<Book> getBooksForVerification() {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where is_approved=:value");
		q.setParameter("value", false);
		return q.getResultList();
	}

	@Override
	public Optional<Book> getBookById(Long bookId) {
		Session session = entityManager.unwrap(Session.class);
		Query<Book> q = session.createQuery("From Book where book_id=:value");
		q.setParameter("value", bookId);
		return q.uniqueResultOptional();
		
	}

	@Override
	public void deleteBook(Book book) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(book);	
	}

	@Override
	public void save(Book book) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(book);
		
	}
}
