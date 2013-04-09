package Task;
 
import java.util.ArrayList;

import KnowledgeBase.Axiom;
import KnowledgeBase.Method;
import KnowledgeBase.Methods;
import KnowledgeBase.Operator;
import State.WorldState;


/**
 * This class is used to hold Task information by the Tasks object
 * @author Brandon
 *
 */
public class Task {
	
	String name;
	String questText;
	ArrayList<String> params;
	ArrayList<Operator> operators;
	Methods methods;
	
	ArrayList<Method> methodsArr;
	Content content;

	ArrayList<Axiom> preConditions;
	
	boolean primative;
	
	public Task(String name){
		this.name = name;
		primative = true;
	}
	
	public void setContent(Content c){
		content = c;
	}
	
	public Content getContent(){
		return content;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Axiom> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(ArrayList<Axiom> preConditions) {
		this.preConditions = preConditions;
	}

	
	public boolean isPrimative() {
		return primative;
	}
	
	public void setParams(ArrayList<String> p){
		params = p;
	}
	
	public void setMethods(Methods m){
		if(m != null){
			primative = false;
			methods = m;
		}
	}
	
	public void setMethods(ArrayList<Method> m){
		if (m != null){
			primative = false;
			methodsArr = m;
			methods = new Methods(m);
		}
	}
	

	public Methods getMethods() {
		return methods;
	}
	
	public ArrayList<Method> getMethodsAsArray(){
		
		ArrayList<Method> methArr = new ArrayList<Method>();
		
		for(int i = 0; i<methods.getLength(); i++){
			methArr.add(getMethod(i));
		}
		
		return methArr;
	}
	
	public void updateMethods(Tasks tasks){
		for (int i = 0; i < methodsArr.size(); i++){
			methodsArr.get(i).updateSubTasks(tasks);
		}
	}
	
	public Method getMethod(int i){
		return methods.getItem(methods.getKeysAsArray()[i].toString());
	}
	
	public void setOperators(ArrayList<Operator> op){
		operators = op;
	}
	
	public ArrayList<Operator> getOperators() {
		return operators;
	}

	public void executeOperation(int i, WorldState state){
		if (operators != null){
			operators.get(i).executeOp(state);
		}
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}
