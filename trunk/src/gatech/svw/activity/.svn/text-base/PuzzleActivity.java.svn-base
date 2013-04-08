package gatech.svw.activity;

import gatech.svw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PuzzleActivity extends Activity {

	Button bMapClick;
	Button bConversationClick;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		@SuppressWarnings("unused")
		TextView tv = new TextView(this);
		setContentView(R.layout.puzzle_screen);
		
		bMapClick = (Button)findViewById(R.id.bMap);
		
		bMapClick.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent ourIntent = new Intent(PuzzleActivity.this, RadiationMapActivity.class);
		        startActivity(ourIntent);
			}
		});
		
		bConversationClick = (Button)findViewById(R.id.bConversation);
		
		bConversationClick.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent ourIntent = new Intent(PuzzleActivity.this, ConversationActivity.class);
		        startActivity(ourIntent);
			}
		});
		
//		initializeVars();
	}
}
