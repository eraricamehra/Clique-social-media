package com.neu.esd.clique.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="getRequestsByUser", query="select r from Request r where r.receiver.userId  =:userId") 
@NamedQuery(name="getRequestsBySenderAndReceiver", query="select r from Request r where r.sender.userId  =:userId or r.receiver.userId =:userId") 

@JsonIgnoreProperties(ignoreUnknown = false)
@Table(name = "request")
public class Request implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
    @Column(name = "request_id")
	private int requestId;
	
	@ManyToOne
	@JoinColumn(name="fk_sender")
	private User sender;
    
	@ManyToOne
	@JoinColumn(name="fk_receiver")
	private User receiver;
    
    @Column(name = "status")
	private String status;
    
    @Column(name = "created_date")
	private Timestamp createdDate;
    
    @Column(name = "modified_date")
	private Timestamp modifiedDate;

	public Request(int requestId, User sender, User receiver, String status, Timestamp createdDate,
			Timestamp modifiedDate) {
		super();
		this.requestId = requestId;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public Request() {

	}


	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}



	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
