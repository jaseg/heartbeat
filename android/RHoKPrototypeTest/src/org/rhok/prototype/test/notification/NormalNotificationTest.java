package org.rhok.prototype.test.notification;

import java.util.List;

import org.apache.http.NameValuePair;
import org.rhok.prototype.notification.NormalNotification;

import android.test.AndroidTestCase;
import android.util.Log;

public class NormalNotificationTest extends AndroidTestCase {
	
	private static final String TAG = "NormalNotificationTest";
	
	private NormalNotification testNotificationOne;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		testNotificationOne = new NormalNotification("Test message", 0.1, 5.1);
	}
	
	public void testNotificationContentNotNull(){
		assertNotNull(testNotificationOne.getContent());
	}
	
	public void testNotificationMapContentNotNull(){
		List<NameValuePair> content = testNotificationOne.getContent();
		for(NameValuePair nameValuePair : content){
			Log.d(TAG,"Testing object with key: " + nameValuePair);
			assertNotNull(nameValuePair);
		}
	}
}
