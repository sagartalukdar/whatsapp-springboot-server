package com.react.Service;

import java.util.List;

import com.react.Exception.ChatException;
import com.react.Exception.UserException;
import com.react.Model.Chat;
import com.react.Model.User;
import com.react.Request.GroupChatRequest;

public interface ChatService {

	public Chat createChat(User reqUser,Integer userId2) throws UserException;
	
	public Chat findChatById(Integer chatId) throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
	
	public Chat createGroup(GroupChatRequest groupChatRequest,User reqUser) throws UserException;
	
	public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser) throws ChatException, UserException;
	
	public Chat renameGroup(Integer chatId,String groupName,User reqUser) throws ChatException, UserException;
	
	public Chat removeFromGroup(Integer chatId,Integer userId,User reqUser) throws ChatException, UserException;
	
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;
	
	

}
