package org.rhok.prototype.activities;

import org.rhok.prototype.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		addPreferencesFromResource(R.xml.settings);
	}
	
}
