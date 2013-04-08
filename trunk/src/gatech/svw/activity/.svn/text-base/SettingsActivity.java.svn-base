package gatech.svw.activity;

import gatech.svw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity
{
	// there's prolly a better way than static vars to preserve between calls to this activity
	private static String questXML = "default-quest.xml";
	private static boolean cheating = false;
	private static int timeLimit = 60;
	
	public static int getTimeLimit() {
		return timeLimit;
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello, Android");
		setContentView(R.layout.settings);
//		initializeVars();
		
		// check if we type in how long the game should be
		final EditText timeLimitField = (EditText) findViewById(R.id.settingsTimeLimit);
		timeLimitField.setText(""+timeLimit);
		timeLimitField.setInputType(InputType.TYPE_CLASS_NUMBER);
		timeLimitField.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				timeLimit = Integer.parseInt(timeLimitField.getText().toString());
		        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
		        	// Perform action on key press
		            return true;
		        }
		        return false;
			}
		});
		
		// check if the cheating box is selected
		final CheckBox checkbox = (CheckBox) findViewById(R.id.settingsCheating);
		checkbox.setChecked(cheating);
		checkbox.setText("Cheating: "+(cheating?"On":"Off"));
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
				cheating = isChecked;
				checkbox.setText("Cheating: "+(cheating?"On":"Off"));
			}
		});
		
		final Button questXMLBtn = (Button)findViewById(R.id.settingsChooseXML);
		questXMLBtn.setText(questXML);
		questXMLBtn.setOnClickListener(new OnClickListener() { // check for button press
			public void onClick(View v) {
//				Class newGameClass = null;
//				try {
//					newGameClass = Class.forName("gatech.svw.activity.ListMenu");
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				Intent intent = new Intent(SettingsActivity.this, ListMenu.class);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	/**
	 * Check for result of our button press
	 */
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
	  super.onActivityResult(requestCode, resultCode, data); 
	  switch(requestCode) { 
	    case (1) : { 
	      if (resultCode == Activity.RESULT_OK) { 
	    	  questXML = data.getStringExtra("xmlfile");
	    	  final Button questXMLBtn = (Button)findViewById(R.id.settingsChooseXML);
	  		  questXMLBtn.setText(questXML);
	      } 
	      break; 
	    } 
	  } 
	}
	
	public static boolean isCheatingEnabled() {
		return cheating;
	} 
	
	public static String getquestXML()
	{
		return questXML;
	}
}
