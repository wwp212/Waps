package com.wyattmakesstuff.here;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

public class Wap {
	private LatLng location;
	private String title;
	private CharSequence category;

	public Wap(LatLng location, String title, CharSequence category){
		this.location = location;
		this.title = title;
		this.category = category;
	}

	public Wap(Wap wap){
		location = wap.getLocation();
		title = wap.getTitle();
		category = wap.getCategory();
	}
	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CharSequence getCategory() {
		return category;
	}

	public void setCategory(CharSequence category) {
		this.category = category;
	}
	
	
}
