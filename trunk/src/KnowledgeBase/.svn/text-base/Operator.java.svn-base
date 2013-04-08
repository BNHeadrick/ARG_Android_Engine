package KnowledgeBase;

import java.util.ArrayList;

import State.WorldState;

public class Operator {

	ArrayList<Axiom> axiomsToAdd;
	ArrayList<Axiom> axiomsToRemove;
	private int karmaVal;
	
	public Operator(){
		axiomsToAdd = new ArrayList<Axiom>();
		axiomsToRemove = new ArrayList<Axiom>();
	}
	
	public Operator(ArrayList<Axiom> remove, ArrayList<Axiom> add){
		axiomsToRemove = remove;
		axiomsToAdd = add;
	}

	public ArrayList<Axiom> getAxiomsToAdd() {
		return axiomsToAdd;
	}

	public void setAxiomsToAdd(ArrayList<Axiom> axiomsToAdd) {
		this.axiomsToAdd = axiomsToAdd;
	}

	public ArrayList<Axiom> getAxiomsToRemove() {
		return axiomsToRemove;
	}

	public void setAxiomsToRemove(ArrayList<Axiom> axiomsToRemove) {
		this.axiomsToRemove = axiomsToRemove;
	}
	
	public void setKarmaValue(int karmaVal){
		this.karmaVal = karmaVal;
	}
	
	public int getKarmaValue(){
		return karmaVal;
	}
	
	public void executeOp(WorldState state){
		for(int i = 0; i<axiomsToAdd.size(); i++){
			state.addAxiom(axiomsToAdd.get(i));
		}
		for(int i = 0; i<axiomsToRemove.size(); i++){
			state.removeAxiom(axiomsToRemove.get(i));
		}
	}

	
}
