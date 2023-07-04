package com.neu.esd.clique.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.esd.clique.model.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

@Repository
@Transactional
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public User save(User user) {
		Session session = this.sessionFactory.openSession();
		session.save(user);
		return user;
	}
	
	public List<User> findAllUsers() {
		Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.getNamedQuery("findAll");
		List<User> users;
		try {
			users = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		session.close();
    	return users;
	}

	public User findById(int id) {
		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, id);
		session.close();
		return user;
	}

	public User findByUsername(String username) {
		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, username);
		session.close();
		return user;
	}

	public Optional<User> findByEmail(String email) {
		Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.getNamedQuery("findByEmail");
		query.setParameter("email", email);
		User userByEmail;
		try {
			userByEmail = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		session.close();
    	return Optional.ofNullable(userByEmail);
	}
	
	public User findByEmailAndPassword(String email, String password) {
		Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.getNamedQuery("findByEmail");
		query.setParameter("email", email);
//		query.setParameter("password", password);
		User userByEmail;
		try {
			userByEmail = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		session.close();
    	return Optional.ofNullable(userByEmail).get();
	}


	public List<User> findByName(String firstName, String lastName) {
		Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.getNamedQuery("findByFullName");
		query.setParameter("firstName", "%" + firstName + "%");
		query.setParameter("lastName", "%" + lastName + "%");
		List<User> usersByName = query.getResultList();
		session.close();
		return usersByName;
	}
	

	public List<User> findByFirstName(String firstName) {
		Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.getNamedQuery("findByFirstName");
		query.setParameter("firstName", "%" + firstName + "%");
		query.setParameter("username", "%" + firstName + "%");
		List<User> usersByName = query.getResultList();
		session.close();
		return usersByName;
	}

	public User update(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
		return user;
	}

	public void delete(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(user);
		transaction.commit();
		session.close();
	}

}
