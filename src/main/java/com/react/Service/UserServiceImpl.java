package com.react.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.react.Config.TokenProvider;
import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Repository.UserRepository;
import com.react.Request.UserRequest;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public User findUserById(Integer userId) throws UserException {
		Optional<User> opt=userRepository.findById(userId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("user not found with id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=tokenProvider.getEmailFormToken(jwt);
		if(email!=null) {
			Optional<User> opt=userRepository.findByEmail(email);
			if(opt.isPresent()) {
				return opt.get();
			}
			throw new UserException("user not found with email : "+email);
		}
		throw new BadCredentialsException("recieved intvalid token");
	}

	@Override
	public User updateUser(UserRequest reqUser, Integer userId) throws UserException {
		
		User user=findUserById(userId);
		
		if(reqUser.getFull_name()!=null) {
			user.setFull_name(reqUser.getFull_name());
		}
		if(reqUser.getProfile_picture()!=null) {
			user.setProfile_picture(reqUser.getProfile_picture());
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> searchUsers=userRepository.searchUsers(query);
		return searchUsers;
	}

}
