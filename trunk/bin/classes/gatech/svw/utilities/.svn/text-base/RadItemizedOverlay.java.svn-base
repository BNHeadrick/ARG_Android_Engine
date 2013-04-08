package gatech.svw.utilities;

import gatech.svw.activity.SettingsActivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class RadItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	TableLayout tlPopUpView;
	TextView tvPopUpTitle;
	TextView tvPopUpText;
	Button bIAmHere;
	Button bIDontWanna;
	GeoPoint gpSelectedOverlayPoint;
	
	public RadItemizedOverlay(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
	}
	
	public RadItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenter(defaultMarker));
		  setmContext(context);
		  populate();
		}
	
	public void initialize(TableLayout tlPopUpView, TextView tvPopUpTitle, TextView tvPopUpText, Button bIAmHere, Button bIDontWanna)
	{
		this.tlPopUpView = tlPopUpView;
		this.tvPopUpTitle = tvPopUpTitle;
		this.tvPopUpText = tvPopUpText;
		this.bIAmHere = bIAmHere;
		this.bIDontWanna = bIDontWanna;
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	public void removeMyLocationOverlay()
	{
    	for(int index = 0; index < mOverlays.size(); index++)
    	{
    		if(mOverlays.get(index).getTitle().equalsIgnoreCase("My Location"))
    		{
    			mOverlays.remove(index);
    		}
    	}
	}
	
	/**
	 * Removes an overlay
	 * @param name
	 */
	public void removeOverlay(String name){
		for (int i = 0; i < mOverlays.size(); i++){
			if (mOverlays.get(i).getTitle().equals(name)){
				mOverlays.remove(i);
			}
		}
	}
	
	/**
	 * changes an overlays descreiption
	 * @param name
	 * @param desc
	 */
	public void changeOverlayDescription(String name, String desc){
		/// TODO?
//		for (int i = 0; i < mOverlays.size(); i++){
//			if (mOverlays.get(i).getTitle().equals(name)){
//				mOverlays.get(i).
//			}
//		}
	}

	public void clearOverlays()
	{
		mOverlays.clear();
	}
	
	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		
		gpSelectedOverlayPoint = item.getPoint();
		String sTitle = item.getTitle();
		String sText = item.getSnippet();
		
		sText += "\nLatitude: " + gpSelectedOverlayPoint.getLatitudeE6()/1E6 + "\nLongitude: " + gpSelectedOverlayPoint.getLongitudeE6()/1E6;
		
		tvPopUpTitle.setText(sTitle);
		tvPopUpText.setText(sText);
		
		if(sTitle.equalsIgnoreCase("My Location") || sTitle.contains("Radiation Zone"))
		{
			bIAmHere.setVisibility(View.GONE);
			bIDontWanna.setVisibility(View.GONE);
		}
		else if(SettingsActivity.isCheatingEnabled())
		{
			bIAmHere.setVisibility(View.VISIBLE);
		}
		
		if(!(sTitle.contains("Radiation Zone") || sTitle.equalsIgnoreCase("My Location") || (bIDontWanna.getVisibility() == View.VISIBLE)))
		{
			bIDontWanna.setVisibility(View.VISIBLE);
		}
		
		if(!tlPopUpView.isShown())
		{
			tlPopUpView.setVisibility(View.VISIBLE);
		}
		
		super.onTap(index);
		
		return true;
	}
	
	public GeoPoint getLastOverlayPoint()
	{
		return gpSelectedOverlayPoint;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
}
