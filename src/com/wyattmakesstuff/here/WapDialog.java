package com.wyattmakesstuff.here;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class WapDialog extends DialogFragment{
	private LatLng clickLocation;
	private Spinner categorySpinner;
	private EditText inputText;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.place_dialog, null))
        // Add action buttons
               .setPositiveButton("Good", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                	   //String text = String.valueOf(spinner.getSelectedItem());
	            	   Wap wap = new Wap(clickLocation, inputText.getText().toString(), categorySpinner.getSelectedItem().toString());
	            	   Here.placeMarker(wap);
	            	   DBHelper db = new DBHelper(getActivity());
	            	   db.addWap(wap);
	            	   String text = categorySpinner.getSelectedItem().toString();
	            	   Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
	               		toast.show();
                   }
               })
               .setNegativeButton("Bad", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   Toast toast = Toast.makeText(getActivity().getApplicationContext(), "sucks", Toast.LENGTH_SHORT);
                  		toast.show();
                   }
               });      
        
		
        
        
        return builder.create();
    }
	
	@Override
	public void onStart() {
	    super.onStart();
	    setCategorySpinner();
	    setTextPrompt();
	}
	
	public void setTextPrompt(){
		inputText = (EditText) getDialog().findViewById(R.id.editText1);
	}
	
	public void setCategorySpinner(){
		categorySpinner = (Spinner) getDialog().findViewById(R.id.spinner2);
		List<CharSequence> categories = getCategoryList();
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
	        android.R.layout.simple_spinner_item, categories);
		// Specify the layout to use when the list of choices appears
		adapter.notifyDataSetChanged();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		categorySpinner.setAdapter(adapter);
	}
	
	public List<CharSequence> getCategoryList(){
		WapsSingleton waps = WapsSingleton.getInstance(getActivity());
		return waps.getAllCategories();
	}
	
	public void showWapDialog(FragmentManager fm, String tag, LatLng location){
		clickLocation = location;
		show(fm, tag);
	}
	
}
