package org.rhok.prototype.notification;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class NormalNotification extends Notification {
	
	private static final String KEY_LONGITUDE = "longitude";
	
	private static final String KEY_LATITUDE = "latitude";
	
	private static final String KEY_MESSAGE = "message";
	
	public NormalNotification(String message, double longitude, double latitude){
		content.add(new BasicNameValuePair(KEY_MESSAGE, message));
		content.add(new BasicNameValuePair(KEY_LONGITUDE, Double.toString(longitude)));
		content.add(new BasicNameValuePair(KEY_LATITUDE, Double.toString(latitude)));
	}
	
	public NormalNotification(){ /*Create an empty notification*/}
	
	@Override
	public List<NameValuePair> getContent() {
		return content;
	}

}
