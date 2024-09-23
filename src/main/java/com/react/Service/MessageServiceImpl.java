package com.react.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.Exception.ChatException;
import com.react.Exception.MessageException;
import com.react.Exception.UserException;
import com.react.Model.Chat;
import com.react.Model.Message;
import com.react.Model.User;
import com.react.Repository.ChatRepository;
import com.react.Repository.MessageRepository;
import com.react.Request.MessageRequest;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Message sendMessage(MessageRequest messageRequest) throws UserException, ChatException {
		User user=userService.findUserById(messageRequest.getUserId());
		Chat chat=chatService.findChatById(messageRequest.getChatId());
		
		Message message=new Message();
		message.setContent(messageRequest.getContent());
		message.setUser(user);
		message.setChat(chat);
		message.setTimeStamp(LocalDateTime.now());
		
		message=messageRepository.save(message);
		chat.getMessages().add(message);
		chatRepository.save(chat);
		
		return message;
	}

	@Override
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException, UserException {
		Chat chat=chatService.findChatById(chatId);
		if(chat.getUsers().contains(reqUser)) {
			return messageRepository.findByChatId(chatId);
		}
		throw new UserException("you are not related this chat");
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> opt=messageRepository.findById(messageId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("message not found by id : "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
		Message message=findMessageById(messageId);
		if(message.getUser().getId()==reqUser.getId()) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("you can't delete another user's message");
	}

}
