package gatech.svw.activity;

import gatech.svw.R;
import gatech.svw.utilities.FileCopier;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

public class Splash extends Activity {

//	MediaPlayer ourSound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		AssetManager assetManager = getAssets();		
		FileCopier.copy("earlyInput.xml", assetManager);
		
		setContentView(R.layout.splash);
		
		// copy default-quest to sd card
		FileCopier.copy("default-quest.xml", getAssets());
		
//		ourSound = MediaPlayer.create(Splash.this, R.raw.beep);
//		ourSound.start();
		
		Thread timer = new Thread(){
			
			@Override
			public void run(){
//				Class ourClass = null;
				try{
					sleep(3000);
					//ourClass = Class.forName("gatech.svw.activity.TitleScreenActivity");
				} 
				catch (InterruptedException ie){
					ie.printStackTrace();
//					
				} 
//				catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				finally{
					Intent ourIntent = new Intent(Splash.this, TitleScreenActivity.class);
					
                    startActivity(ourIntent);
//				}
				
			}
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		ourSound.release();
		finish();	//finish splash activity, get rid of it, unless they open app again.
	}

}