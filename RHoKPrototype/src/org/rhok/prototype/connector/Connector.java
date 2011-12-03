package org.rhok.prototype.connector;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.rhok.prototype.notification.Notification;

public abstract class Connector {

	protected String serverIP;
	
	public abstract boolean sendData(Notification notificationToSend) throws UnsupportedEncodingException, ClientProtocolException;
	
	public Connector(String serverIP){
		this.serverIP = serverIP;
	}
	
}
