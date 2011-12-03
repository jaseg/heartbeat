package org.rhok.prototype.test.connector;

import org.rhok.prototype.connector.RestConnector;

import android.test.AndroidTestCase;

public class RestConnectorTest extends AndroidTestCase {

	private RestConnector connectorToTest;
	
	public RestConnectorTest(){
		super();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		connectorToTest = new RestConnector();
	}
	
	public void testConnectorShouldConnect(){
		assertTrue(connectorToTest.connect());
	}
	
	public void testConnectorShouldSendData(){
		assertTrue(connectorToTest.sendData());
	}
}
