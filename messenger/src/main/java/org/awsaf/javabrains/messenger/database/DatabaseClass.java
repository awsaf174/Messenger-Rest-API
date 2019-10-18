package org.awsaf.javabrains.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.awsaf.javabrains.messenger.model.Messages;
import org.awsaf.javabrains.messenger.model.Profile;

public class DatabaseClass {
	
	private static Map<Long, Messages> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Messages> getMessages(){
		return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
}
