package com.react.Request;

public class MessageRequest {

	private Integer chatId;
	private Integer userId;
	private String content;
	
	public MessageRequest() {
		
	}	
	
	public MessageRequest(Integer chatId, Integer userId, String content) {
		super();
		this.chatId = chatId;
		this.userId = userId;
		this.content = content;
	}

	public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
