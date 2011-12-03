package org.rhok.prototype.notification;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public abstract class Notification {

	protected List<NameValuePair> content;
	
	public abstract List<NameValuePair> getContent();
	
	public Notification(){
		content = new ArrayList<NameValuePair>();
	}
	
}
