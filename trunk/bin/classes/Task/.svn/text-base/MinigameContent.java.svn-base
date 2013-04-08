package Task;

import java.util.ArrayList;
 
public class MinigameContent extends Content {
	
	public class Location{
		private String name;
		private String description;
		
		public String getName(){
			return name;
		}
		
		public String getDescription(){
			return description;
		}
		
		@Override
		public boolean equals(Object other){
			return name.equals(((Location)other).name);
		}
		
		public String toString(){
			return name;
		}
	}
	
	private Location location;
	
	
	private ArrayList<String> triggerType; /**< what kind of trigger */
	private ArrayList<String> triggerParam;
	
	public MinigameContent(){
		super(Content.MINIGAME);
		triggerType = new ArrayList<String>();
		triggerParam = new ArrayList<String>();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(String name, String desc) {
		location = new Location();
		location.name = name;
		location.description = desc;
	}

	public ArrayList<String> getTriggerType() {
		return triggerType;
	}
	
	public ArrayList<String> getTriggerParam(){
		return triggerParam;
	}

	public void addTrigger(String type, String param) {
		triggerType.add(type);
		triggerParam.add(param);
	}
	
	public String toString(){
		return "Location: "+location+"\n\tTrigger: "+triggerType+"("+triggerParam+")";
	}
}
