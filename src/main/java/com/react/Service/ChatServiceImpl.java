package com.react.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.Exception.ChatException;
import com.react.Exception.UserException;
import com.react.Model.Chat;
import com.react.Model.User;
import com.react.Repository.ChatRepository;
import com.react.Request.GroupChatRequest;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
       User user2=userService.findUserById(userId2);
       Chat isChatExist=chatRepository.findSingleChatByUsers(reqUser, user2);
       if(isChatExist!=null) {
          return isChatExist;
       }
       Chat createdChat=new Chat();
       createdChat.setCreatedBy(reqUser);
       createdChat.getUsers().add(user2);
       createdChat.getUsers().add(reqUser);    
       createdChat.setGroup(false);
       
       return chatRepository.save(createdChat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> opt= chatRepository.findById(chatId);
		if(opt.isEmpty()) {
			throw new ChatException("chat not found with id : "+chatId);
		}
		return opt.get();
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		List<Chat> chats=chatRepository.findChatsByUserId(userId);
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest groupChatRequest, User reqUser) throws UserException {
		// TODO Auto-generated method stub
		Chat chat=new Chat();
		chat.setChat_image(groupChatRequest.getChat_image());
		chat.setChat_name(groupChatRequest.getChat_name());
		chat.setGroup(true);
		chat.setCreatedBy(reqUser);
		
		for(Integer userId:groupChatRequest.getUserIds()) {
			User user=userService.findUserById(userId);
			chat.getUsers().add(user);
		}
		chat.getUsers().add(reqUser);
		chat.getAdmins().add(reqUser);
		
		return chatRepository.save(chat);
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws ChatException, UserException {
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(userId);
		
		if(chat.getAdmins().contains(reqUser)) {
			chat.getUsers().add(user);
			return chatRepository.save(chat);
		}else {
			throw new UserException("you don't have access to add");
		}		
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		Chat chat=findChatById(chatId);
		
		if(chat.getUsers().contains(reqUser)) {
			chat.setChat_name(groupName);
			return chatRepository.save(chat);
		}
		throw new ChatException("you are not a member of this group");
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(userId);
		
		if(chat.getAdmins().contains(reqUser)) {
			chat.getUsers().remove(user);
			return chatRepository.save(chat);
		}else if(chat.getUsers().contains(reqUser)) {
			if(user.getId().equals(reqUser.getId())) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
		}		
		throw new UserException("you can't delete another user");		
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(userId);
		if(chat.getUsers().contains(user)) {
			chatRepository.delete(chat);
		}		
	}

}
