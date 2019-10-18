package org.awsaf.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.awsaf.javabrains.messenger.database.DatabaseClass;
import org.awsaf.javabrains.messenger.exception.DataNotFoundException;
import org.awsaf.javabrains.messenger.model.Messages;


public class MessageService {
	
	private Map<Long, Messages> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Messages(1, "Hello World!", "awsaf"));
		messages.put(2L, new Messages(2, "Hello Jersey!", "awsaf"));
	}
	
	public List<Messages> getAllMessages(){
		//Messages m1 = new Messages(1L, "Hello World!", "Awsaf");
		//Messages m2 = new Messages(2L, "Hello Jersey!", "Awsaf");
		
		//List<Messages> messages = new ArrayList<>();
		
		return new ArrayList<Messages>(messages.values());
	}
	
	public List<Messages> getAllMessagesForYear(int year){
		List<Messages> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		
		for(Messages message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Messages> getAllMessagesPaginated(int start, int size){
		ArrayList<Messages> messageList = new ArrayList<Messages>(messages.values());
		if(start + size > messageList.size()) {
			return new ArrayList<>();
		}
		return messageList.subList(start, start+size);
	}
	
	public Messages getMessage(long id) {
		Messages message = messages.get(id);
		
		if(message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		
		return messages.get(id);
	}
	
	public Messages addMessage(Messages message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		
		return message;
	}
	
	public Messages updateMessage(Messages message) {
		if(message.getId() <=0 ) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Messages removeMessage(long id) {
		return messages.remove(id);
	}
}
