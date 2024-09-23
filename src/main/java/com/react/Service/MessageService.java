package com.react.Service;

import java.util.List;

import com.react.Exception.ChatException;
import com.react.Exception.MessageException;
import com.react.Exception.UserException;
import com.react.Model.Message;
import com.react.Model.User;
import com.react.Request.MessageRequest;

public interface MessageService {

	public Message sendMessage(MessageRequest messageRequest) throws UserException, ChatException;
	
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException;
}
