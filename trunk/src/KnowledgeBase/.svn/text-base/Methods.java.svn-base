package KnowledgeBase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import State.WorldState;

public class Methods extends BaseKBObject{

	private Hashtable<String, Method> hash;
	
	public Methods(){
		
		hash = new Hashtable<String, Method>();
		createTestData();
	}

	/**
	 * This fills in the Methods object with Methods that are passed in from the constructor
	 * @param aList
	 */
	public Methods(ArrayList<Method> aList){
		
		hash = new Hashtable<String, Method>();
		
		for(int i = 0; i<aList.size(); i++){
			
			hash.put(aList.get(i).toString(), aList.get(i));
		}
	}
	

	
	public void addItem(String methodName, Method methodObject)
	{
		hash.put(methodName, methodObject);
	}
	
	public Method getItem(String key)
	{
		
		return hash.get(key);
	}
	
	public Set<String> getKeys()
	{
		return hash.keySet();
	}
	
	public Object[] getKeysAsArray()
	{
		return hash.keySet().toArray();
	}
	
	public int getLength(){
		return hash.size();
	}
	
	public Method getItemAtIndex(int i){
		return getItem(getKeysAsArray()[i].toString());
	}
	
	private void createTestData() {
		
//		addItem("atLoc", new Method("atLoc", CreateArrWithItems("here", "there")));
//		addItem("hasItem", new Method("hasItem", CreateArrWithItems("spoon", "fork")));
		
	}
	
//	private ArrayList<String> CreateArrWithItems(String...strings ){
//		
//		ArrayList<String> a = new ArrayList<String>();
//		
//		for(int i = 0; i<strings.length; i++){
//			a.add(strings[i]);
//		}
//		
//		return a;
//		
//	}
	
	public ArrayList<Method> getValidMethods(WorldState state){
		
		ArrayList<Method> validMeths = new ArrayList<Method>();
		for(int i = 0; i<getLength(); i++){
			
			if(state.areAxiomsTrue(getItemAtIndex(i).getPreConditions())){
				validMeths.add(getItemAtIndex(i));
			}
			
		}
		return validMeths;
		
	}
}