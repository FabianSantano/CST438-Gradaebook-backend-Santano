//package com.cst438.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cst438.domain.AccountCredentials;
//import com.cst438.domain.CourseRepository;
//import com.cst438.domain.User;
//import com.cst438.domain.UserRepository;
//import com.cst438.services.JwtService;
//
//
//@RestController
//public class LoginController {
//	@Autowired
//	private JwtService jwtService;
//
//	@Autowired	
//	AuthenticationManager authenticationManager;
//	@Autowired
//	UserRepository repository;
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
//	   
//		User user = repository.findByAlias(credentials.username());
//
//	    if (user != null && user.getPassword().equals(credentials.password())) {
//	        // Passwords match, generate and return a JWT token
//	        String jwts = jwtService.getToken(user.getA());
//	        // Build response with the generated token
//	        return ResponseEntity.ok()
//	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
//	            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
//	            .build();
//	    } else {
//	        // Passwords do not match, return an unauthorized response
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//	    }
//	}
//
//}
package com.cst438.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.AccountCredentials;
import com.cst438.services.JwtService;


@RestController
public class LoginController {
	@Autowired
	private JwtService jwtService;

	@Autowired	
	AuthenticationManager authenticationManager;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds =
				new UsernamePasswordAuthenticationToken(
						credentials.username(),
						credentials.password());

		System.out.println(credentials.password());
		Authentication auth = authenticationManager.authenticate(creds);
		
		// Generate token
		String jwts = jwtService.getToken(auth.getName());
		System.out.println(credentials.password());

		// Build response with the generated token
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.build();

	}
}