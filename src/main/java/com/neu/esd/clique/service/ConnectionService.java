package com.neu.esd.clique.service;

import java.util.List;

import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.esd.clique.dao.ConnectionDao;
import com.neu.esd.clique.model.Connection;
import com.neu.esd.clique.model.User;

@Service
public class ConnectionService {

	@Autowired
	private ConnectionDao connectionDao;

	public Connection addConnection(Connection connection) {
		return connectionDao.save(connection);
	}

	public Connection findById(Integer connectionId) {
		return connectionDao.findById(connectionId);

	}

	public List<Connection> getAllConnections(Integer userId) {
		return connectionDao.getAllConnectionsByReceiver(userId);

	}

	public List<Connection> getAllConnectionsByReceiver(Integer userId) {
		return connectionDao.getAllConnectionsByReceiver(userId);

	}
	
	
	public List<Connection> getConnectionBySenderAndReceiver(User sender, User receiver) {
		return connectionDao.getConnectionBySenderAndReceiver(sender, receiver);

	}
//	@NamedQuery(name="getConnectionsBySenderAndReceiver", query="select c from Connection c where c.connection.userId =:receiver and c.user.userId =:sender") 

	public void delete(Connection connection) {
//		 connectionDao.delete(connection);
		List<Connection> connections = connectionDao.getConnectionBySenderAndReceiver(connection.getUser(),
				connection.getConnection());
		connections.addAll(
				connectionDao.getConnectionBySenderAndReceiver(connection.getConnection(), connection.getUser()));
		for (Connection con : connections) {
			connectionDao.delete(con);
		}
	}

}
