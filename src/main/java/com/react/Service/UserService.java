package com.react.Service;

import java.util.List;

import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Request.UserRequest;

public interface UserService {

	public User findUserById(Integer userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User updateUser(UserRequest reqUser,Integer userId) throws UserException;
	
	public List<User> searchUser(String query);
}
