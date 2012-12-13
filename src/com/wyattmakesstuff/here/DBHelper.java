package com.wyattmakesstuff.here;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "wapManager";
 
    //table names
    private static final String TABLE_WAPS = "waps";
    private static final String TABLE_CATS = "categories";
 
    //Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LON = "longitude";
    private static final String KEY_TIT = "title";
    private static final String KEY_CAT = "category";
    private static final String KEY_COL = "color";
 
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Adding new wap
    public void addWap(Wap wap) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, wap.getLocation().latitude);
        values.put(KEY_LON, wap.getLocation().longitude);
        values.put(KEY_TIT, wap.getTitle());
        values.put(KEY_CAT, wap.getCategory().toString());
     
        // Inserting Row
        db.insert(TABLE_WAPS, null, values);
        db.close(); // Closing database connection
    }
    
    public void addCategory(String category, float color) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_CAT, category);
        values.put(KEY_COL, color);
     
        // Inserting Row
        db.insert(TABLE_CATS, null, values);
        db.close(); // Closing database connection
    }
    
    // Getting All Waps
    public List<Wap> getAllWaps() {
       List<Wap> wapList = new ArrayList<Wap>();
       // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_WAPS;
    
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
    
       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
        	   LatLng latlng = new LatLng(Float.parseFloat(cursor.getString(1)), Float.parseFloat(cursor.getString(2)));
               Wap wap = new Wap(latlng, cursor.getString(3), cursor.getString(4));
               wapList.add(wap);
           } while (cursor.moveToNext());
       }
    
       // return contact list
       return wapList;
   }
    
    public List<Category> getAllCategories(){
    	List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Log.d("WYATT", "LOOK YO : " + cursor.getString(2));
            	Category cat = new Category(cursor.getString(1), Float.parseFloat(cursor.getString(2)));
         	   categoryList.add(cat);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return categoryList;
    }
    
    // Getting single wap
    public Wap getWap(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(TABLE_WAPS, new String[] { KEY_ID,
                KEY_LAT, KEY_LON, KEY_TIT, KEY_CAT, KEY_COL, }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        LatLng latlng = new LatLng(Float.parseFloat(cursor.getString(1)), Float.parseFloat(cursor.getString(2)));
        Wap wap = new Wap(latlng, cursor.getString(3), cursor.getString(4));
        // return contact
        return wap;
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WAPS_TABLE = "CREATE TABLE " + TABLE_WAPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LAT + " REAL,"
                + KEY_LON + " REAL," + KEY_TIT + " TEXT,"
                + KEY_CAT + " TEXT" + ")";
        db.execSQL(CREATE_WAPS_TABLE);
        
        String CREATE_CATS_TABLE = "CREATE TABLE " + TABLE_CATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CAT + " TEXT," + KEY_COL + " REAL" + ")";
        db.execSQL(CREATE_CATS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATS);
 
        // Create tables again
        onCreate(db);
    }
}
