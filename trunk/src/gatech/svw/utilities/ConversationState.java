package gatech.svw.utilities;

import gatech.svw.activity.ConversationActivity;
import java.util.ArrayList;
import android.graphics.drawable.Drawable;

public class ConversationState {

//	private static ConversationState State;
	
//	private static LinearLayout llMessages;
//	private static EditText etNewMessageToSend;
//	private static Button bSendMessage;
	
	private static ArrayList<String> Senders = new ArrayList<String>();
	private static ArrayList<String> Bodies = new ArrayList<String>();
	private static ArrayList<String> Types = new ArrayList<String>();
	private static ArrayList<Drawable> Graphics = new ArrayList<Drawable>();
	private static int lastReply = -1;
	public static int lastReadMessage = 0;
	
//	protected ConversationState()
//	{
//		
//	}
//	
//	public ConversationState getInstance()
//	{
//		if(State == null)
//		{
//			State = new ConversationState();
//		}
//		
//		return State;
//	}
	
	public static void clear(){
		Senders.clear();
		Bodies.clear();
		Types.clear();
		Graphics.clear();
		lastReply = -1;
		lastReadMessage = 0;
	}
	
	public static void saveState(ArrayList<String> Senders, ArrayList<String> Bodies, ArrayList<String> Types, ArrayList<Drawable> Graphics)
	{
		if(!Senders.isEmpty())
		{
			ConversationState.Senders.clear();
			ConversationState.Senders.addAll(Senders);
		}
		
		if(!Bodies.isEmpty())
		{
			ConversationState.Bodies.clear();
			ConversationState.Bodies.addAll(Bodies);
		}
		
		if(!Types.isEmpty())
		{
			ConversationState.Types.clear();
			ConversationState.Types.addAll(Types);
		}
		
		if(!Graphics.isEmpty())
		{
			ConversationState.Graphics.clear();
			ConversationState.Graphics.addAll(Graphics);
		}
	}
	
	public static void loadState(ArrayList<String> Senders, ArrayList<String> Bodies, ArrayList<String> Types, ArrayList<Drawable> Graphics)
	{
		if(!ConversationState.Senders.isEmpty())
		{
			Senders.clear();
			Senders.addAll(ConversationState.Senders);
		}
		
		if(!ConversationState.Bodies.isEmpty())
		{
			Bodies.clear();
			Bodies.addAll(ConversationState.Bodies);
		}
		
		if(!ConversationState.Types.isEmpty())
		{
			Types.clear();
			Types.addAll(ConversationState.Types);
		}
		
		if(!ConversationState.Graphics.isEmpty())
		{
			Graphics.clear();
			Graphics.addAll(ConversationState.Graphics);
		}
	}
	
	/**
	 * adds a message
	 * @param sender
	 * @param body
	 * @param type
	 * @param graphic
	 */
	public static void addMessage(String sender, String body, String type, Drawable graphic){
		Senders.add(sender);
		Bodies.add(body);
		Types.add(type);
		Graphics.add(graphic);
		
		ConversationActivity.show();
	}
	
	/**
	 * Gets the body of latest reply
	 * @return reply
	 */
	public static String getLastReply(){
		for (int i = Types.size()-1; i > lastReply; i--){
			if (Types.get(i).equalsIgnoreCase("Send")){
				return Bodies.get(i);
			}
		}
		return "";
	}
	
	public static void incrementLastReply(){
		for (int i = Types.size()-1; i > lastReply; i--){
			if (Types.get(i).equalsIgnoreCase("Send")){
				lastReply = i;
				break;
			}
		}
	}
}
