package com.example.testdininghall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// so basically DiningHall class return list of menus where each menu have all information already filled.
// After first time running this application, database will keep information about name/favorite and need to update only the dining hall information

public class MenuDatabaseSQLite {
	// set name of columns
	public static final String KEY_ROWID ="_id";
	public static final String KEY_NAME ="menu_name";
	public static final String KEY_FAVORITE = "menu_favorite";
	public static final String KEY_BREAKFAST_HALL = "menu_breakfast_hall";
	public static final String KEY_LUNCH_HALL = "menu_lunch_hall";
	public static final String KEY_DINNER_HALL = "menu_dinner_hall";
	public static final String KEY_NUTINFO = "menu_nutinfo";
	public static final String KEY_ADDED_DATE = "menu_addeddate";
	
	private static final String DATABASE_NAME ="MenuDatabase";
	private static final String DATABASE_TABLE ="MenuTable";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	private File mDatabaseFile;
	private Context db_context;
	

	public class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mDatabaseFile = context.getDatabasePath(DATABASE_NAME);
			db_context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Setting up the columns for our database (more will be added)
			// SQL Code: INTEGER PRIMARY KEY AUTOINCREMENT -> increment integer by one
			//			TEXT NOT NULL -> SQL version of String
			db.execSQL("CREATE TABLE  " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_NAME + " TEXT NOT NULL, " +
					KEY_FAVORITE + " TEXT NOT NULL, " +
					KEY_BREAKFAST_HALL + " TEXT NOT NULL, " +
					KEY_LUNCH_HALL + " TEXT NOT NULL, " +
					KEY_DINNER_HALL + " TEXT NOT NULL, " +
					KEY_NUTINFO + " TEXT NOT NULL, " +
					KEY_ADDED_DATE + " TEXT NOT NULL);"
					
			); //above are SQL command passed in as string
			
			
			
			
		}//

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	//constructor for our database
	public MenuDatabaseSQLite (Context c){
		ourContext = c;
	}
	
	public MenuDatabaseSQLite open(){
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close(); //close database
	}
	
	public void deleteDatabase(){
		db_context.deleteDatabase(DATABASE_NAME);
	}
	
	//create new database with given offered menu list with given list of favorite menu name
	public void createNewDatabase(ArrayList<MenuItem> menuList, ArrayList<String> favList){
		ArrayList<String> notOfferedFavMenu = new ArrayList<String>(); 
		notOfferedFavMenu.addAll(favList);
		deleteAllEntry();
        for(MenuItem m: menuList){
        	createEntry(m);
        	if(favList.contains(m.getName())){//if offered menu is in favorite menu
        		changeFavorite(m.getName()); //change to favorite=true (because default is set to favorite=false)
        		notOfferedFavMenu.remove(m.getName()); //remove from list of not offered favorite menu
        	}
        }
    
         for(String nameOfMenu: notOfferedFavMenu){ //if favorite menu is not offered
        	 ArrayList<String> breakfast_empty = new ArrayList<String>();
        	breakfast_empty.add("Empty");
        	ArrayList<String> lunch_empty = new ArrayList<String>();
        	lunch_empty.add("Empty");
        	ArrayList<String> dinner_empty = new ArrayList<String>();
        	dinner_empty.add("Empty");
        	ArrayList<String> nutInfo_empty = new ArrayList<String>();
        	nutInfo_empty.add("Empty");
        	MenuItem menu1 = new MenuItem(nameOfMenu, true, breakfast_empty,lunch_empty,dinner_empty, nutInfo_empty); //add it to database but null all dining halls 
        																	   //***caution***, nutInfo is null as well, might need to change
        	createEntry(menu1);

        		
        }//end of second for loop */
	}
	
	//insert menu given in menuItem format into database in table of strings format.
	public long createEntry(MenuItem inputMenu){
		ContentValues cv = new ContentValues();
		Date cDate;
		//input for menu name
		cv.put(KEY_NAME, inputMenu.getName());
		
		//input for favorite
		if(inputMenu.isFavorite())
			cv.put(KEY_FAVORITE, "TRUE");
		else
			cv.put(KEY_FAVORITE, "FALSE");
		
		//input for dinner dining hall
		String inputDiningHall="";
		ArrayList<String> inputDiningHallList = inputMenu.getBreakfastDiningHall();
		for (String halls: inputDiningHallList){
			if(halls.equals("Covel"))
				inputDiningHall += "Covel,";
			if(halls.equals("De Neve"))
				inputDiningHall += "De Neve,";
			if(halls.equals("Bruin Plate"))
				inputDiningHall += "Bruin Plate,";
			if(halls.equals("Feast"))
				inputDiningHall += "Feast,";
			if(halls.equals("Hedrick"))
				inputDiningHall += "Hedrick";
			if(halls.equals("Empty"))
				inputDiningHall += "Empty";
		}//end of for loop
		//inputDiningHall will be or substring of: "Covel,De Neve,Bruin Plate,Feast,Hedrick"
		cv.put(KEY_BREAKFAST_HALL, inputDiningHall); 

		//input for dinner dining hall
		inputDiningHall="";
		inputDiningHallList = inputMenu.getLunchDiningHall();
		for (String halls: inputDiningHallList){
			if(halls.equals("Covel"))
				inputDiningHall += "Covel,";
			if(halls.equals("De Neve"))
				inputDiningHall += "De Neve,";
			if(halls.equals("Bruin Plate"))
				inputDiningHall += "Bruin Plate,";
			if(halls.equals("Feast"))
				inputDiningHall += "Feast,";
			if(halls.equals("Hedrick"))
				inputDiningHall += "Hedrick";
			if(halls.equals("Empty"))
				inputDiningHall += "Empty";
		}//end of for loop
		//inputDiningHall will be or substring of: "Covel,De Neve,Bruin Plate,Feast,Hedrick"
		cv.put(KEY_LUNCH_HALL, inputDiningHall); 
		
		//input for dinner dining hall
		inputDiningHall="";
	    inputDiningHallList = inputMenu.getDinnerDiningHall();
		for (String halls: inputDiningHallList){
			if(halls.equals("Covel"))
				inputDiningHall += "Covel,";
			if(halls.equals("De Neve"))
				inputDiningHall += "De Neve,";
			if(halls.equals("Bruin Plate"))
				inputDiningHall += "Bruin Plate,";
			if(halls.equals("Feast"))
				inputDiningHall += "Feast,";
			if(halls.equals("Hedrick"))
				inputDiningHall += "" + "Hedrick";
			if(halls.equals("Empty"))
				inputDiningHall += "Empty";
		}//end of for loop
		//inputDiningHall will be or substring of: "Covel,De Neve,Bruin Plate,Feast,Hedrick"
		cv.put(KEY_DINNER_HALL, inputDiningHall); 
		
		//input for Nutrition Info (might not be needed if we use screenshot of nutrition info)
		String inputNutInfo="";
		ArrayList<String> inputNutInfoArray = inputMenu.getNutInfo();
		for(String info : inputNutInfoArray)
			inputNutInfo += info;
		cv.put(KEY_NUTINFO, inputNutInfo);	
		
		//Date Menu Information was added
		cDate = new Date();
		cv.put(KEY_ADDED_DATE, new SimpleDateFormat("yyyy-MM-dd").format(cDate));
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}//end of createEntry

	
	//return list of all menus in database in ArrayList<MenuItem> type.
	public ArrayList<MenuItem> getData() {
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
	//	MenuItem menu = new MenuItem();
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iFavorite = c.getColumnIndex(KEY_FAVORITE);
		int iBreakfastHall = c.getColumnIndex(KEY_BREAKFAST_HALL);
		int iLunchHall = c.getColumnIndex(KEY_LUNCH_HALL);
		int iDinnerHall = c.getColumnIndex(KEY_DINNER_HALL);
		int iNutInfo = c.getColumnIndex(KEY_NUTINFO);
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {//stop when cursor is after last row
			MenuItem menu = new MenuItem();
			menu.setName(c.getString(iName));
			menu.setFavorite(getFavorite(c.getString(iFavorite)));
			menu.setBreakfastDiningHall(getDiningHallList(c.getString(iBreakfastHall)));
			menu.setLunchDiningHall(getDiningHallList(c.getString(iLunchHall)));
			menu.setDinnerDiningHall(getDiningHallList(c.getString(iDinnerHall)));
			
			//set nutrition information (but might not be required)
			ArrayList<String> nutInfoList = new ArrayList<String>();
			nutInfoList.add(c.getString(iNutInfo));
			menu.setNutInfo(nutInfoList);
			menuList.add(menu);
		}//end of for loop
		return menuList;
	}// end of getData
	
	private boolean getFavorite(String favoriteStr){
		if(favoriteStr.equals("TRUE"))
				return true;
		else
			return false;
	}//end of getFavorite
	
	//from the database's string format of dining halls, change to ArrayList<String> format of dining halls
	private ArrayList<String> getDiningHallList(String hallStr) {
		ArrayList<String> resultList = new ArrayList<String>();
		if(isStringContain(hallStr, "Covel"))
			resultList.add("Covel");
		if(isStringContain(hallStr, "De Neve"))
			resultList.add("De Neve");
		if(isStringContain(hallStr, "Bruin Plate"))
			resultList.add("Bruin Plate");
		if(isStringContain(hallStr, "Feast"))
			resultList.add("Feast");
		if(isStringContain(hallStr, "Hedrick"))
			resultList.add("Hedrick");
		if(isStringContain(hallStr, "Empty"))
			resultList.add("Empty");
		return resultList;
	}// end of getDiningHall//
	
	//get the date of the menu that was added last to the database (not used)
	public String getLastUpdatedItemsDate(){
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		if(c.moveToLast()){
			int iAddedDate = c.getColumnIndex(KEY_ADDED_DATE);
			return c.getString(iAddedDate);
		}
		else
			return "EMPTY";
	}
		
	//get the date of the menu that was added first to the database
	public String getFirstUpdatedItemsDate(){
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		if(c.moveToFirst()){
			int iAddedDate = c.getColumnIndex(KEY_ADDED_DATE);
			return c.getString(iAddedDate);
		}
		else
			return "EMPTY";
	}
	
	// CHANGE FAVORITE STATE OF MENU WITH TARGET_NAME INSIDE THE DATABASE
	public void changeFavorite(String target_name){
		ContentValues cv = new ContentValues();
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iFavorite = c.getColumnIndex(KEY_FAVORITE);
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {//stop when cursor is after last row
			if(target_name.equals(c.getString(iName))){
					if(c.getString(iFavorite).equals("TRUE"))
						cv.put(KEY_FAVORITE, "FALSE");
					else // if(c.getString(iFavorite).equals("FALSE"))
						cv.put(KEY_FAVORITE, "TRUE");	
					int row = Integer.parseInt(c.getString(iRow));
					ourDatabase.update(DATABASE_TABLE, cv , KEY_ROWID + "=" + row, null);
					return;
			}//end of if
		}//end of for loop
		
	}
	
	public void deleteAllEntry(){
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int iRow = c.getColumnIndex(KEY_ROWID);
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {//stop when cursor is after last row
				int row = Integer.parseInt(c.getString(iRow));
				deleteEntry(row);
		}//end of for loop
	}
	
	public void deleteEntry(int row){
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "-" + row, null);
	}
	
	public ArrayList<String> searchMenuByName(String findStr){
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_FAVORITE, KEY_BREAKFAST_HALL, KEY_LUNCH_HALL, KEY_DINNER_HALL, KEY_NUTINFO, KEY_ADDED_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> searchList = new ArrayList<String>();
		int iName = c.getColumnIndex(KEY_NAME);
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {//stop when cursor is after last row
			String menuName = c.getString(iName);
			if(isStringContain(menuName, findStr))
			{
				searchList.add(menuName);
			}
		}//end of for loop
		return searchList;
	}
	
	
	
	//Look in searchStr and try to find string findStr which is substring.
	//e.g. return true if searchStr = "Green Eggs and Ham" and findStr="Eggs"
	public boolean isStringContain(String searchString, String findString){
		return searchString.contains(findString);
	}//end of isStringContain

	
	
}//end of MenuDatabaseSQL
