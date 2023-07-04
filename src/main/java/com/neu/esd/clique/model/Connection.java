package com.neu.esd.clique.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedQuery(name="getConnectionsByUserId", query="select c from Connection c where c.user.userId =:userId") 
@NamedQuery(name="getConnectionsByReceiver", query="select c from Connection c where c.connection.userId =:userId") 
@NamedQuery(name="getConnectionsBySenderAndReceiver", query="select c from Connection c where c.connection.userId =:receiver and c.user.userId =:sender") 

@Table(name = "connections")
public class Connection {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
    @Column(name = "connection_id")
	private Integer connectionId;
	
	@ManyToOne
	@JoinColumn(name="fk_user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="fk_connection")
	private User connection;
	
	public Connection() {
		
	}
	
	public Connection(User user, User connection) {
		super();
		this.user = user;
		this.connection = connection;
	}
	
	public Connection(Integer connectionId, User user, User connection) {
		super();
		this.connectionId = connectionId;
		this.user = user;
		this.connection = connection;
	}
	
	public int getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public User getConnection() {
		return connection;
	}

	public void setConnection(User connection) {
		this.connection = connection;
	}
	
	

}
