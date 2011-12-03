package org.rhok.prototype.connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;


public class RestConnector extends Connector {
	
	private static final String TAG = "RestConnector";
	
	private URL serverURL;
	
	private HttpURLConnection urlConnection = null;

	public RestConnector(String serverIP) {
		super(serverIP);
		
		try{
		serverURL = new URL("http://" + serverIP);
		}catch (MalformedURLException mue) {
			Log.e(TAG, "Can't set URL", mue);
		}
	}

	
	@Override
	public boolean connect() {
		try{
			urlConnection = (HttpURLConnection) serverURL.openConnection();
			return true;
		}catch(IOException ioe){
			Log.e(TAG, "Can't connect to server: " + serverURL.toString());
		}
		return false;
	}

	public boolean connected(){
		return urlConnection != null;
	}
	
	@Override
	public boolean sendData() {
		return false;
	}

}
