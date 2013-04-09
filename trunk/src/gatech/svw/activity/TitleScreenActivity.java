package gatech.svw.activity;


import gatech.svw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TitleScreenActivity extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */

	Button newGame, settings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		@SuppressWarnings("unused")
		TextView tv = new TextView(this);
		setContentView(R.layout.title);
		initializeVars();
		
		
		

		/*
		Class ourClass = null;
		try {
			ourClass = Class.forName("gatech.svw.Test1");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent ourIntent = new Intent(TitleScreen.this, ourClass);
		startActivity(ourIntent);
		*/

	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		newGame = (Button) findViewById(R.id.bNewGame);
		settings = (Button) findViewById(R.id.bSettings);
		Log.d("RAD", ""+settings);
		newGame.setOnClickListener(this);
		settings.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bNewGame:
//			Class newGameClass = null;
//			try {
//				//newGameClass = Class.forName("gatech.svw.activity.RadiationMapActivity");
//				//newGameClass = Class.forName("gatech.svw.activity.ConversationActivity");
//				newGameClass = Class.forName("gatech.svw.activity.QuestDealerActivity");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Intent intent = new Intent(TitleScreenActivity.this, QuestDealerActivity.class);
			startActivity(intent);
			
			break;
		case R.id.bSettings:
			
//			Class settingsClass = null;
//			try {
//				settingsClass = Class.forName("gatech.svw.activity.SettingsActivity");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Intent intent2 = new Intent(TitleScreenActivity.this, SettingsActivity.class);
			startActivity(intent2);
			
		}
		
	}
}