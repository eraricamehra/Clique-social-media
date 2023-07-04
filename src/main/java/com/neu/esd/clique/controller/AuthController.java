package com.neu.esd.clique.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.neu.esd.clique.model.LoginRequest;
import com.neu.esd.clique.model.LoginResponse;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.JwtUtils;
import com.neu.esd.clique.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserService userService;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody LoginRequest request) {
		System.out.println("*********Inside");
		Optional<User> fetchedUser = userService.getUserByEmail(request.getEmail());
		if (fetchedUser != null) {
			User user = fetchedUser.get();

			String token = jwtUtil.generateToken(user);
			return new ResponseEntity<>(token, HttpStatus.CREATED);

		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}


	@PostMapping("/auth/test")
	public String authenticate() {
		return "jsfchsk";
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<?> signUpUser(@RequestBody User user) {
		User createdUser = userService.saveUser(user);
		String token = jwtUtil.generateToken(createdUser);
		return new ResponseEntity<>(token, HttpStatus.CREATED);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("login");
		User user = userService.getUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
		if (user == null)
			throw new IllegalArgumentException();
		String token = jwtUtil.generateToken(user);
		LoginResponse loginResponse = new LoginResponse(user, token);
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}


	@ExceptionHandler({ MethodArgumentNotValidException.class, InternalServerError.class })
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		System.out.println("prinitng");
		Map<String, String> errorMap = new HashMap<>();
		List<String> list = new ArrayList<>();
		for (ObjectError error : exception.getAllErrors()) {
			list.add(error.getDefaultMessage());

		}
		errorMap.put("errorMap", list.toString());
		errorMap.put("status", "400");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<?> handleIllegalArgumentExceptionException(IllegalArgumentException exception) {
		System.out.println("prinitng");
		Map<String, String> errorMap = new HashMap<>();

		errorMap.put("errorMap", "Incorrect email or password");
		errorMap.put("status", "400");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
	}
}
