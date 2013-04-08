/**
 * 
 */
package gatech.svw.activity;

import java.util.ArrayList;
import java.util.List;

import gatech.svw.R;
import gatech.svw.utilities.GPS;
import gatech.svw.utilities.MapState;
import gatech.svw.utilities.RadItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import Task.TaskTrigger;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * @author mikhailjacob
 *
 */ 
public class RadiationMapActivity extends MapActivity implements View.OnClickListener {

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		MapState.loadState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		MapState.loadState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		MapState.saveState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		super.onStop();
	}

	private MapController mcController;
	private MapView mvMap;
	private GPS myGPS; 
	private int zoomLevel;
	private List<Overlay> mapOverlays;
	private RadItemizedOverlay locationoverlay;
	private RadItemizedOverlay radiationoverlay;
	private RadItemizedOverlay conversationoverlay;
	private RadItemizedOverlay puzzleoverlay;
	private RadItemizedOverlay treasureoverlay;
	TextView tvPopUpTitle;
	TextView tvPopUpText;
	TableLayout tlPopUp;
	Button bClosePopUp;
	Button bIAmHere;
	Button bIDontWanna;
	Button bConversation;
	Button bPuzzle;
	
	ArrayList<GeoPoint> Locations = new ArrayList<GeoPoint>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Test using "telnet localhost 5554" in terminal to connect to ADB
		//Then use "geo fix -84.3995 33.7788" in terminal to set test location Map View
		
		setContentView(R.layout.radiation_map_screen);
		
		MapState.loadState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		
		myGPS = new GPS(this);
		
		mvMap = (MapView)findViewById(R.id.mvMap);
		mvMap.setBuiltInZoomControls(true);
		mvMap.setSatellite(false);
		
		mcController = mvMap.getController();
		zoomLevel = mvMap.getMaxZoomLevel() - 3;
		mcController.setZoom(zoomLevel);
		
		tlPopUp = (TableLayout)findViewById(R.id.tlPopUp);
		tvPopUpTitle = (TextView)findViewById(R.id.tvPopUpTitle);
		tvPopUpText = (TextView)findViewById(R.id.tvPopUpText);
		bClosePopUp = (Button)findViewById(R.id.bClosePopUp);
		bIAmHere = (Button)findViewById(R.id.bIAmHere);
		bIDontWanna = (Button)findViewById(R.id.bIDontWanna);
		bClosePopUp.setOnClickListener(this);
		bIAmHere.setOnClickListener(this);
		bIDontWanna.setOnClickListener(this);
		bConversation = (Button)findViewById(R.id.bConversation);
		bConversation.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//setContentView(R.layout.conversation_screen);
				Intent ourIntent = new Intent(RadiationMapActivity.this, ConversationActivity.class);
		        startActivity(ourIntent);
			}
		});
		
		bPuzzle = (Button)findViewById(R.id.bMiniGames);
		bPuzzle.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent ourIntent = new Intent(RadiationMapActivity.this, PuzzleActivity.class);
		        startActivity(ourIntent);
			}
		});
		
		if(Locations == null)
		{
			Locations = new ArrayList<GeoPoint>();
		}
		
		mapOverlays = mvMap.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.locationmarker);
		locationoverlay = new RadItemizedOverlay(drawable, mvMap.getContext());
		locationoverlay.initialize(tlPopUp, tvPopUpTitle, tvPopUpText, bIAmHere, bIDontWanna);
		mapOverlays.add(locationoverlay);
		
		drawable = this.getResources().getDrawable(R.drawable.personmarker);
		conversationoverlay = new RadItemizedOverlay(drawable, mvMap.getContext());
		conversationoverlay.initialize(tlPopUp, tvPopUpTitle, tvPopUpText, bIAmHere, bIDontWanna);
		mapOverlays.add(conversationoverlay);
				
		drawable = this.getResources().getDrawable(R.drawable.puzzlemarker);
		puzzleoverlay = new RadItemizedOverlay(drawable, mvMap.getContext());
		puzzleoverlay.initialize(tlPopUp, tvPopUpTitle, tvPopUpText, bIAmHere, bIDontWanna);
		mapOverlays.add(puzzleoverlay);
		
		drawable = this.getResources().getDrawable(R.drawable.treasuremarker);
		treasureoverlay = new RadItemizedOverlay(drawable, mvMap.getContext());
		treasureoverlay.initialize(tlPopUp, tvPopUpTitle, tvPopUpText, bIAmHere, bIDontWanna);
		mapOverlays.add(treasureoverlay);
		
		drawable = this.getResources().getDrawable(R.drawable.radiationmarker);
		radiationoverlay = new RadItemizedOverlay(drawable, mvMap.getContext());
		radiationoverlay.initialize(tlPopUp, tvPopUpTitle, tvPopUpText, bIAmHere, bIDontWanna);
		mapOverlays.add(radiationoverlay);
		
		myGPS.setrmaActivity(this);
		
		animateOverlayToMyLocation();
		
		testMapsActivity(getRandomRouteAtBoundedTime(myGPS.getCurrentLocationE6(),
				SettingsActivity.getTimeLimit(), 2*QuestDealerActivity.getLocations().size()), 
				getRandomPointsAtBoundedDistance(myGPS.getCurrentLocationE6(), 2000, 10));
		
		//End Test Code
		
		MapState.saveState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		
		mcController.animateTo(myGPS.getCurrentLocationE6());
		
		// test debug whatevs
//		MapState.showLocation(QuestDealerActivity.getLocations().get(0).getName(), QuestDealerActivity.getLocations().get(0).getDescription());
		
		tlPopUp.setVisibility(View.GONE);
			
		Intent ourIntent = new Intent(RadiationMapActivity.this, ConversationActivity.class);
        startActivity(ourIntent);
	}
	
	public void testMapsActivity(ArrayList<GeoPoint> RandomRoute, ArrayList<GeoPoint> RadiationZones)
	{
		//TestCode...
//		Locations.clear();
		Locations.addAll(RandomRoute);
		Locations.addAll(RadiationZones);
		
//		String[] sTypeArray = {"puzzle", "conversation", "treasure"};
		int index = 1;
//		int arrayindex = 0;
		
		radiationoverlay.clearOverlays();
		conversationoverlay.clearOverlays();
		puzzleoverlay.clearOverlays();
		treasureoverlay.clearOverlays();
		
		MapState.setCoordinates(RandomRoute);
		
//		for(GeoPoint GP : RandomRoute)
//		{
//			arrayindex = 0;//(int) Math.floor(Math.random() * 3);
//			addNewOverlay(GP, "Brandon's Location", "Meet Brandon to get info about current situation", sTypeArray[arrayindex]);
//			System.out.println(arrayindex);
//		}
		
		index = 0;
		for(GeoPoint GP : RadiationZones)
		{
			addNewOverlay(GP, "Random Radiation Zone " + index, "Radiation Zone " + index++ + " at upto 10000m distance at random.", "radiation");
		}
		
		int tempLat = (int)((Math.random() * 90) * 1E6);
		int tempLng = (int)((Math.random() * 180) * 1E6);
		GeoPoint gpTemp = new GeoPoint(tempLat, tempLng);
		addNewOverlay(gpTemp, "Random Location", "This is a random Location for testing Cheating features...\n", "location");
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		MapState.saveState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		super.onDestroy();
		
		myGPS.stop();
		finish();
	}
	
	public void animateMapToPoint(GeoPoint gpLocation)
	{
		mcController.animateTo(gpLocation);
	}
	
	public void addMyLocationOverlay(OverlayItem overlay)
	{
		locationoverlay.removeMyLocationOverlay();
		locationoverlay.addOverlay(overlay);
		
		mvMap.invalidate();
	}
	
	public void addNewOverlay(OverlayItem overlay, String sType)
	{
		if(sType.equalsIgnoreCase("radiation"))
		{
			radiationoverlay.addOverlay(overlay);
		}
		else if(sType.equalsIgnoreCase("puzzle"))
		{
			puzzleoverlay.addOverlay(overlay);
		}
		else if(sType.equalsIgnoreCase("conversation"))
		{
			conversationoverlay.addOverlay(overlay);
		}
		else if(sType.equalsIgnoreCase("treasure"))
		{
			treasureoverlay.addOverlay(overlay);
		}
		else
		{
			locationoverlay.addOverlay(overlay);
		}
	}
	
	public void addNewOverlay(GeoPoint gpLocation, String sTitle, String sText, String sType)
	{
		OverlayItem overlayitem = new OverlayItem(gpLocation, sTitle, sText);
		addNewOverlay(overlayitem, sType);
	}
	
	public void animateToMyLocation()
	{
		animateMapToPoint(myGPS.getCurrentLocationE6());
		OverlayItem overlayitem = new OverlayItem(myGPS.getCurrentLocationE6(), "My Location", "Current GPS Location\n");
		addMyLocationOverlay(overlayitem);
	}
	
	public void animateOverlayToMyLocation()
	{
		OverlayItem overlayitem = new OverlayItem(myGPS.getCurrentLocationE6(), "My Location", "Current GPS Location\n");
		addMyLocationOverlay(overlayitem);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mapviewmenu, menu);
	    menu.getItem(1).setVisible(false);
	    
	    MapState.saveState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
	    return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		if(!myGPS.isCheatingEnabled())
		{
			menu.getItem(1).setVisible(false);
		}
		else if(!menu.getItem(1).isVisible())
		{
			menu.getItem(1).setVisible(true);
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
		{
			case R.id.StopCheatingOption:
			{
				myGPS.setCheatingEnabled(false);
				break;
			}
		
			case R.id.MyLocationOption:
			{
				animateToMyLocation();
				break;
			}
		}
		
		return true;
	}
	
	/*
	 * Gets a random point at a certain meter distance from a GeoPoint
	 */
	public GeoPoint getRandomPointAtDistance(GeoPoint currentpointE6, double distanceinmeters)
	{
		Point currentpointpixels = new Point();
		mvMap.getProjection().toPixels(currentpointE6, currentpointpixels);
		double r = mvMap.getProjection().metersToEquatorPixels((float) distanceinmeters);
		double theta = Math.random() * 2 * Math.PI;
		double dx = r * Math.cos(theta);
		double dy = r * Math.sin(theta);
		currentpointpixels.offset((int)Math.round(dx), (int)Math.round(dy));
		
		return mvMap.getProjection().fromPixels(currentpointpixels.x, currentpointpixels.y);
	}
	
	/*
	 * Gets a random point within a certain bounded meter distance from a GeoPoint 
	 */
	public GeoPoint getRandomPointAtBoundedDistance(GeoPoint currentpointE6, double boundinmeters)
	{
		return getRandomPointAtDistance(currentpointE6, Math.random() * boundinmeters);
	}
	
	
	/*
	 * Gets a given number of random points within a certain bounded meter distance from a GeoPoint
	 */
	public ArrayList<GeoPoint> getRandomPointsAtBoundedDistance(GeoPoint currentpointE6, double boundinmeters, int numberofpoints)
	{
		ArrayList<GeoPoint> randompoints = new ArrayList<GeoPoint>();
		
		for(int index = 0; index < numberofpoints; index++)
		{
			randompoints.add(getRandomPointAtBoundedDistance(currentpointE6, boundinmeters));
		}
		
		return randompoints;
	}
	
	/*
	 * Gets a route between a given number of random points within a certain duration of average walking time (speed 5.25 km / hour) from a GeoPoint
	 */
	public ArrayList<GeoPoint> getRandomRouteAtBoundedTime(GeoPoint currentpointE6, double boundinminutes, int numberofpoints)
	{
		return getRandomRouteAtBoundedTime(currentpointE6, boundinminutes, 4.5, numberofpoints);
	}
	
	/*
	 * Gets a route between a given number of random points within a certain duration of average walking time at a given speed from a GeoPoint
	 */
	public ArrayList<GeoPoint> getRandomRouteAtBoundedTime(GeoPoint currentpointE6, double boundinminutes, double averagewalkingspeedkmph, int numberofpoints)
	{
		ArrayList<GeoPoint> randomroute = new ArrayList<GeoPoint>();
		double[] timearray = new double[numberofpoints];
		
		double increment = (boundinminutes < numberofpoints ? (boundinminutes / numberofpoints) : 1);
		int stoppingcondition = (int) (boundinminutes < numberofpoints ? numberofpoints : boundinminutes);
		
		Log.d("RAD", "Number of Points (Should be greater than 0)" + numberofpoints);
		
		for(int index = 0; index < stoppingcondition; index++)
		{
			timearray[(int)Math.floor(Math.random() * numberofpoints)] += increment; 
		}
		
		//Accounting for possible randomly unfilled time values with 0 values using a borrowing from neighbour system
		double previousdeficit = 0;
		
		GeoPoint Current = currentpointE6;
		for(int index = 0; index < numberofpoints; index++)
		{
			timearray[index] -= previousdeficit;
			
			if(timearray[index] < 1)
			{
				previousdeficit = 1 - timearray[index];
				timearray[index] = 1;
			}
			
			Current = getRandomPointAtDistance(Current, timearray[index] * averagewalkingspeedkmph / 60 * 1000);
			
			randomroute.add(Current);
		}
		
		
		
		return randomroute;
	}

	public void onClick(View v) {
		switch(v.getId())
		{
//			case R.id.bIDontWanna:
//			{
//				QuestDealerActivity.rePlan();
//				
//				break;
//			}
		
			case R.id.bIAmHere:
			{
//				myGPS.setCheatingEnabled(true);
				
				double NewLat = puzzleoverlay.getLastOverlayPoint().getLatitudeE6();
				NewLat /= 1E6;
				myGPS.setLat(NewLat);
				
				double NewLng = puzzleoverlay.getLastOverlayPoint().getLongitudeE6();
				NewLng /= 1E6;
				myGPS.setLng(NewLng);
				
				puzzleoverlay.removeMyLocationOverlay();
				
				// trigger the next task
				TaskTrigger.check(null);
				
				//intentionally not executing break; so that pop up closes after selecting i am here.
			}
			
			case R.id.bClosePopUp:
			{
				tlPopUp.setVisibility(View.GONE);
				tvPopUpTitle.setText("");
				tvPopUpText.setText("");
				break;
			}
			
			default:
			{
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onPause()
	 */
	@Override
	protected void onPause() {
		MapState.saveState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onResume()
	 */
	@Override
	protected void onResume() {
		MapState.loadState(myGPS, mapOverlays, locationoverlay, radiationoverlay, conversationoverlay, puzzleoverlay, treasureoverlay, Locations);
		tlPopUp.setVisibility(View.GONE);
		super.onResume();
	}

}
