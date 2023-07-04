package com.neu.esd.clique.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.esd.clique.dao.RequestDao;
import com.neu.esd.clique.model.Connection;
import com.neu.esd.clique.model.Request;
import com.neu.esd.clique.model.User;

@Service
public class RequestService {

	@Autowired
	private RequestDao requestDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	// save
	public Request sendRequest(Request request) {
		return requestDao.save(request);
	}

	public Request getRequestById(int requestId) {
		return requestDao.findById(requestId);
	}

	public List<Request> getRequestByUser(Integer userId) {
		User existingUser = userService.getUserById(userId);
		if (existingUser != null) {
			List<Request> requests = requestDao.getRequestsByUser(userId);
			return requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("PENDING"))
					.collect(Collectors.toList());
		}
		return null;
	}
	
	public List<Request> getRequestBySenderAndReceiver(Integer userId) {
		User existingUser = userService.getUserById(userId);
		if (existingUser != null) {
			return requestDao.getRequestsBySenderAndReceiver(userId);
		}
		return null;
	}

	public boolean confirmRequest(int requestId, String status) {
		Request acceptedRequest = acceptRequest(requestId, status);
		if(acceptedRequest != null) {
		Connection connection1 = connectionService.addConnection(new Connection(acceptedRequest.getSender(), acceptedRequest.getReceiver() ));
		Connection connection2 = connectionService.addConnection(new Connection(acceptedRequest.getReceiver(), acceptedRequest.getSender() ));

		if(connection1 != null && connection2 != null) return true;
		}
		return false;
	}

	// update
	public Request acceptRequest(int requestId, String status) {
		Request existingRequest = requestDao.findById(requestId);
		if (existingRequest != null) {
			existingRequest.setStatus(status);
			return requestDao.update(existingRequest);
		}
		return null;
	}

	// delete
	public void deleteeRequest(Request request) {
		requestDao.delete(request);
	}

}
