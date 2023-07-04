package com.neu.esd.clique.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.esd.clique.model.Request;

@Repository
@Transactional
public class RequestDao {
	
	@Autowired
	private SessionFactory sessionFactory;
 
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Request save(Request user){
		 Session session = this.sessionFactory.openSession();
		 session.save(user);
		 return user;
	}
		
	@Transactional
	public Request findById(int id) {
		Session session = sessionFactory.openSession();
		Request request = (Request) session.get(Request.class, id);
		session.close();
		return request;
	}

	public Request update(Request user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
		return user;
	}
	
	public List<Request> getRequestsByUser(Integer userId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Request> query = session.getNamedQuery("getRequestsByUser");
		query.setParameter("userId", (int) userId);
		List<Request> requestsByUser = query.getResultList();
		session.close();
		return requestsByUser;
	}

	public List<Request> getRequestsBySenderAndReceiver(Integer userId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Request> query = session.getNamedQuery("getRequestsBySenderAndReceiver");
		query.setParameter("userId", (int) userId);
		List<Request> requestsByUser = query.getResultList();
		session.close();
		return requestsByUser;
	}
	
	public void delete(Request request) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(request);
		transaction.commit();
		session.close();
	}


}
