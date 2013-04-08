package KnowledgeBase;

import java.util.ArrayList;

public class Axiom {
	String name;
	ArrayList<String> params;
	boolean ignore;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}
	
	public Axiom(String n, ArrayList<String> p){
		name = n;
		params = p;
		ignore = false;
	}
	
	public void ignoreParams(){
		ignore = true;
	}
	
	@Override
	public boolean equals(Object a){
		
		Axiom axiom = (Axiom)a;
		if (axiom.params.size() == params.size()){
			if (axiom.name.equals(name)){
				if (!ignore && !axiom.ignore){
					for (int i = 0; i < params.size(); i++){
						if (!axiom.params.get(i).equals(params.get(i))){
							return false;
						}
					}
				}
				return true;
			}
		}
			
		return false;
	}
	
	public String toString(){
		return name+params;
		
	}
}
