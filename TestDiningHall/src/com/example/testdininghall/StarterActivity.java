package com.example.testdininghall;

import android.content.Intent;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StarterActivity extends Activity {
    MenuDatabase menuDatabase = new MenuDatabase();
    MenuDatabaseSQLite databaseSQL = new MenuDatabaseSQLite(this);
    getMenu task1 = new getMenu();
    boolean database_isOutDated;
	boolean database_isEmpty;
	
    //
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        database_isOutDated= isDatabaseEmpty();
    	database_isEmpty = isDatabaseOutDated();
		// task1 will get menu data from the website and 
		// put it inside our SQLdatabase if SQLdatabase is out-date.
        task1.execute(database_isOutDated, database_isEmpty);
        //if database is up-to-date, just go to home activity.

	}
	
	// isDatabaseOutDated():
	// compare today's date and date database was last updated to see if database is up to date 
    public boolean isDatabaseOutDated(){
    	databaseSQL.open();
    	Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);  
        String firstdate_database = databaseSQL.getFirstUpdatedItemsDate();
        databaseSQL.close();
        	if(fDate.equals(firstdate_database)){ 	
        		return false;			  	
    		}//end of if
        	else{
        		return true; 	//do nothing if database is up-to date
        	}

    }

	// isDatabaseEmpty():
	// check if database is empty
	// if firstdate_database field is set to "EMPTY" return false, else return true
    public boolean isDatabaseEmpty(){
    	databaseSQL.open();
    	String firstdate_database = databaseSQL.getFirstUpdatedItemsDate();
    	databaseSQL.close();
    	if(!firstdate_database.equals("EMPTY"))
    		return false;
    	else 
    		return true;
    }

	// createDatabase():
	// create new database if current database is empty or outdated
	public void createDatabase(){
		databaseSQL.open(); 
		if(database_isOutDated || database_isEmpty){ 	
		// if date does not match with current date or database is empty,
		// then create new entries for the database. (remove currently existing database entries)
			ArrayList<MenuItem> menuList = menuDatabase.getDatabase();
			ArrayList<String> favList= new ArrayList<String>(); 
			favList = getFavoriteList(databaseSQL.getData());
			databaseSQL.createNewDatabase(menuList, favList);
		}
        databaseSQL.close();
	}

	/* getFavoriteMenu(ArrayList<MenuItem> menuList):
	   menuList: list of menu that we will be looking for user's favorite menu
	   return: array of string that only contain user's favorite menu name
	*/	
	private ArrayList<String> getFavoriteList(ArrayList<MenuItem> menuList) {
		ArrayList<String> favList = new ArrayList<String>();
		for (MenuItem menu: menuList)
			if(menu.isFavorite())
				favList.add(menu.getName());
		return favList;
	}
	
	/* getFavoriteMenuList(ArrayList<MenuItem> menuList):
	   menuList: list of menu that we will be looking for user's favorite menu
	   return: list of menuItem that only contain user's favorite menu
	*/
	private ArrayList<MenuItem> getFavoriteMenuList(ArrayList<MenuItem> menuList) {
		ArrayList<MenuItem> favList = new ArrayList<MenuItem>();
		for (MenuItem menu: menuList)
			if(menu.isFavorite())
				favList.add(menu);
		return favList;
	}

	// go to home screen
	public void goToHomeActivity(){
		Intent ToHome = new Intent (this, HomeActivity.class);
		startActivity(ToHome);
	}
	
	// on background, check if database is empty or outdated
    private class getMenu extends AsyncTask<Boolean, Integer, String> {
        boolean create = false;
        @Override
        protected String doInBackground(Boolean... params) {
        	boolean isOutDated = params[0];
        	boolean isEmpty = params[1];
                try {
              		if(isEmpty || isOutDated) //if database is empty or outdated
                        	if(!create){ 
                        		menuDatabase.createMenu(); //create new database
                        		create = true;
                        	} // end of if
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return "done";
        }
        
        @Override
        protected void onPostExecute(String result){
            createDatabase();	
        	goToHomeActivity();
        }
    }//end of getMenu
    
}//end of StarterActivity
        

