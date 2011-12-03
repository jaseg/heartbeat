package org.rhok.prototype.connector;

public abstract class Connector {

	protected String serverIP;
	
	public abstract boolean connect();
	
	public abstract boolean sendData();
	
	public Connector(String serverIP){
		this.serverIP = serverIP;
	}
	
}
