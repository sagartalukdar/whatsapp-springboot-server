package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Request.UserRequest;
import com.react.Response.ApiResponse;
import com.react.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserByToken(@RequestHeader("Authorization")String jwt) throws UserException{
		return new ResponseEntity<User>(userService.findUserProfileByJwt(jwt),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUser(@RequestParam("name") String name){
		return new ResponseEntity<List<User>>(userService.searchUser(name),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserRequest userRequest,@RequestHeader("Authorization")String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		userService.updateUser(userRequest, user.getId());
		
		ApiResponse apiResponse=new ApiResponse("account updated successfully", true);
				
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
	}
}
