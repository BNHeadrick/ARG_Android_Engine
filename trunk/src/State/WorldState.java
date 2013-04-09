package State;
import java.util.ArrayList;
import android.text.format.Time;

import KnowledgeBase.Axiom;

 
public class WorldState implements Cloneable{

//	static String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
	static Time now = new Time();
	
	public WorldState(){
		now.setToNow();
	}
	
	/**
	 * change this object after I decide on a more concrete representation of state information
	 */
	ArrayList<Axiom> stateList = new ArrayList<Axiom>();
	
	public ArrayList<Axiom> getCurrentState()
	{
		
		return stateList;
	}
	
	public boolean isAxiomTrue(Axiom a){
		
		for(int i = 0; i<stateList.size(); i++)
		{
			if(a.equals(stateList.get(i))){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString(){
		return stateList.toString();
	}
	
	public boolean areAxiomsTrue(ArrayList<Axiom> as){
		for(int i = 0; i<as.size(); i++){
			if(!isAxiomTrue(as.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public void addAxiom(Axiom a){
		stateList.add(a);
	}
	
	public void removeAxiom(Axiom a){
		for(int i = 0; i<stateList.size(); i++)
		{
			if(a.equals(stateList.get(i))){
				stateList.remove(i);
				break;
			}
			
		}
	}

	@Override
	public Object clone(){
		try{
			WorldState clone = (WorldState)super.clone();
			clone.stateList = new ArrayList<Axiom>();
			clone.stateList.addAll(stateList);
			
			return clone;
		}catch(CloneNotSupportedException e){
			System.out.println("Cloning not allowed!");
			return this;
		}
	}

	public static int getTimeInSeconds(){
		return now.second;
	}
	
	public static int getTimeInMinutes(){
		return now.minute;
	}
	
	public static int getTimeInHours(){
		return now.hour;
	}
	
	public static int getAbsoluteTime(){
		return now.second + now.minute*60 + now.hour*3600;
	}
	
}
