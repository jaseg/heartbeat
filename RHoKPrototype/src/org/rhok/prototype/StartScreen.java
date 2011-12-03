package org.rhok.prototype;

import org.rhok.prototype.activities.Settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StartScreen extends Activity {
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
    
}