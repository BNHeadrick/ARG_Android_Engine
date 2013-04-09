package gatech.svw.utilities;

import gatech.svw.activity.QuestDealerActivity;

import java.util.ArrayList;
import java.util.List;

import Task.MinigameContent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapState {

//	private static MapState State;
	
//	private static MapController mcController;
//	private static MapView mvMap;
	private static GPS myGPS;
//	private static int zoomLevel;
	private static List<Overlay> mapOverlays;
	private static RadItemizedOverlay locationoverlay;
	private static RadItemizedOverlay radiationoverlay;
	private static RadItemizedOverlay conversationoverlay;
	private static RadItemizedOverlay puzzleoverlay;
	private static RadItemizedOverlay treasureoverlay;
	
	private static ArrayList<GeoPoint> coordinateList;
	
//	private static TextView tvPopUpTitle;
//	private static TextView tvPopUpText;
//	private static TableLayout tlPopUp;
//	private static Button bClosePopUp;
//	private static Button bIAmHere;
//	private static Button bConversation;
	
	private static ArrayList<GeoPoint> Locations = new ArrayList<GeoPoint>();
	
	public static void setCoordinates(ArrayList<GeoPoint> points){
		coordinateList = points;
	}
	
	/**
	 * Shows a location
	 * @param location
	 * @param description
	 */
	public static void showLocation(String location, String description){
		if (puzzleoverlay != null){
			ArrayList<MinigameContent.Location> locs = QuestDealerActivity.getLocations();
			for (int i = 0; i < locs.size(); i++){
				if (locs.get(i).getName().equals(location)){
					puzzleoverlay.addOverlay(new OverlayItem(coordinateList.get(i), location, description));
					break;
				}
			}
		}
	}
	
	public static GeoPoint getLocationCoordinates(String location){
		if (coordinateList != null){
			ArrayList<MinigameContent.Location> locs = QuestDealerActivity.getLocations();
			for (int i = 0; i < locs.size(); i++){
				if (locs.get(i).getName().equals(location)){
					return coordinateList.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Hides a location
	 * @param location
	 */
	public static void hideLocation(String location){
		if (puzzleoverlay != null){
			puzzleoverlay.removeOverlay(location);
		}
	}
	
	/**
	 * Changes an location's description
	 * @param location
	 * @param description
	 */
	public static void changeLocationDescription(String location, String description){
		puzzleoverlay.changeOverlayDescription(location, description);
	}
	
	public static void clear(){
		Locations.clear();
	}
	
	public static void saveState(GPS myGPS, List<Overlay> mapOverlays, RadItemizedOverlay locationoverlay, RadItemizedOverlay radiationoverlay, RadItemizedOverlay conversationoverlay, RadItemizedOverlay puzzleoverlay, RadItemizedOverlay treasureoverlay, ArrayList<GeoPoint> Locations)
	{
		MapState.myGPS = myGPS;
		MapState.mapOverlays = mapOverlays;
		MapState.locationoverlay = locationoverlay;
		MapState.radiationoverlay = radiationoverlay;
		MapState.conversationoverlay = conversationoverlay;
		MapState.puzzleoverlay = puzzleoverlay;
		MapState.treasureoverlay = treasureoverlay;
		
		if(!Locations.isEmpty())
		{
			MapState.Locations.clear();
			MapState.Locations.addAll(Locations);
		}
	}
	
	
	
	public static void loadState(GPS myGPS, List<Overlay> mapOverlays, RadItemizedOverlay locationoverlay, RadItemizedOverlay radiationoverlay, RadItemizedOverlay conversationoverlay, RadItemizedOverlay puzzleoverlay, RadItemizedOverlay treasureoverlay, ArrayList<GeoPoint> Locations)
	{
		myGPS = MapState.myGPS;
		mapOverlays = MapState.mapOverlays;
		locationoverlay = MapState.locationoverlay;
		radiationoverlay = MapState.radiationoverlay;
		conversationoverlay = MapState.conversationoverlay;
		puzzleoverlay = MapState.puzzleoverlay;
		treasureoverlay = MapState.treasureoverlay;
		
		if(!MapState.Locations.isEmpty())
		{
			Locations.clear();
			Locations.addAll(MapState.Locations);
		}
	}
	
	public static ArrayList<GeoPoint> getLocations()
	{
		return Locations;
	}
}
