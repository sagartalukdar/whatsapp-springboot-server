package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Exception.ChatException;
import com.react.Exception.MessageException;
import com.react.Exception.UserException;
import com.react.Model.Message;
import com.react.Model.User;
import com.react.Request.MessageRequest;
import com.react.Response.ApiResponse;
import com.react.Service.MessageService;
import com.react.Service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest messageRequest) throws UserException, ChatException{
		return new ResponseEntity<Message>(messageService.sendMessage(messageRequest),HttpStatus.OK);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getAllMessagesforChat(@PathVariable("chatId")Integer chatId,@RequestHeader("Authorization")String jwt) throws UserException, ChatException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<List<Message>>(messageService.getChatMessages(chatId, reqUser),HttpStatus.OK);
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessage(@PathVariable("messageId")Integer messageId,@RequestHeader("Authorization")String jwt) throws UserException, MessageException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		messageService.deleteMessage(messageId, reqUser);
		return new ResponseEntity<ApiResponse>(new ApiResponse("message deleted successfuly",false),HttpStatus.OK);
	}
	
	
}
