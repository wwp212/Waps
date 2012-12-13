package com.wyattmakesstuff.here;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Here extends FragmentActivity {
	private static GoogleMap map;
	private DBHelper db;
	private Context context;
	WapDialog placeDialog;
	CategoryDialog categoryDialog;
	WapsSingleton waps;
	private static final String GPS_CATEGORY = "GPS Location";
	private static final float GPS_COLOR = 120.0f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.here);
        
        context = this;
        waps = WapsSingleton.getInstance(context);
        
        final ActionBar actionBar = getActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.action_bar_custom, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
        placeDialog = new WapDialog();
        categoryDialog = new CategoryDialog();
        
        map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        GPS gps = new GPS(this);
        if (gps.isNetworkEnabled){
        	showCurrentLocation(gps);
        }
        else{
        	gps.showSettingsAlert();
        }

        displayStoredPoints();
        setClickEvents();
    }
	
	public void showCurrentLocation(GPS gps){
		LatLng location = new LatLng(gps.getLatitude(), gps.getLongitude());
		map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(location, 15, 0, 0)));
		Wap wap = new Wap(location, "you are here", GPS_CATEGORY);
		placeMarker(wap);
	}
	
	public void displayStoredPoints(){
		List<Wap> wapList = waps.getAllWaps();
		for (int i = 0; i < wapList.size(); i++){
			placeMarker(wapList.get(i));
		}
	}
	
	public void setClickEvents(){
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng clickLocation) {
				if (waps.getAllCategories().size() > 0){
					placeDialog.showWapDialog(getFragmentManager(), "WYATT", clickLocation);
				}
				else{
					Context context = getApplicationContext();
		        	CharSequence text = "You must have at least one category to create points";
		        	int duration = Toast.LENGTH_LONG;

		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.show();
		        	
		        	categoryDialog.show(getFragmentManager(), "Category");
				}
			}
		});
		
		map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng clickLocation) {
				//Nothing yet
			}
		});
	}
	
	public static void placeMarker(Wap wap){
		BitmapDescriptor icon = null;
		if (wap.getCategory().toString().equals(GPS_CATEGORY)){
			icon = BitmapDescriptorFactory.defaultMarker(GPS_COLOR);
		}
		else{
			icon = BitmapDescriptorFactory.defaultMarker(WapsSingleton.getColorForCat(wap.getCategory()));
		}
		map.addMarker(new MarkerOptions()
        .position(wap.getLocation())
        .title(wap.getCategory().toString())
        .icon(icon));
	}
	
	public static float translateColor(String textColor){
		float textConst = 0.0f;
		if (textColor.equals("Blue")){
			textConst = 240.0f;
		}
		else if (textColor.equals("Green")){
			textConst = 120.0f;
		}
		else if (textColor.equals("Orange")){
			textConst = 30.0f;
		}
		else if (textColor.equals("Violet")){
			textConst = 270.0f;
		}
		else if (textColor.equals("Yellow")){
			textConst = 60.0f;
		}
		Log.d("WYATT", "YO THE COLOR IS : " + textConst);
		return textConst;
	}
	  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.here, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        boolean ret = false;
        if (item.getItemId() == R.id.menu_settings)
        {
        	Context context = getApplicationContext();
        	CharSequence text = "We did it!!!";
        	int duration = Toast.LENGTH_SHORT;

        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
            ret = true;
        } 
        return ret;
    }
    
    public void addCategory(View v){
    	categoryDialog.show(getFragmentManager(), "Category");
    }
    
    
}
