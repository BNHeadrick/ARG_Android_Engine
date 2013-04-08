package gatech.svw.activity;

import gatech.svw.utilities.*;
import gatech.svw.planner.*;
import java.util.ArrayList;
import State.WorldState;
import Task.Content;
import Task.MinigameContent;
import Task.Task;
import Task.TaskTrigger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;



public class QuestDealerActivity extends Activity {
	// String tId = null, tLayout = null, tDialog = null, tReincorp = null, tPre
	// = null, tPost = null
	
	static ArrayList<Task> tasks; /**< list of all tasks */
	static WorldState state; /**< world state */
	static SHOP htn; /**< htn */
	
	static ArrayList<MinigameContent.Location> locations = new ArrayList<MinigameContent.Location>();
	
	static int initialQuestsSize;
	static int remainingQuestsSize;
	static int currentQuestIndex;
//	public static PowerManager.WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		TaskTrigger.first = true;
		TaskTrigger.waitStart = null;
		ConversationState.clear();
		MapState.clear();
		
		InputExtractor input = new InputExtractor(SettingsActivity.getquestXML());//.extract();
		
		tasks = input.extract();
		state = new WorldState();
		
		htn = new SHOP();
		htn.run(state, tasks);
		
		locations = new ArrayList<MinigameContent.Location>();
		for (int i = 0; i < htn.getPlan().size(); i++){
			Log.d("RAD", htn.getPlan().get(i).toString());
			if (htn.getPlan().get(i).getContent() != null && htn.getPlan().get(i).getContent().getType() == Content.MINIGAME){
				MinigameContent.Location loc = ((MinigameContent)htn.getPlan().get(i).getContent()).getLocation();
				if (loc != null && !locations.contains(loc)){
					locations.add(loc);
				}
			}
		}
		
		initialQuestsSize = locations.size();
		remainingQuestsSize = locations.size();
		
		Log.d("RAD", "Number locations: "+locations.size());
		Log.d("RAD", locations.toString());

		
		// unneccessary now because TaskTrigger will trigger the quests
//		Task firstQuest = htn.getPlan().get(0);
//		
//		if (firstQuest.getContent() != null){ // we are in serious trouble if this false
//			switch (firstQuest.getContent().getType()){
//			case Content.TEXT:
//			case Content.MINIGAME:
//			}
//		}
		
//		setContentView(R.layout.radiation_map_screen);
		Intent ourIntent = new Intent(QuestDealerActivity.this, RadiationMapActivity.class);
        startActivity(ourIntent);

	}
	
	public static ArrayList<Task> getPlan(){
		return htn.getPlan();
	}
	
	public static ArrayList<MinigameContent.Location> getLocations() {
		return locations;
	}
	
	/**
	 * Removes the specified task
	 * @param task the one that should be removed
	 * @param op the op to execute
	 */
	public static void removeTask(Task task, int op){
		tasks.remove(task);
		htn.removeTask(task, tasks); // should be .get(0) anyhow
		task.executeOperation(op, state);
		if (op != 0 && htn.getPlan().size() > 0){ // htn was wrong!
			Log.d("RAD", "checkState: "+op);
			htn.checkState(state, htn.getPlan().get(0), tasks);
			
			// recheck the locations
//			locations.clear();
			for (int i = 0; i < htn.getPlan().size(); i++){
				Log.d("RAD", htn.getPlan().get(i).toString());
				if (htn.getPlan().get(i).getContent() != null && htn.getPlan().get(i).getContent().getType() == Content.MINIGAME){
					MinigameContent.Location loc = ((MinigameContent)htn.getPlan().get(i).getContent()).getLocation();
					if (loc != null && !locations.contains(loc)){
						locations.add(loc);
					}
				}
			}
		}
		if (htn.getPlan().size() > 0){
			htn.checkEnding(state, tasks);
		}
		else{
			ConversationState.addMessage("Radventure", "Thanks for playing, but the game is over. You don't need to be here anymore.", "Recvd", ConversationActivity.getImage("helicopter"));
		}
		
		
		remainingQuestsSize--;
		if(task.getContent().getType() == Content.MINIGAME && ((MinigameContent)task.getContent()).getLocation() != null){
			currentQuestIndex++;
		}
	}
/*
	public static void rePlan(){
		Log.d("RAD", "tasks before: "+tasks);
		removeMethod(htn.getPlan().get(0));
		Log.d("RAD", "tasks after: "+tasks);
		
		InputExtractor input = new InputExtractor(SettingsActivity.getquestXML());//.extract();
		
		tasks = input.extract();
		state = new WorldState();
		
		htn = new SHOP();
		htn.run(state, tasks);
		
		locations = new ArrayList<MinigameContent.Location>();
		for (int i = 0; i < htn.getPlan().size(); i++){
			Log.d("RAD", htn.getPlan().get(i).toString());
			if (htn.getPlan().get(i).getContent() != null && htn.getPlan().get(i).getContent().getType() == Content.MINIGAME){
				MinigameContent.Location loc = ((MinigameContent)htn.getPlan().get(i).getContent()).getLocation();
				if (!locations.contains(loc)){
					locations.add(loc);
				}
			}
		}
		
		Log.d("RAD", "Number locations: "+locations.size());
		Log.d("RAD", locations.toString());
		
		Task firstQuest = htn.getPlan().get(0);
		
		if (firstQuest.getContent() != null){ // we are in serious trouble if this false
			switch (firstQuest.getContent().getType()){
			case Content.TEXT:
			case Content.MINIGAME:
			}
		}
		
		TaskTrigger.setFirst(true);
		
		TaskTrigger.check(null);
		
	}

	private static void removeMethod(Task task) {
//		for (int i = 0; i < tasks.size(); i++){
//			if (!tasks.get(i).isPrimative()){
//				_removeMethod(tasks.get(i), task);
//			}
//		}
	}
	
//	private static void _removeMethod(Task task, Task target){
//		for (int j = 0; j < task.getMethodsAsArray().size(); j++){
//			for (int k = 0; k < task.getMethodsAsArray().get(j).getSubTasks().size(); k++){
//				if (!task.getMethodsAsArray().get(j).getSubTasks().get(k).isPrimative()){
//					Log.d("RAD", "recurses!");
//					
//					_removeMethod(task.getMethodsAsArray().get(j).getSubTasks().get(k), target);
//				}
//				else if (task.getMethodsAsArray().get(j).getSubTasks().get(k).equals(target)){
//					Log.d("RAD", "removing "+j);
//					task.getMethodsAsArray().remove(j--);
//					break;
//				}
//			}
//		}
//	}

	public static int getCurrentQuestIndex() {
		return currentQuestIndex;
	}
*/		
}
