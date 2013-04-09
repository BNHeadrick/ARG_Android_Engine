package KnowledgeBase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import KnowledgeBase.Axiom;

public class Axioms extends BaseKBObject{

	private Hashtable<String, Axiom> hash;
	
	public Axioms(){
		
		hash = new Hashtable<String, Axiom>();
		createTestData();
	}

	/**
	 * This fills in the Axioms object with Axioms that are passed in from the constructor
	 * @param aList
	 */
	public Axioms(ArrayList<Axiom> aList){
		
		hash = new Hashtable<String, Axiom>();
		
		for(int i = 0; i<aList.size(); i++){
			
			hash.put(aList.get(i).toString(), aList.get(i));
		}
	}
	
	
	
	public void addItem(String axiomName, Axiom axiomObject)
	{
		hash.put(axiomName, axiomObject);
	}
	
	@Override
	public Axiom getItem(String key)
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
	
//	public int getFromIndex(){
//		return hash.
//	}
	
	@Override
	public Axiom getItemAtIndex(int i){
		return getItem(getKeysAsArray()[i].toString());
	}
	
	private void createTestData() {
		
		addItem("atLoc", new Axiom("atLoc", CreateArrWithItems("here", "there")));
		addItem("hasItem", new Axiom("hasItem", CreateArrWithItems("spoon", "fork")));
		
	}
	
	private ArrayList<String> CreateArrWithItems(String...strings ){
		
		ArrayList<String> a = new ArrayList<String>();
		
		for(int i = 0; i<strings.length; i++){
			a.add(strings[i]);
		}
		
		return a;
		
	}
	
	
}
