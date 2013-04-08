package gatech.svw.planner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.util.Log;

import KnowledgeBase.Method;
import State.WorldState;
import Task.Task;

/**
 * a prototype of the following algorithm
 * procedure SHOP (S, T, D)
1.   if T = nil then return nil endif
2.   t = the first task in T
3.   U = the remaining tasks in T
4.   if t is primitive (i.e., there is an operator for t) then
5.         nondeterministically choose an operator o for t
6.         P = SHOP (o(S), U, D)
7.         if P = FAIL then return FAIL endif
8.         return cons(p, P)
9.   else if there is a method applicable to t whose
         preconditions can all be inferred from S then
10.    nondeterministically let m be such a method
11.    return SHOP(S, append(m(t,S), U), D)
12. else
13.    return FAIL !!![NO!  continue to find better tasks in the task list.  then, make sure to put the failed task back in the task list]
14. endif
end SHOP

Shop2!

procedure SHOP2(s, T,D)
P = the empty plan
T0   {t 2 T : no other task in T is constrained to precede t}
loop
if T = ; then return P
nondeterministically choose any t 2 T0
if t is a primitive task then
A   {(a, ) : a is a ground instance of an operator in D,  is a substitution
that unifies {head(a), t}, and s satisfies a’s preconditions}
if A = ; then return failure
nondeterministically choose a pair (a, ) 2 A
modify s by deleting del(a) and adding add(a)
append a to P
modify T by removing t and applying 
T0   {t 2 T : no task in T is constrained to precede t}
else
M   {(m, ) : m is an instance of a method in D,  unifies {head(m), t},
pre(m) is true in s, and m and  are as general as possible}
if M = ; then return failure
nondeterministically choose a pair (m, ) 2 M
modify T by removing t, adding sub(m), constraining each task
in sub(m) to precede the tasks that t preceded, and applying 
if sub(m) 6= ; then
T0   {t 2 sub(m) : no task in T is constrained to precede t}
else T0   {t 2 T : no task in T is constrained to precede t}
repeat
end SHOP2

/new alg
 * 
 *  a prototype of the following algorithm
 * procedure SHOP (S, T, D)
 * T0 = some Task that has no constraints & is in T (pre-cond)
 * loop while T0 is not empty
	2.   t = the first task in T0
	3.   remove t from T
	4.   if t is primitive (i.e., there is an operator for t) then
	5.         nondeterministically choose an operator o for t
			   Execute o(S) //operator to modify world state
			   append t to the plan P
			   update T0 based on the remaining tasks in T whose constraints are satisfied by the current state S.=
	9.   else
	10.    nondeterministically let m be such a method, then add it to the front of T0 so that it is executed next
	14. endif
	endloop
end SHOP

S is a state, T is a list of tasks, and D is the knowledge base (methods, operators,
and Horn-clause axioms).

 * @author Brandon
 *
 */
public class SHOP {

	ArrayList<Task> tasksClone;
	WorldState stateClone;
	ArrayList<Task> plan;
	
	HashMap<String, Task> parentTasks;
	HashMap<String, String> chosenMethods;
	private boolean plannedEnding;

	public SHOP() {
		// TODO Auto-generated constructor stub
//		this.state = state;
//		this.tasks = tasks;
//		this.kb = kb;
		
		plannedEnding = false;
		
		
	}

	public void printResults() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Task> getPlan() {
		return plan;
	}
	
	public void run(WorldState state, ArrayList<Task> tasks){
		
		stateClone = (WorldState) state.clone();
		tasksClone = new ArrayList<Task>(tasks);
		
		Collections.shuffle(tasksClone);
		
		plan = new ArrayList<Task>();
		parentTasks = new HashMap<String, Task>();
		chosenMethods = new HashMap<String, String>();
		
		ArrayList<Task> validTasks = getValidTasks(stateClone, tasksClone, new ArrayList<Task>());
		
		doShop(validTasks, state, tasks);
	}
	
	private void doShop(ArrayList<Task> validTasks, WorldState state, ArrayList<Task> tasks){
		stateClone = (WorldState) state.clone();
		tasksClone = new ArrayList<Task>(tasks);
		
		Collections.shuffle(tasksClone);
		
		ArrayList<Task> parents = new ArrayList<Task>();
		ArrayList<Integer> parentLengths = new ArrayList<Integer>();
		while(validTasks.size() > 0){
//			System.out.println("SHOP");
			Task currentTask = validTasks.get(0);
			validTasks.remove(0);
			tasksClone.remove(currentTask);
			
			if(currentTask.isPrimative()){
				if (currentTask.getPreConditions() == null || stateClone.areAxiomsTrue(currentTask.getPreConditions())){
					currentTask.executeOperation(0, stateClone);
					
					plan.add(currentTask);
					// add me some parents
					if (parents.size() > 0){
						parentTasks.put(currentTask.getName(), parents.get(parents.size()-1));
						int n = parentLengths.get(parentLengths.size()-1).intValue()-1;
						if (n <= 0){
							parentLengths.remove(parentLengths.size()-1);
							parents.remove(parents.size()-1);
						}
						else{
							parentLengths.set(parentLengths.size()-1, n);
						}
					}
					validTasks = getValidTasks(stateClone, tasksClone, validTasks);
				}
				else{
					tasksClone.add(currentTask);
				}
			}
			else{
				
				ArrayList<Task> newValidTasks = new ArrayList<Task>();
//				newValidTasks.add(currentTask.getMethods().getItem(currentTask.getMethods().getKeysAsArray()[0].toString()).getSubTasks().get(0));
				
				
				ArrayList<Method> validMethods = getValidMethods(stateClone, currentTask.getMethodsAsArray());
				if (validMethods.size() > 0){
					// pick a valid method
					int method = (int)(Math.random()*validMethods.size());
					ArrayList<Task> subtasks = validMethods.get(method).getSubTasks();
					
					if (parents.size() > 0){
						parentTasks.put(currentTask.getName(), parents.get(parents.size()-1));
					}
					
					parents.add(currentTask);
					parentLengths.add(subtasks.size());
					chosenMethods.put(currentTask.getName(), validMethods.get(method).getName());
					
					for (int i = 0; i < subtasks.size(); i++){
						//only add if haven't done it yet
						if (tasksClone.contains(subtasks.get(i))){
							newValidTasks.add(subtasks.get(i));
						}
					}
				}
				
				for(int i = 0; i<validTasks.size(); i++){
					if (!newValidTasks.contains(validTasks.get(i))){
						newValidTasks.add(validTasks.get(i));
					}
				}
				
				validTasks = newValidTasks;
				
//				validTask currentTask.getMethods(
				
			}
		}
		
		parents.clear();
		parentLengths.clear();
		// append the final quest
		for (int i = 0; i < tasks.size(); i++){
			if (tasksClone.get(i).getName().equalsIgnoreCase("return")){
				validTasks.add(tasksClone.get(i));
				break;
			}
		}
		while(validTasks.size() > 0){
//			System.out.println("SHOP");
			Task currentTask = validTasks.get(0);
			validTasks.remove(0);
			tasksClone.remove(currentTask);
			
			if(currentTask.isPrimative()){
				if (currentTask.getPreConditions() == null || stateClone.areAxiomsTrue(currentTask.getPreConditions())){
					currentTask.executeOperation(0, stateClone);
					
					plan.add(currentTask);
					// add me some parents
					if (parents.size() > 0){
//						if (parentTasks.get(currentTask.getName()) != null){
//							Log.d("RAD", parentTasks.get(currentTask.getName())+" is about to be overwritten with: "+parents.get(parents.size()-1));
//						}
						
						int n = parentLengths.get(parentLengths.size()-1).intValue()-1;
						if (n <= 0){
							parentLengths.remove(parentLengths.size()-1);
							parents.remove(parents.size()-1);
						}
						else{
							parentLengths.set(parentLengths.size()-1, n);
						}
					}
//					validTasks = getValidTasks(stateClone, tasksClone, validTasks);
				}
				else{
					tasksClone.add(currentTask);
				}
			}
			else{
				
				ArrayList<Task> newValidTasks = new ArrayList<Task>();
//				newValidTasks.add(currentTask.getMethods().getItem(currentTask.getMethods().getKeysAsArray()[0].toString()).getSubTasks().get(0));
				
				
				ArrayList<Method> validMethods = getValidMethods(stateClone, currentTask.getMethodsAsArray());
				if (validMethods.size() > 0){
					
					// pick a valid method
					int method = (int)(Math.random()*validMethods.size());
					ArrayList<Task> subtasks = validMethods.get(method).getSubTasks();
					
					if (parents.size() > 0){
						parentTasks.put(currentTask.getName(), parents.get(parents.size()-1));
					}
					
					parents.add(currentTask);
					parentLengths.add(subtasks.size());
					chosenMethods.put(currentTask.getName(), validMethods.get(method).getName());
					
					for (int i = 0; i < subtasks.size(); i++){
						//only add if haven't done it yet
						if (tasksClone.contains(subtasks.get(i))){
							newValidTasks.add(subtasks.get(i));
						}
					}
				}
				
				for(int i = 0; i<validTasks.size(); i++){
					if (!newValidTasks.contains(validTasks.get(i))){
						newValidTasks.add(validTasks.get(i));
					}
				}
				
				validTasks = newValidTasks;
				
//				validTask currentTask.getMethods(
				
			}
		}
	}
	
	public void checkState(WorldState state, Task task, ArrayList<Task> taskList){
		Task parent = parentTasks.get(task.getName());		
		ArrayList<Method> methods = parent.getMethodsAsArray();
		// get the method we picked for this task
		int method = 0;
		for (int i = 0; i < methods.size(); i++){
			if (methods.get(i).getName().equals(chosenMethods.get(parent.getName()))){
				method = i;
				break;
			}
		}
		
		Log.d("RAD", "the method is: "+method+" "+methods.get(method).getName());
		
		//(parent.getPreConditions() != null && !state.areAxiomsTrue(parent.getPreConditions()))
		
		
		
		// if the preconditions are no longer true, choose another valid method
		if (methods.get(method).getPreConditions() != null && !state.areAxiomsTrue(methods.get(method).getPreConditions())){
			boolean didReplan = false;
			for (int i = 0; i < methods.size(); i++){
				if (i != method){
					// check if valid
					if (methods.get(i).getPreConditions() == null || state.areAxiomsTrue(methods.get(i).getPreConditions())){
						// replace current task set with these new ones and replan ...
						ArrayList<Task> validTasks = new ArrayList<Task>(methods.get(i).getSubTasks());
						
						Log.d("RAD", "Using method: "+methods.get(i).getName());
						Log.d("RAD", "Using method: "+methods.get(i).getSubTasks());
						
						plan.clear();
						
						doShop(validTasks, state, taskList);
						didReplan = true;
						
						Log.d("RAD", "new plan: "+plan);
						break;
					}
				}
			}
			if (!didReplan){
				run(state, taskList);
				
				Log.d("RAD", "new plan: "+plan);
			}
		}
		else{
			Log.d("RAD", "method is somehow valid, which is a dirty lie!");
		}
		
	}
	
	/**
	 * Checks the ending
	 */
	public void checkEnding(WorldState state, ArrayList<Task> taskList){
		if (!plannedEnding){
			Task parent = parentTasks.get(plan.get(0).getName());
//			Log.d("RAD", parent+" is parent of "+plan.get(0));
			if (parent != null){
				ArrayList<Method> methods = parent.getMethodsAsArray();
				// get the method we picked for this task
				int method = 0;
				for (int i = 0; i < methods.size(); i++){
					if (methods.get(i).getName().equals(chosenMethods.get(parent.getName()))){
						method = i;
						break;
					}
				}
				
				// check if this is the FINAL quest, if so
				if (methods.get(method).getName().contains("END")){
					// reshuffle this quest ???
					run(state, taskList);
					plannedEnding = true;
					Log.d("RAD", "ending plan is: "+plan);
				}
			}
		}
	}
	
	public ArrayList<Task> getValidTasks(WorldState state, ArrayList<Task> tasks, ArrayList<Task> validTasks){		
		//remove invalid takss
		/*for (int i = 0; i < validTasks.size(); i++){
			if (validTasks.get(i).getPreConditions() != null && !state.areAxiomsTrue(validTasks.get(i).getPreConditions())){
				validTasks.remove(i--);
			}
		}*/
		// add new valid tasks
		for (int i = 0; i < tasks.size(); i++){
			if (!validTasks.contains(tasks.get(i)) && !tasks.get(i).isPrimative()
					&& (!tasks.get(i).getName().equalsIgnoreCase("return")) 
					&& (tasks.get(i).getPreConditions() == null || state.areAxiomsTrue(tasks.get(i).getPreConditions()))){
				validTasks.add(tasks.get(i));
			}
		}
		
		return validTasks;
		
	}
	
	public ArrayList<Method> getValidMethods(WorldState state, ArrayList<Method> methods){
		
		ArrayList<Method> validMeths = new ArrayList<Method>();
		for(int i = 0; i<methods.size(); i++){
			
			if(methods.get(i).getPreConditions() == null || state.areAxiomsTrue(methods.get(i).getPreConditions())){
				validMeths.add(methods.get(i));
			}
			
		}
		return validMeths;
		
	}
	

	public void removeTask(Task task, ArrayList<Task> taskList){
		plan.remove(task);
		while (parentTasks.get(task.getName()) != null){
			task = parentTasks.get(task.getName());
			Log.d("RAD", "removing parent: "+task);
			if (!taskList.remove(task)){
				break;
			}
		}
	}
}
