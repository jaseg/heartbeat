package org.rhok.prototype.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService {

	private Context context;
	
	private LocationManager locationManager;
	
	private LocationListener locationLister;
	
	private LocationInterface locationInterface;
	
	private static LocationService instance; 
	
	private LocationService(){/*Singleton Class*/}
	
	public void setContext(Context context) {
		this.context = context;
		instantiateLocationManager();
	}
	
	private void instantiateLocationManager() {
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public void startUpdatingUserLocation(){
		locationLister = new UserLocationListener();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationLister);
	}
	
	public void stopUpdatingUserLocation(){
		locationManager.removeUpdates(locationLister);
	}
	
	public static LocationService getInstance(Context context, LocationInterface locationInterface){
		if(instance == null){
			instance = new LocationService();
			instance.setLocationInterface(locationInterface);
			instance.setContext(context);
			return instance;
		}
		instance.setContext(context);
		instance.setLocationInterface(locationInterface);
		return instance;
	}
	
	private void setLocationInterface(LocationInterface locationInterface) {
		this.locationInterface = locationInterface;
	}

	private class UserLocationListener implements LocationListener{

		public void onLocationChanged(Location location) {
			locationInterface.userLocationUpdate(location);
		}

		public void onProviderDisabled(String provider) {
			// TODO Work with this data
		}

		public void onProviderEnabled(String provider) {
			// TODO: Work with this data
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Work with this method
		}
		
	}
}
