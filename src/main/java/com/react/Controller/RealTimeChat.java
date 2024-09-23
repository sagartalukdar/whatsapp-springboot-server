package com.react.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.react.Model.Message;

public class RealTimeChat {
	
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message reciveMessage(@Payload Message message) {		
		simpMessagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(), message);
		return message;
	}
}
