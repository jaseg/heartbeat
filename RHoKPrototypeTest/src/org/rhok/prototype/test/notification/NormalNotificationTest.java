package org.rhok.prototype.test.notification;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rhok.prototype.notification.NormalNotification;

import android.test.AndroidTestCase;
import android.util.Log;

public class NormalNotificationTest extends AndroidTestCase {
	
	private static final String TAG = "NormalNotificationTest";
	
	private static final String KEY_COORDINATES = "coordinates";
	
	private NormalNotification testNotificationOne;
	
	private NormalNotification malformedNotification;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		testNotificationOne = new NormalNotification("Test message", new double[]{0.1, 5.1});
		malformedNotification = new NormalNotification("Test Message", new double[]{0.1, 5.1, 10});
	}
	
	public void testNotificationContentNotNull(){
		assertNotNull(testNotificationOne.getContent());
		assertNotNull(malformedNotification.getContent());
	}
	
	public void testNotificationMapContentNotNull(){
		HashMap<String, String> content = testNotificationOne.getContent();
		for(String key : content.keySet()){
			Log.d(TAG,"Testing object with key: " + key);
			assertNotNull(content.get(key));
		}
	}
	
	public void testMalformedNotificationMapping(){
		HashMap<String, String> content = malformedNotification.getContent();
		
		assertNull(content.get(KEY_COORDINATES));
	}
	
	public void testNotificationCoordinationStringFormat(){
		HashMap<String, String> content = testNotificationOne.getContent();
		
		checkCoordinationScheme(content.get(KEY_COORDINATES));
	}

	private void checkCoordinationScheme(String coordinates) {
		Log.d(TAG,"Checking: " + coordinates);

		Pattern regExpPattern = Pattern.compile("\\[[\\d]*.[\\d]*,[\\d]*.[\\d]*\\]");
		Matcher regExpMatcher = regExpPattern.matcher(coordinates);
		assertTrue(regExpMatcher.find());
		
	}
}
