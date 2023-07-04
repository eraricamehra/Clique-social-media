package com.neu.esd.clique.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.esd.clique.model.Connection;
import com.neu.esd.clique.model.User;

@Repository
@Transactional
public class ConnectionDao {
	
	@Autowired
	private SessionFactory sessionFactory;
 
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Connection save(Connection connection){
		 Session session = this.sessionFactory.openSession();
		 session.save(connection);
		 return connection;
	}
		
	@Transactional
	public Connection findById(Integer id) {
		Session session = sessionFactory.openSession();
		Connection connection = (Connection) session.get(Connection.class, id);
		session.close();
		return connection;
	}

	public void delete(Connection connection) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(connection);
		transaction.commit();
		session.close();
	}

	public List<Connection> getAllConnections(Integer userId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Connection> query = session.getNamedQuery("getConnectionsByUserId");
		query.setParameter("userId", (int) userId);
		List<Connection> connectionsByConnection = query.getResultList();
		session.close();
		return connectionsByConnection;
	}

	public List<Connection> getAllConnectionsByReceiver(Integer userId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Connection> query = session.getNamedQuery("getConnectionsByReceiver");
		query.setParameter("userId", (int) userId);
		List<Connection> connectionsByConnection = query.getResultList();
		session.close();
		return connectionsByConnection;
	}
	
	public List<Connection> getConnectionBySenderAndReceiver(User sender, User receiver) {
		Session session = sessionFactory.openSession();
		TypedQuery<Connection> query = session.getNamedQuery("getConnectionsBySenderAndReceiver");
		query.setParameter("receiver",  receiver.getUserId());
		query.setParameter("sender", sender.getUserId());
		List<Connection> connectionsByConnection = query.getResultList();
		session.close();
		return connectionsByConnection;
	}
	

}
