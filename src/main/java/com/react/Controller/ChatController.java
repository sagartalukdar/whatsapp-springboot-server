package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Exception.ChatException;
import com.react.Exception.UserException;
import com.react.Model.Chat;
import com.react.Model.User;
import com.react.Request.CreateChatRequest;
import com.react.Request.GroupChatRequest;
import com.react.Response.ApiResponse;
import com.react.Service.ChatService;
import com.react.Service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createSingleChat(@RequestBody CreateChatRequest createChatRequest,@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);		
		return new ResponseEntity<Chat>(chatService.createChat(reqUser, createChatRequest.getUserId()),HttpStatus.CREATED);
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroup(@RequestHeader("Authorization")String jwt,@RequestBody GroupChatRequest groupChatRequest) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<Chat>(chatService.createGroup(groupChatRequest, reqUser),HttpStatus.CREATED);
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatById(@PathVariable("chatId")Integer chatId) throws ChatException{
		return new ResponseEntity<Chat>(chatService.findChatById(chatId),HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserId(@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<List<Chat>>(chatService.findAllChatByUserId(reqUser.getId()),HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroup(@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId,@PathVariable("userId")Integer userId) throws UserException, ChatException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<Chat>(chatService.addUserToGroup(userId, chatId, reqUser),HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserToGroup(@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId,@PathVariable("userId")Integer userId) throws UserException, ChatException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<Chat>(chatService.removeFromGroup(chatId, userId, reqUser),HttpStatus.OK);
	}
	
	@PutMapping("/rename/{chatId}")
	public ResponseEntity<Chat> renameChat(@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId,@RequestBody GroupChatRequest groupChatRequest) throws ChatException, UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<Chat>(chatService.renameGroup(chatId, groupChatRequest.getChat_name(), reqUser),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChat(@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId) throws UserException, ChatException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		ApiResponse apiResponse=new ApiResponse("chat deleted Successfully", true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	
	
	
}
