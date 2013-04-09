package gatech.svw.activity;

import java.util.ArrayList;

import gatech.svw.R;
import gatech.svw.utilities.ConversationState;
import Task.TaskTrigger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConversationActivity extends Activity {
	
	LinearLayout llMessages;
	EditText etNewMessageToSend;
	Button bSendMessage;
	Button bMapClick; 
	Button bPuzzleClick; 
	Button bCheckMessages; 
	
	ArrayList<String> Senders = new ArrayList<String>();
	ArrayList<String> Bodies = new ArrayList<String>();
	ArrayList<String> Types = new ArrayList<String>();
	ArrayList<Drawable> Graphics = new ArrayList<Drawable>();

	private static ConversationActivity me = null;
	private boolean isActive;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		isActive = false;
		ConversationState.saveState(Senders, Bodies, Types, Graphics);
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		isActive = false;
		ConversationState.saveState(Senders, Bodies, Types, Graphics);
		super.onPause();
		finish();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		isActive = true;
		ConversationState.loadState(Senders, Bodies, Types, Graphics);
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		isActive = true;
		ConversationState.loadState(Senders, Bodies, Types, Graphics);
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		isActive = true;
		ConversationState.loadState(Senders, Bodies, Types, Graphics);
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		isActive = false;
		ConversationState.saveState(Senders, Bodies, Types, Graphics);
		super.onStop();
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.conversation_screen);
		
		ConversationState.loadState(Senders, Bodies, Types, Graphics);
		
		me = this;
		isActive = true;
		
		llMessages = (LinearLayout)findViewById(R.id.llTexts);
		etNewMessageToSend = (EditText)findViewById(R.id.etMessageToSend);
		bSendMessage = (Button)findViewById(R.id.bSendMessage);
		bMapClick = (Button)findViewById(R.id.bMap);
		bCheckMessages = (Button)findViewById(R.id.bCheckMessages);
		
		bMapClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent ourIntent = new Intent(ConversationActivity.this, RadiationMapActivity.class);
		        startActivity(ourIntent);
			}
		});
		
		bPuzzleClick = (Button)findViewById(R.id.bMiniGames);
		
		bPuzzleClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent ourIntent = new Intent(ConversationActivity.this, PuzzleActivity.class);
		        startActivity(ourIntent);
			}
		});
		
		bSendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayMessage("", etNewMessageToSend.getText().toString(), null, "Send");
				
				Senders.add("");
				Bodies.add(etNewMessageToSend.getText().toString());
				Types.add("Send");
				Graphics.add(null);
				
				etNewMessageToSend.setText("");
				
				ConversationState.saveState(Senders, Bodies, Types, Graphics);
				
				TaskTrigger.check(null, false);
			}
		});
		
		bCheckMessages.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				TaskTrigger.check(null, false);
			}
		});
		
		if(Senders.size() < 1)
		{
			Drawable image = this.getResources().getDrawable(R.drawable.radiationmarker);
			displayMessage("GT Emergency Notification System", "Emergency Alert! Radiation sickness has been reported on and around campus. Move to Helicopter location for immediate evacuation. Coordinates of helicopter to follow shortly.", image, "Recvd");
			
			Senders.add("GT Emergency Notification System");
			Bodies.add("Emergency Alert! Radiation sickness has been reported on and around campus. Move to Helicopter location for immediate evacuation. Coordinates of helicopter to follow shortly.");
			Types.add("Recvd");
			Graphics.add(image);
			
			
//			
//			displayMessage("Brandon", "Hey man, I don't know if you heard, but there's some serious shit going down now. Let's meet up and get the hell out of dodge.", null, "Recvd");
			
//			Senders.add("Brandon");
//			Bodies.add("Hey man, I don't know if you heard, but there's some serious shit going down now. Let's meet up and get the hell out of dodge.");
//			Types.add("Recvd");
//			Graphics.add(null);
			
//			displayMessage("Dean Frizbell", "Oh no! We've received a bomb threat from the same shadowy menace responsible for the radiation attacks. Someone find it and defuse the bomb! Hurry!!!", null, "Recvd");
//			
//			Senders.add("Dean Frizbell");
//			Bodies.add("Oh no! We've received a bomb threat from the same shadowy menace responsible for the radiation attacks. Someone find it and defuse the bomb! Hurry!!!");
//			Types.add("Recvd");
//			Graphics.add(null);
			
//			displayMessage("Dean Frizbell", "Thank you so much! This institution owes you a debt of gratitude! Enjoy this token of our thanks.", image, "Recvd");
//			
//			Senders.add("Dean Frizbell");
//			Bodies.add("Thank you so much! This institution owes you a debt of gratitude! Enjoy this token of our thanks.");
//			Types.add("Recvd");
//			Graphics.add(image);
			
//			displayMessage("Helicopter Pilot", "Folks we're landing at the LZ. Evac in T-20 minutes. Lookout for this bird.", image, "Recvd");
//			
//			Senders.add("Helicopter Pilot");
//			Bodies.add("Folks we're landing at the LZ. Evac in T-20 minutes. Lookout for this bird.");
//			Types.add("Recvd");
//			Graphics.add(image);
//			
//			displayMessage("GTENS", "Emergency Alert! Evacuation helicopter crash landed! Multiple fatalities. Await further news!", null, "Recvd");
//			
//			Senders.add("GTENS");
//			Bodies.add("Emergency Alert! Evacuation helicopter crash landed! Multiple fatalities. Await further news!");
//			Types.add("Recvd");
//			Graphics.add(null);
			
			ConversationState.saveState(Senders, Bodies, Types, Graphics);
		}
		else
		{
			testConversationActivity(Senders, Bodies, Types, Graphics);
		}
		
		ConversationState.saveState(Senders, Bodies, Types, Graphics);
		
		// test debug whatevs
//		ConversationState.addMessage("Test Sender", "Test Message", "Recvd", null);
		
//		if (SettingsActivity.isCheatingEnabled()){ // cheating is enabled, so don't rely on GPS lock
//			Log.d("RAD", "cheating");
//			TaskTrigger.check(null);
//		}
		
		for (int i = 0; i < ConversationState.lastReadMessage; i+=2){
        	llMessages.getChildAt(i).setBackgroundDrawable(me.getResources().getDrawable(R.drawable.popupbackground));
        	llMessages.invalidate();
        }
		ConversationState.lastReadMessage = llMessages.getChildCount();
		
		TaskTrigger.check(null, false); // run the first quest
	}
	
	/**
	 * shows the activity
	 */
	public static void show(){
		if (me != null){
			((Vibrator)me.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(300); // let them know they have a message
			
			if (!me.isActive){
				Intent ourIntent = new Intent(me, ConversationActivity.class);
		        me.startActivity(ourIntent);
			}
			else{
				ConversationState.loadState(me.Senders, me.Bodies, me.Types, me.Graphics);
				me.displayMessage(me.Senders.get(me.Senders.size()-1), me.Bodies.get(me.Bodies.size()-1), me.Graphics.get(me.Graphics.size()-1), me.Types.get(me.Types.size()-1));
				ConversationState.lastReadMessage = me.llMessages.getChildCount();
			}
		}
	}
	
	/**
	 * gets an image drawable
	 * @param name
	 * @return
	 */
	public static Drawable getImage(String name){
		if (me != null){
			//debug
			return me.getResources().getDrawable(R.drawable.helicopter);//me.getResources().getIdentifier(name, "drawable", null));
		}
		return null;
	}
	
	public void testConversationActivity(ArrayList<String> Senders, ArrayList<String> Bodies, ArrayList<String> Types, ArrayList<Drawable> Graphics)
	{
		for(int index = 0; index < Senders.size(); index++)
		{
			displayMessage(Senders.get(index), Bodies.get(index), Graphics.get(index), Types.get(index));
		}
	}
	
	public void displayMessage(String sSender, String sText, Drawable graphic, String sType)
	{
		LinearLayout llNewMessage = new LinearLayout(this);
		TextView tvNewTextTitle = new TextView(this);
		TextView tvNewTextContent = new TextView(this);
		TextView tvBlankFiller = new TextView(this);
		
		llMessages.setOrientation(LinearLayout.VERTICAL);
		llNewMessage.setPadding(10, 10, 10, 10);
		llNewMessage.setOrientation(LinearLayout.VERTICAL);
		llNewMessage.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		
		if(sType.equalsIgnoreCase("Send"))
		{
			tvNewTextTitle.setText("Me");
			tvNewTextTitle.setGravity(android.view.Gravity.RIGHT);
			tvNewTextContent.setText(sText + "\n");
			tvNewTextContent.setGravity(android.view.Gravity.RIGHT);
			
			llNewMessage.addView(tvNewTextTitle);
			llNewMessage.addView(tvNewTextContent);
			llNewMessage.setGravity(android.view.Gravity.RIGHT);
			
			Drawable image = this.getResources().getDrawable(R.drawable.popupbackground);
			llNewMessage.setBackgroundDrawable(image);
		}
		else
		{
			tvNewTextTitle.setText(sSender);
			tvNewTextTitle.setGravity(android.view.Gravity.LEFT);
			tvNewTextContent.setText("\n" + sText + "\n");
			tvNewTextContent.setGravity(android.view.Gravity.LEFT);
			ImageView ivGraphic = new ImageView(this);
			
			if(graphic != null)
			{
				ivGraphic.setImageDrawable(graphic);
			}
			
			llNewMessage.addView(tvNewTextTitle);
			llNewMessage.addView(tvNewTextContent);
			llNewMessage.addView(ivGraphic);
			llNewMessage.setGravity(android.view.Gravity.LEFT);
			
			Drawable image = this.getResources().getDrawable(R.drawable.unreadbackground);
			llNewMessage.setBackgroundDrawable(image);
		}
		
		llMessages.addView(llNewMessage);
		llMessages.addView(tvBlankFiller);
		llMessages.invalidate();
		
		ConversationState.saveState(Senders, Bodies, Types, Graphics);
	}
}
