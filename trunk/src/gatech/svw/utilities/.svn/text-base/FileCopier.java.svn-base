package gatech.svw.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

/**
 * Copies files from the apk to the sd card
 * @author Jeff Bernard
 * @date October 2011
 */
public class FileCopier
{
	/**
	 * Copies a file (from the asset folder) to the sd card
	 * @param src the file to copy
	 * @param assets the asset manager
	 */
	
	static public void copy(String src, AssetManager assets)
	{
		File sdcard = Environment.getExternalStorageDirectory();
		String dest = sdcard+"/Android/data/gatech.svw"; //we're supposed to store stuff the same package as our app
		
		File dir = new File(dest);
		dir.mkdirs();
	    InputStream in;
		try
		{
//			for(int i = 0; i<assets.list("").length; i++)
//			{
//				Log.d("RAD", "here is assets " + assets.list("")[i]);
//			}
			
			in = assets.open(src);
//			Log.d("RAD", "here is 1");
		    OutputStream out = new FileOutputStream(dest+"/"+src);
//		    Log.d("RAD", "here is 2");
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) // read the file and write it back out
		    {
		    	out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}
		catch (IOException e)
		{
			Log.d("RAD", "Error copying "+src+" to "+dest + " " + e);
			Log.e("RAD", e.toString());
		}
	}
}
