package org.rhok.prototype;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.rhok.prototype.activities.Settings;
import org.rhok.prototype.connector.RestConnector;
import org.rhok.prototype.notification.NormalNotification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class StartScreen extends Activity {
	
    private static final String TAG = "StartScreen";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.application_menu, menu);
    	
    	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.settings:
    		startSettingsActivity();
    		break;
    	}
    	return super.onMenuItemSelected(featureId, item);
    }

	private void startSettingsActivity() {
		Intent settingsIntent = new Intent(this, Settings.class);
		this.startActivity(settingsIntent);
	}
    
	public void sendNotification(View view){
		RestConnector restConnector = new RestConnector(getServerIP());
		
		NormalNotification notification = new NormalNotification("Test Notification", 1.0, 1.0);
		
		sendData(restConnector, notification);
	}

	private void sendData(RestConnector restConnector,
			NormalNotification notification) {
		try{
			boolean sended = restConnector.sendData(notification);
			showSuccessIfSended(sended);
		}catch(UnsupportedEncodingException uee){
			Log.e(TAG, "Wrong encoding", uee);
		}catch(ClientProtocolException cpe){
			Log.e(TAG, "Can't understand protocol", cpe);
		}
	}

	private void showSuccessIfSended(boolean sended) {
		if(sended){
			Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, getString(R.string.send_failure), Toast.LENGTH_LONG).show();
		}
		
	}

	private String getServerIP() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String serverIP = preferences.getString(getString(R.string.server_ip_key), "");
		Log.d(TAG, "ServerIP = " + serverIP);
		return serverIP;
	}
	
}