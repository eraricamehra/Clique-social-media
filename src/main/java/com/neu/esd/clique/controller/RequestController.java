package com.neu.esd.clique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.Request;
import com.neu.esd.clique.model.SuccessResponse;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.RequestService;
import com.neu.esd.clique.service.UserService;

@RestController
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
//	@GetMapping("/hello")
//	public ResponseEntity<String> test() {
//		return ResponseEntity.ok("hello");
//	}

	@GetMapping("/clique/request/{requestId}")
	public ResponseEntity<Request> getUserById(@PathVariable Integer requestId) {
		Request request = requestService.getRequestById(requestId);
		if (request != null) {
			return ResponseEntity.ok(request);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/clique/requests/user/{userId}")
	public ResponseEntity<List<Request>> getAllPendingRequests(@PathVariable Integer userId) {
		List<Request> requests = requestService.getRequestByUser(userId);
		if (requests != null) {
			return ResponseEntity.ok(requests);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/clique/request")
	public ResponseEntity<Request> createRequest(@RequestBody Request request) {
		Request createdRequest = requestService.sendRequest(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	//fetch all requests of a user
	
	@PatchMapping("/clique/request/{requestId}")
	public ResponseEntity<?> confirmFriendRequest(@PathVariable Integer requestId) {
		if (requestId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Request existingRequest = requestService.getRequestById(requestId);
		if (existingRequest == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		boolean result = requestService.confirmRequest(requestId, "CONFIRMED");
		SuccessResponse response ;
		if(result) {
			response = new SuccessResponse(HttpStatus.OK.toString(), "CONFIRMED");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		response = new SuccessResponse(HttpStatus.BAD_REQUEST.toString(), "BAD REQUEST");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}


	@DeleteMapping("/clique/request/{requestId}")
	public ResponseEntity<?> deleteUserRequest(@PathVariable Integer requestId) {
		if (requestId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Request existingRequest = requestService.getRequestById(requestId);
		if (existingRequest == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		requestService.deleteeRequest(existingRequest);
		SuccessResponse successResponse = new SuccessResponse(HttpStatus.NO_CONTENT.toString(), "Delete Success");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successResponse);
	}


}
