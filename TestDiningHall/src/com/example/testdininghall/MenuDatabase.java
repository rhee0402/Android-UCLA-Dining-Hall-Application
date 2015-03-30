package com.example.testdininghall;

import java.io.IOException;
import java.util.ArrayList;

public class MenuDatabase {
	ArrayList<MenuItem> menuDatabase;
	int dataBaseSize; //number of menuItem in the database
	private static DiningHall hall;
	
	//default constructor
	public MenuDatabase(){
        hall = new DiningHall();
	}
	public void createMenu() throws IOException{
	hall.createMenu();
   	menuDatabase = (ArrayList<MenuItem>) hall.getMenu();
	}
	
	public ArrayList<MenuItem> getDatabase(){ //this return empty database for now
		return menuDatabase; //For future, I should be able to pass entire Database
	}
	
	public int getDatabaseSize(){ 
		return this.dataBaseSize;
	}
	
	//get MenuItem in given index position, for now, data is fixed.
	public MenuItem getMenuItem(int index){
		return menuDatabase.get(index);
	}

}
