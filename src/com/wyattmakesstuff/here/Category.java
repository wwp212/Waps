package com.wyattmakesstuff.here;

public class Category {
	private CharSequence category;
	private float color;
	
	public Category(CharSequence category, float color){
		this.category = category;
		this.color = color;
	}

	public CharSequence getCategory() {
		return category;
	}

	public void setCategory(CharSequence category) {
		this.category = category;
	}

	public float getColor() {
		return color;
	}

	public void setColor(float color) {
		this.color = color;
	}
	
}
