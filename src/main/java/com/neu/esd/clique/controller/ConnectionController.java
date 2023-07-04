package com.neu.esd.clique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.Connection;
import com.neu.esd.clique.model.SuccessResponse;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.ConnectionService;
import com.neu.esd.clique.service.RequestService;
import com.neu.esd.clique.service.UserService;

@RestController
public class ConnectionController {
	
	@Autowired
	private ConnectionService connectionService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/clique/connections/user/{userId}")
	public ResponseEntity<List<Connection>> getAllConnections(@PathVariable Integer userId) {
        if(userId == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		User existingUser = userService.getUserById(userId);
        if(existingUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		List<Connection>  connections = connectionService.getAllConnections(userId);
		return ResponseEntity.ok(connections);
	}
	
	@GetMapping("/clique/connections/{userId}/{connectionId}")
	public ResponseEntity<List<Connection>> getConnectionExists(@PathVariable Integer userId, @PathVariable Integer connectionId) {
        if(userId == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		User existingUser = userService.getUserById(userId);
        if(existingUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        User connection = userService.getUserById(connectionId);
        if(connection == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		List<Connection>  connections = connectionService.getConnectionBySenderAndReceiver(existingUser, connection);
		return ResponseEntity.ok(connections);
	}
	
	@DeleteMapping("/clique/connection/{connectionId}")
	public ResponseEntity<?> removeConnection(@PathVariable Integer connectionId) {
		Connection existingConnection = connectionService.findById(connectionId);
        if(existingConnection == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		connectionService.delete(existingConnection);
		SuccessResponse successResponse = new SuccessResponse(HttpStatus.NO_CONTENT.toString(), "Delete Success");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successResponse);
	}

}
