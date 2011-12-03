package org.rhok.prototype.notification;

import java.util.HashMap;

public abstract class Notification {

	protected HashMap<String, String> content;
	
	public abstract HashMap<String, String> getContent();
	
	public Notification(){
		content = new HashMap<String, String>();
	}
	
}
