package KnowledgeBase;

import java.util.ArrayList;

import Task.Task;
import Task.Tasks;

public class Method {

	ArrayList<Axiom> preConditions;
	ArrayList<Task> subTasks;
	ArrayList<String> strSubTasks;
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Task> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(ArrayList<Task> subTasks) {
		this.subTasks = subTasks;
	}
	
	public void updateSubTasks(Tasks tasks){
		subTasks = new ArrayList<Task>();
		for (int i = 0; i < strSubTasks.size(); i++){
			subTasks.add(tasks.getItem(strSubTasks.get(i)));
		}
	}
	
	public void setSubTasksAsStrings(String name, ArrayList<String> params) {
		
		this.strSubTasks.add(name);
		
	}
	
	public ArrayList<String> getSubTasksAsStrings() {
		return strSubTasks;
	}

	public ArrayList<Axiom> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(ArrayList<Axiom> preConditions) {
		this.preConditions = preConditions;
	}
	
	public Method(){
		strSubTasks = new ArrayList<String>();
	}
	
	public Method(String name){
		this.name = name;
		strSubTasks = new ArrayList<String>();
	}
	
	public Method(ArrayList<Task> st, ArrayList<Axiom> a){
		this.subTasks = st;
		this.preConditions = a;
		strSubTasks = new ArrayList<String>();
	}
	
	
}
