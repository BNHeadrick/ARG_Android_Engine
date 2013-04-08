package gatech.svw.utilities;

import gatech.svw.activity.RadiationMapActivity;

import com.google.android.maps.GeoPoint;
import Task.TaskTrigger;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Allows the man to track to the player's location
 * @author Jeff Bernard
 * @date October 2011
 */
public class GPS
{
	
	double[] timeArray;
	
	/**
	 * Listens to and responds to GPS event information
	 * @author Jeff Bernard
	 * @date October 2011
	 */
	private class GPSListener implements LocationListener
	{
		/**
		 * fires when the location has changed
		 * @param location the changed location
		 */
		public void onLocationChanged(Location location)
		{
			lat = location.getLatitude();
			lng = location.getLongitude();
			
			Log.d("RAD", "location change");
			
			if(rmaActivity != null)
			{
				rmaActivity.animateOverlayToMyLocation();
			}
			
			TaskTrigger.check(location);
			
//			int ogTime = WorldState.getAbsoluteTime();
//			Time time  = new Time();
//			time.setToNow();
//			int curTime = time.second + time.minute*60 + time.hour*3600;
//			
////			if((curTime - ogTime) > (timeArray[QuestDealerActivity.getCurrentQuestIndex()]*60) + 600){
//			Log.d("RAD", " " + (curTime - ogTime));
//			if((curTime - ogTime) > 20){
//				QuestDealerActivity.rePlan(); 
//			}
		}
		
		/**
		 * fires when the location service has been shut off
		 * @param provider the name of the location service
		 */
		public void onProviderDisabled(String provider)
		{
			Log.d("RAD", "provider off");
		}
		
		/**
		 * fires when the location service has been turned on
		 * @param provider the name of the location service
		 */
		public void onProviderEnabled(String provider)
		{
			Log.d("RAD", "provider on");
		}
		
		/**
		 * fires when the status of the location service has changed
		 * @param provider the name of the location service
		 * @param status a status code
		 * @param extras
		 */
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			Log.d("RAD", "status change");
		}
	}
	
	private LocationManager manager; /**< interfaces with the android's location services */
	private GPSListener listener; /**< listens for and responds to GPS events */
	private double lat; /**< current latitude */
	private double lng; /**< current longitude */
	//Does not check if cheating is possible but serves to check if current location is User Set Cheat or from GPS
	private Boolean CheatingEnabled = false;
	private RadiationMapActivity rmaActivity;
	
	/**
	 * Constructs a new GPS (and starts it)
	 * @param context the context in which the GPS will reside
	 */
	public GPS(Context context)
	{
		lat = 33.777498;
		lng = -84.397429;
		start(context);
	}
	
	/**
	 * Starts the GPS
	 * @param context the context in which to start the GPS
	 */
	public void start(Context context)
	{
		listener = new GPSListener();
		manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		// requests an update every 15,000 milliseconds
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, listener);
	}
	
	/**
	 * Stops the GPS
	 */
	public void stop()
	{
		manager.removeUpdates(listener);
		rmaActivity = null;
		manager = null;
		listener = null;
	}
	
	/**
	 * Gets the latitude
	 * @return this->lat
	 */
	public double getLat()
	{
		return lat;
	}
	
	public void setLat(double NewLat)
	{
		lat = NewLat; 
	}
	
	/**
	 * Gets the longitude
	 * @return this->lng
	 */
	public double getLng()
	{
		return lng;
	}
	
	public void setLng(double NewLng)
	{
		lng = NewLng; 
	}
	
	public GeoPoint getCurrentLocation()
	{
		return new GeoPoint((int)(lat), (int)(lng));
	}
	
	public GeoPoint getCurrentLocationE6()
	{
		return new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
	}
	
	public void setrmaActivity(RadiationMapActivity rmaActivity)
	{
		this.rmaActivity = rmaActivity;
	}
	
	//Use it when setting Location manually from MapView PopUpBubble
	public void setCheatingEnabled(Boolean CheatingEnabled)
	{
//		if(!this.CheatingEnabled && CheatingEnabled)
//		{
//			manager.removeUpdates(listener);
//		}
//		else if(this.CheatingEnabled && !CheatingEnabled)
//		{
//			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, listener);
//		}
		
		this.CheatingEnabled = CheatingEnabled;
	}
	
	public boolean isCheatingEnabled()
	{
		return CheatingEnabled;
	}
}
