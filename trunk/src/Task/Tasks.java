package Task;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import KnowledgeBase.BaseKBObject;
 
/**
 * This class holds all of the Tasks that will be executed in the SHOP algorithm; not necessarily all of the tasks that exist in the input file, as this file must be populated by the user
 * 
 * At the moment, this class can either be used with a passed in arrayList of Tasks, or with the default constructor with tasks added manually.
 * @author Brandon
 *
 */
public class Tasks extends BaseKBObject{

	private Hashtable<String, Task> hash;
	
	public Tasks(){
		
		hash = new Hashtable<String, Task>();
	}
	
	/**
	 * This fills in the Tasks object with tasks that are passed in from the constructor
	 * @param aList
	 */
	public Tasks(ArrayList<Task> aList){
		
		hash = new Hashtable<String, Task>();
		
		for(int i = 0; i<aList.size(); i++){
			
			hash.put(aList.get(i).toString(), aList.get(i));
		}
	}
	

	
	public void addItem(String taskName, Task taskObject)
	{
		hash.put(taskName, taskObject);
	}
	
	@Override
	public Task getItem(String key)
	{
		
		return hash.get(key);
	}
	
	@Override
	public Set<String> getKeys()
	{
		return hash.keySet();
	}
	
	@Override
	public Object[] getKeysAsArray()
	{
		return hash.keySet().toArray();
	}
	
	@Override
	public int getLength(){
		return hash.size();
	}
	
	@Override
	public Task getItemAtIndex(int i){
		return getItem(getKeysAsArray()[i].toString());
	}
	
	
	
}
