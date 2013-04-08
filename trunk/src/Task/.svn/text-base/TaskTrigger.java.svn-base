package Task;

import java.util.ArrayList;

import android.location.Location;
import android.text.format.Time;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import gatech.svw.activity.QuestDealerActivity;
import gatech.svw.utilities.ConversationState;
import gatech.svw.utilities.MapState;

/**
 * Checks if the trigger for the current task has been met
 * @author Jeff Bernard
 *
 */
public class TaskTrigger {
	
	private static final float EPSILON_AT = 20; /**< how many meters to consider at location ? */
	private static final float EPSILON_NEAR = 50; /**< how many meters to consider near a location ? */
	public static boolean first = true;
	public static Time waitStart = null;
	
	/**
	 * checks if the trigger for the has been met
	 */
	public static void check(Location curLoc){
		check(curLoc, true);
	}
	
	/**
	 * Checks if the trigger for the current task has been met
	 */
	public static void check(Location curLoc, boolean canCheat){
		boolean cheating = false;
		if (curLoc == null && canCheat){
			cheating = !first;
		}
		do{
			Log.d("RAD", "checking stuff");
			ArrayList<Task> plan = QuestDealerActivity.getPlan();
			boolean triggered = false;
			
			if (!first){
				if (plan.size() > 0){ 
					Task current = plan.get(0);
					if (current.getContent() == null){ 
						Log.d("RAD", "null trigger"); 
						triggered = true;
						QuestDealerActivity.removeTask(current, 0);
					}
					else if (current.getContent().getType() == Content.MINIGAME){
						ArrayList<String> triggers = ((MinigameContent)current.getContent()).getTriggerType();
						ArrayList<String> triggerParams = ((MinigameContent)current.getContent()).getTriggerParam();
						// iterate through all triggers
						for (int i = 0; i < triggers.size(); i++){
							String trigger = triggers.get(i);
							String triggerParam = triggerParams.get(i);
							
							if (trigger.equalsIgnoreCase("at")){ // check if at location
								// find coordinate of location
								GeoPoint destinationPoint = MapState.getLocationCoordinates(triggerParam);
								if (destinationPoint != null){
									Location destination = new Location("RAD");
									destination.setLongitude(destinationPoint.getLongitudeE6()/1E6);
									destination.setLatitude(destinationPoint.getLatitudeE6()/1E6);
									if (cheating || (curLoc != null && curLoc.distanceTo(destination) < EPSILON_AT)){
										cheating = false;
										if (((MinigameContent)current.getContent()).getLocation() != null){
											MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
										}
										QuestDealerActivity.removeTask(current, i);
										triggered = true;
										Log.d("RAD", "minigam don "+current);
									}
								}
								else{
									// not desintation, i guess we're there
									if (((MinigameContent)current.getContent()).getLocation() != null){
										MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
									}
									QuestDealerActivity.removeTask(current, i);
									triggered = true;
									Log.d("RAD", "no geopoint");
								}
							}
							else if (trigger.equalsIgnoreCase("near")){//check if near location
								// find coordinate of location
								GeoPoint destinationPoint = MapState.getLocationCoordinates(triggerParam);
								if (destinationPoint != null){
									Location destination = new Location("RAD");
									destination.setLongitude(destinationPoint.getLongitudeE6()/1E6);
									destination.setLatitude(destinationPoint.getLatitudeE6()/1E6);
									if (cheating || (curLoc != null && curLoc.distanceTo(destination) < EPSILON_NEAR)){
										cheating = false;
										if (((MinigameContent)current.getContent()).getLocation() != null){
											MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
										}
										QuestDealerActivity.removeTask(current, i);
										triggered = true;
										Log.d("RAD", "minigame done "+current);
									}
								}
								else{
									// not desintation, i guess we're there
									if (((MinigameContent)current.getContent()).getLocation() != null){
										MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
									}
									QuestDealerActivity.removeTask(current, i);
									triggered = true;
									Log.d("RAD", "no geopoint");
								}
							}
							else if (trigger.equalsIgnoreCase("wait")){
								if (waitStart == null){
									waitStart = new Time();
									waitStart.setToNow();
									Log.d("RAD", "waiting start "+current);
								}
								else{
									Time now = new Time();
									now.setToNow();
									int elapsedTime = (now.hour-waitStart.hour)*3600+(now.minute-waitStart.minute)*60+(now.second-waitStart.second);
									Log.d("RAD", "waited for: "+elapsedTime);
									if (elapsedTime >= Integer.parseInt(triggerParam)){
										if (((MinigameContent)current.getContent()).getLocation() != null){
											MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
										}
										triggered = true;
										Log.d("RAD", "waiting done "+current);
										QuestDealerActivity.removeTask(current, i);
										waitStart = null;
									}
								}
							}
							else if (trigger.equalsIgnoreCase("reply")){
								String reply = ConversationState.getLastReply();
								Log.d("RAD", "reply: "+reply+" ("+triggerParam+")");
								if (reply.toUpperCase().contains(triggerParam.toUpperCase())){
									if (((MinigameContent)current.getContent()).getLocation() != null){
										MapState.hideLocation(((MinigameContent)current.getContent()).getLocation().getName());
									}
									triggered = true;
									Log.d("RAD", "reply done "+current);
									QuestDealerActivity.removeTask(current, i);
								}
							}
							// other triggers?
						}
						
					}
					else if (current.getContent().getType() == Content.TEXT){//text content is inately satisfied
						Log.d("RAD", "text  is finished "+current);
						QuestDealerActivity.removeTask(current, 0);
						triggered = true;
					}
				}
			}
			
			plan = QuestDealerActivity.getPlan();
			
			// now check to execute the next task
			if (first || (triggered && plan.size() > 0)){ // there is a task to execute
				if (triggered){
					Log.d("RAD", "task triggered");
				}
				ConversationState.incrementLastReply();
				waitStart = null;
				first = false;
				Task current = plan.get(0);
				if (current.getContent() != null){
					if (current.getContent().getType() == Content.MINIGAME){
						Log.d("RAD", "display minigame "+current);
						if (((MinigameContent)current.getContent()).getLocation() != null){
							MapState.showLocation(((MinigameContent)current.getContent()).getLocation().getName(), ((MinigameContent)current.getContent()).getLocation().getDescription());
						}
					}
					else if (current.getContent().getType() == Content.TEXT){
						Log.d("RAD", "display text "+current);
						TextContent text = (TextContent)current.getContent();
						ConversationState.addMessage(text.getName(), text.getBody(), "Recvd", /*ConversationActivity.getImage(text.getImage())*/null);
					}
				}
				else{
					Log.d("RAD", "some lame null content");
				}
				continue;
			}
			break;
		} while (true);
		Log.d("RAD", "done");
	}
	public static void setFirst(boolean b){
		first = b;
	}
}
