package org.rhok.prototype.notification;

import java.util.HashMap;

import org.rhok.prototype.exceptions.WrongCoordinateArrayException;

import android.util.Log;

public class NormalNotification extends Notification {
	
	private static final String TAG = "NormalNotification";
	
	private static final String KEY_COORDINATES = "coordinates";
	
	private static final String KEY_MESSAGE = "message";
	
	private static final int ARRAY_THRESHOLD = 2;
	
	private static final int ARRAY_THRESHOLD_ITERATION = 1;
	
	public NormalNotification(String message, double[] coordinates){
		try{
			content.put(KEY_COORDINATES, putCoordinatesIntoContent(coordinates));
		}catch (WrongCoordinateArrayException wce) {
			Log.e(TAG, "Couldn't put data into the content" ,wce);
		}
		content.put(KEY_MESSAGE, message);
	}
	
	private String putCoordinatesIntoContent(double[] coordinates) throws WrongCoordinateArrayException{
		if(checkLengthOfCoordinateArray(coordinates)){
			return parseArrayAndCreateString(coordinates);
		}
		throw new WrongCoordinateArrayException("Can't parse coordinates because the array is malformed");
	}
	
	private boolean checkLengthOfCoordinateArray(double[] coordinates) {
		if(coordinates.length == ARRAY_THRESHOLD){
			return true;
		}
		return false;
	}	

	private String parseArrayAndCreateString(double[] coordinates) {
		String coordinatesToReturn = "[";
		for(int i = 0; i < coordinates.length; i++){
			coordinatesToReturn += checkIfLastAndAttachToString(i, coordinates[i]);
		}
		return coordinatesToReturn;
	}

	private String checkIfLastAndAttachToString(int i, double d) {
		if(i < ARRAY_THRESHOLD_ITERATION){
			return d + ",";
		}else{
			return d + "]";
		}
	}

	@Override
	public HashMap<String, String> getContent() {
		return content;
	}

}
