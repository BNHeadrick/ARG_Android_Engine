package gatech.svw.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * A list of things to select from
 * @author Jeff Bernard
 * @date October 2011
 */
public class ListMenu extends ListActivity
{
	private ListAdapter listAdapter; /**< widget to display the list */
	private ArrayList<String> list; /**< the list to display */
	
	@Override
	/**
	 * Creates the list menu
	 * @param savedInstanceState
	 */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		list = new ArrayList<String>();

		loadList(Environment.getExternalStorageDirectory()+"/Android/data/gatech.svw/");
		
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(listAdapter);
		getListView().setTextFilterEnabled(true);
	}
	
	/**
	 * Loads the list to display
	 * @param folder the folder where the files are located
	 */
	private void loadList(String folder)
	{
		Log.d("RAD", folder);
		File dir = new File(folder);
		Log.d("RAD", ""+dir.isDirectory());
		if(dir.isDirectory()) // ensure this is actually a directory
		{
			String[] filenames = dir.list();
			for (int i = 0; i < filenames.length; i++)
			{
				if (filenames[i].endsWith(".xml"))
				{
					Log.d("RAD", filenames[i]);
					list.add(filenames[i]);
				}
			}
			
		}
	 }
	
	@Override
	/**
	 * When a list item is clicked
	 */
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		String file = (String)getListView().getItemAtPosition(position);
		
		Intent result = new Intent();
		result.putExtra("xmlfile", file);
		setResult(Activity.RESULT_OK, result);
		finish();
	}
}
