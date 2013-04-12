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

	Button newGame, settings, about;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		@SuppressWarnings("unused")
		TextView tv = new TextView(this);
		setContentView(R.layout.title_screen);
		initializeVars();

	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		newGame = (Button) findViewById(R.id.bNewGame);
		settings = (Button) findViewById(R.id.bSettings);
		about = (Button) findViewById(R.id.bAbout);
		Log.d("RAD", ""+settings);
		newGame.setOnClickListener(this);
		settings.setOnClickListener(this);
		about.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bNewGame:
			Log.w("myApp", "newgame");
			Intent intent = new Intent(TitleScreenActivity.this, QuestDealerActivity.class);
			startActivity(intent);
			break;
		case R.id.bSettings:
			Log.w("myApp", "settings");
			Intent intent2 = new Intent(TitleScreenActivity.this, SettingsActivity.class);
			startActivity(intent2);
			break;
		case R.id.bAbout:
			Log.w("myApp", "about");
			Intent intentAbout = new Intent(TitleScreenActivity.this, AboutScreenActivity.class);
			startActivity(intentAbout);
		}
		
	}
}