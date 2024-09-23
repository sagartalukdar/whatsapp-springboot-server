 package com.react.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Config.TokenProvider;
import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Repository.UserRepository;
import com.react.Request.LoginRequest;
import com.react.Response.AuthResponse;
import com.react.Service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
    @PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(@RequestBody User user) throws UserException{
		String email=user.getEmail();
		String fullName=user.getFull_name();
		String password=user.getPassword();
		
		Optional<User> opt=userRepository.findByEmail(email);
		if(opt.isPresent()) {
			throw new UserException("an account allready present with this email");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=tokenProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse(jwt, true);
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	}
   
	
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest){
    	String email=loginRequest.getEmail();
    	String password=loginRequest.getPassword();
    	
    	Authentication authentication=authenticate(email, password);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	String jwt=tokenProvider.generateToken(authentication);
    	
    	AuthResponse authResponse=new AuthResponse(jwt, true);
    	return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
    }
    
    public Authentication authenticate(String email,String password) {
    	UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
    	if(userDetails==null) {
    		throw new BadCredentialsException("invalid email");
    	}
    	if(!passwordEncoder.matches(password, userDetails.getPassword())) {
    		throw new BadCredentialsException("password not valid");
    	}
    	return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
