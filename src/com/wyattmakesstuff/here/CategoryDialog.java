package com.wyattmakesstuff.here;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CategoryDialog extends DialogFragment{
	private Spinner colorSpinner;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.category_dialog, null))
               .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   EditText inputText = (EditText) getDialog().findViewById(R.id.editText1);
                	   WapsSingleton waps = WapsSingleton.getInstance(getActivity());
                	   float color = Here.translateColor(colorSpinner.getSelectedItem().toString());
                	   waps.addCategory(inputText.getText().toString(), color);
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	@Override
	public void onStart() {
		super.onStart();
	    setColorSpinner();
	}
	
	public void setColorSpinner(){
		colorSpinner = (Spinner) getDialog().findViewById(R.id.spinner1);
	    List<CharSequence> colors = getColorList();
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
	        android.R.layout.simple_spinner_item, colors);
		// Specify the layout to use when the list of choices appears
		adapter.notifyDataSetChanged();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		colorSpinner.setAdapter(adapter);
	}
	
	public List<CharSequence> getColorList(){
		List<CharSequence> colors = new ArrayList<CharSequence>();
		colors.add("Blue");
		colors.add("Green");
		colors.add("Orange");
		colors.add("Violet");
		colors.add("Yellow");
		return colors;
	}
}
