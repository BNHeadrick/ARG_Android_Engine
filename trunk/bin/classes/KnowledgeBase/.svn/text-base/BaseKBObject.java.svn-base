package KnowledgeBase;

import java.util.Hashtable;
import java.util.Set;

/**
 * make Method, Task, Operator, and Axiom inherit this class eventually to clean up those respective classes.
 * @author Brandon
 *
 */
public abstract class BaseKBObject {

	private Hashtable<String, Object> hash;
	
	public void addItem(String name, Object genericObject)
	{
		hash.put(name, genericObject);
	}
	
	public Object getItem(String key)
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
	
	public Object getItemAtIndex(int i){
		return getItem(getKeysAsArray()[i].toString());
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
	
}
