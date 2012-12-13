package com.wyattmakesstuff.here;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class WapsSingleton {
	static WapsSingleton instance = null;
	
	private List<Wap> wapList;
	private List<CharSequence> categoriesList;
	private HashMap<CharSequence, Boolean> shownCats;
	private static HashMap<CharSequence, Float> catColors;
	DBHelper db;
	
	private WapsSingleton(Context ctx){
		
		wapList = new ArrayList<Wap>();
		categoriesList = new ArrayList<CharSequence>();
		shownCats = new HashMap<CharSequence, Boolean>();
		catColors = new HashMap<CharSequence, Float>();
		
		db = new DBHelper(ctx);
		wapList = db.getAllWaps();
		
		List<Category> tempCatList = db.getAllCategories();
		Log.d("WYATT", "2");
		Log.d("WYATT", "2 size: " + tempCatList.size());
		for (int i = 0; i < tempCatList.size(); i++){
			Log.d("WYATT", "3");
			categoriesList.add(tempCatList.get(i).getCategory());
			Log.d("WYATT", "4");
			catColors.put(tempCatList.get(i).getCategory(), tempCatList.get(i).getColor());
			Log.d("WYATT", "5");
		}
		
		//set all categories to shown
		for (int i = 0; i < categoriesList.size(); i++){
			Log.d("WYATT", "Probably gets here");
			if (shownCats.get(categoriesList.get(i)) == null){
				shownCats.put(categoriesList.get(i), true);
			}
			Log.d("WYATT", "But not here");
		}
	}
	
	public static WapsSingleton getInstance(Context ctx){
		if (instance == null){
			instance = new WapsSingleton(ctx);
		}
		return instance;
	}
	
	public List<Wap> getAllWaps(){
		return wapList;
	}
	
	public List<CharSequence> getAllCategories(){
		return categoriesList;
	}
	
	public void addCategory(String category, float color){
		Log.d("WYATT", "ADDING THIS YO : " + category + " WITH " + color);
		db.addCategory(category, color);
		categoriesList.add(category);
		catColors.put(category, color);
	}
	
	public void hideCategory(CharSequence category){
		shownCats.put(category, false);
	}
	
	public void showCategory(CharSequence category){
		shownCats.put(category, true);
	}
	
	public static float getColorForCat(CharSequence category){
		return catColors.get(category);
	}
	
	public List<Wap> getAllShown(){
		List<Wap> shown = new ArrayList<Wap>();
		for (int i = 0; i < wapList.size(); i++){
			if (shownCats.get(wapList.get(i).getCategory())){
				shown.add(wapList.get(i));
			}
		}
		return shown;
	}
}
