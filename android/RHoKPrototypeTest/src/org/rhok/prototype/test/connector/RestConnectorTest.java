package org.rhok.prototype.test.connector;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.rhok.prototype.connector.RestConnector;
import org.rhok.prototype.notification.NormalNotification;
import org.rhok.prototype.notification.Notification;

import android.test.AndroidTestCase;
import android.util.Log;

public class RestConnectorTest extends AndroidTestCase {

	private static final String TAG = "RestConnectorTest";
	
	private RestConnector connectorToTest;
	
	private Notification normalNotificationToSend;
	
	public RestConnectorTest(){
		super();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		connectorToTest = new RestConnector("10.1.20.45");
		normalNotificationToSend = new NormalNotification("Das ist eine test Notification", 5.1, 1.0);
	}
		
	public void testConnectorShouldSendData(){
		try{
		assertTrue(connectorToTest.sendData(normalNotificationToSend));
		}catch(UnsupportedEncodingException uee){
			Log.e(TAG, "Couldn't send message because the encoding was not supported", uee);
		}catch(ClientProtocolException cpe){
			Log.e(TAG, "Can't handle the protocol", cpe);
		}
	}
}
