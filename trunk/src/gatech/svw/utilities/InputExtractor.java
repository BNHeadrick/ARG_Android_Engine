package gatech.svw.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.Environment;

import KnowledgeBase.Axiom;
import KnowledgeBase.Method;
import KnowledgeBase.Operator;
import Task.Content;
import Task.MinigameContent;
import Task.Task;
import Task.Tasks;
import Task.TextContent;

public class InputExtractor {

	File file = null;
	Document doc = null;

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = null;
	
	public InputExtractor(String filename){
		
		try {
//			file = new File("/mnt/sdcard/testInput.xml");
//			file = new File("/mnt/sdcard/earlyInput.xml");
			File sdcard = Environment.getExternalStorageDirectory();
			file = new File(sdcard+"/Android/data/gatech.svw/"+filename);
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);

		} catch (FileNotFoundException fnf) {
			// TODO Auto-generated catch block
			fnf.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Task> extract(){
		NodeList nodeLst = doc.getElementsByTagName("task");
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int s = 0; s < nodeLst.getLength(); s++) { // iterate through tasks

			Node taskNode = nodeLst.item(s);

			if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
				
				NodeList taskChildren = taskNode.getChildNodes();
				Task task = new Task(taskNode.getAttributes().getNamedItem("name").getNodeValue());
				System.out.println("extracting : "+task);
				for (int i = 0; i < taskChildren.getLength(); i++){ // iterate through the individual task tag
					Node node = taskChildren.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE){
						if (node.getNodeName().equalsIgnoreCase("params")){
							task.setParams(readParams(node.getChildNodes()));
//							System.out.println("param: "+task.params.get(0));
						}
						
						else if (node.getNodeName().equalsIgnoreCase("preconditions")){
							task.setPreConditions(readAxioms(node.getChildNodes()));
//							System.out.println("axiom: "+task.getPreConditions().get(0).getName()+task.getPreConditions().get(0).getParams().toString());
						}
						
						else if (node.getNodeName().equalsIgnoreCase("postconditions")){
							task.setOperators(readOperators(node.getChildNodes()));
						}
						
						else if (node.getNodeName().equalsIgnoreCase("methods")){
							task.setMethods(readMethods(node.getChildNodes()));
						}
						
						else if (node.getNodeName().equalsIgnoreCase("content")){
							task.setContent(readContent(node.getAttributes().getNamedItem("type").getNodeValue(), node.getChildNodes()));
						}
					}
				}
				
				tasks.add(task);
				
			}
		}
		
		Tasks taskHash = new Tasks(tasks);
		for (int i = 0; i < tasks.size(); i++){
			if (!tasks.get(i).isPrimative()){
				tasks.get(i).updateMethods(taskHash);
			}
		}
		
		return tasks;
	}
	
	public Content readContent(String type, NodeList nodes){
		Content content = null;
		
		if (type.equalsIgnoreCase("conversation")){
			content = new TextContent();
			for (int i = 0; i < nodes.getLength(); i++){
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE){
					if (node.getNodeName().equalsIgnoreCase("name")){
						((TextContent)content).setName(node.getFirstChild().getNodeValue());
					}
					else if (node.getNodeName().equalsIgnoreCase("body")){
						((TextContent)content).setBody(node.getFirstChild().getNodeValue());
					}
					else if (node.getNodeName().equalsIgnoreCase("image")){
						((TextContent)content).setImage(node.getFirstChild().getNodeValue());
					}
				}
			}
		}
		else if (type.equalsIgnoreCase("minigame")){
			content = new MinigameContent();
			for (int i = 0; i < nodes.getLength(); i++){
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE){
					if (node.getNodeName().equalsIgnoreCase("location")){
						String info = "";
						if (node.getAttributes().getNamedItem("desc") != null){
							info = node.getAttributes().getNamedItem("desc").getNodeValue();
						}
						((MinigameContent)content).setLocation(node.getAttributes().getNamedItem("id").getNodeValue(), info);
					}
					else if (node.getNodeName().equalsIgnoreCase("trigger")){
						((MinigameContent)content).addTrigger(node.getAttributes().item(0).getNodeName(), node.getAttributes().item(0).getNodeValue());
					}
				}
			}
		}
		
		return content;
	}
	
	public ArrayList<String> readParams(NodeList nodes){
		ArrayList<String> params = new ArrayList<String>();
		
		for (int i = 0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				if (node.getAttributes().getNamedItem("id") != null){
					params.add("[ID]"+node.getAttributes().getNamedItem("id").getNodeValue());
				}
				else{
					params.add(node.getFirstChild().getNodeValue());
				}
			}
		}
		
		return params;
	}
	
	public ArrayList<Axiom> readAxioms(NodeList nodes){
		ArrayList<Axiom> axioms = new ArrayList<Axiom>();
		
		for (int i = 0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				axioms.add(new Axiom(node.getAttributes().getNamedItem("name").getNodeValue(), readParams(node.getChildNodes())));
				Node ignore = node.getAttributes().getNamedItem("ignore-params");
				if (ignore != null && ignore.getNodeValue().equalsIgnoreCase("true")){
					axioms.get(axioms.size()-1).ignoreParams();
				}
			}
		}
		
		return axioms;
	}
	
	
	public ArrayList<Operator> readOperators(NodeList nodes) {
		ArrayList<Operator> ops = new ArrayList<Operator>();

		for (int i = 0; i < nodes.getLength(); i++){
			Node parent = nodes.item(i);	//operator tag
			
			if (parent.getNodeType() == Node.ELEMENT_NODE){
				NodeList children = parent.getChildNodes();	
				ArrayList<Axiom> removeAxioms = new ArrayList<Axiom>();
				ArrayList<Axiom> addAxioms = new ArrayList<Axiom>();
				for (int j = 0; j < children.getLength(); j++){
					Node current = children.item(j);
					if (current.getNodeName().equalsIgnoreCase("remove-axioms")){
						removeAxioms = readAxioms(current.getChildNodes());
					}
					else if (current.getNodeName().equalsIgnoreCase("add-axioms")){
						addAxioms = readAxioms(current.getChildNodes());
					}
				}
				ops.add(new Operator(removeAxioms, addAxioms));
			}
		}
		
		return ops;
	}
	
	public ArrayList<Method> readMethods(NodeList nodes) {
		ArrayList<Method> meths = new ArrayList<Method>();
		
		for (int i = 0; i < nodes.getLength(); i++){
			Node parent = nodes.item(i);	//method tag, below this is precondition
			if (parent.getNodeType() == Node.ELEMENT_NODE){
				NodeList children = parent.getChildNodes();	
				
//				ArrayList<Method> methList = new ArrayList<Method>();
				
//				System.out.println(parent.getAttributes().getNamedItem("name").getNodeValue());
//				meths.add(new Method(parent.getAttributes().getNamedItem("name").getNodeValue()));
				
				Method currMethod = new Method(parent.getAttributes().getNamedItem("name").getNodeValue());
				
				for (int j = 0; j < children.getLength(); j++){
					Node current = children.item(j);
					
					if (current.getNodeName().equalsIgnoreCase("preconditions")){
//						System.out.println("in pre");
						currMethod.setPreConditions(readAxioms(current.getChildNodes()));
					}
					else if (current.getNodeName().equalsIgnoreCase("subtask")){
//						System.out.println("in sub");
						currMethod.setSubTasksAsStrings(current.getAttributes().getNamedItem("name").getNodeValue(), readParams(current.getChildNodes()));
						
//						System.out.println("subInfo: " + currMethod);
					}
					
				}
				meths.add(currMethod);
			}
		}
		
		return meths;
	}
}
