package com.example.testdininghall;

import java.util.ArrayList;


public class MenuItem {
	String name = null; 						//name of menu
	boolean favorite = false;  					//if favorite menu, set true
	ArrayList<String> breakfast_diningHall= null; //list of dining hall offering this menu during breakfast
	ArrayList<String> lunch_diningHall=null;	//list of dining hall offering this menu during lunch
	ArrayList<String> dinner_diningHall=null;	//list of dining hall offering this menu during dinner
	ArrayList<String> nutInfo=null;	 			//nutrition information 
	
	//constructor for the MenuItem object
	public MenuItem(String name, boolean favorite, ArrayList<String> breakfast, 
			ArrayList<String> lunch, ArrayList<String> dinner, ArrayList<String> nutInfo ){
		this.name = name;
		this.favorite = favorite;
		this.breakfast_diningHall = breakfast;
		this.lunch_diningHall = lunch;
		this.dinner_diningHall = dinner;
		this.nutInfo = nutInfo;
	}
	
	public MenuItem(){ //default constructor for empty argument
		this.name = null; 					//name of menu
		this.favorite = false;  			//if favorite menu, set true
		this.breakfast_diningHall= new ArrayList<String>(); 	//list of dining hall offering this menu during breakfast
		this.lunch_diningHall= new ArrayList<String>();	//list of dining hall offering this menu during lunch
		this.dinner_diningHall= new ArrayList<String>();		//list of dining hall offering this menu during dinner
		this.nutInfo= new ArrayList<String>();	 				//nutrition information 
	}
	
	//Helper Methods
	//for name of menu
	public String getName(){
		return name;}
	
	public void setName(String newName){
		this.name=newName;}
	
	//for favorite
	public boolean isFavorite(){
		return favorite;
	}
	
	public void setFavorite(boolean newFavorite){
		this.favorite=newFavorite;
	}
	
	//For Breakfast Dining Hall List
	public ArrayList<String> getBreakfastDiningHall(){
		return this.breakfast_diningHall;
	}
	
	public void setBreakfastDiningHall(ArrayList<String> newBfList){
		breakfast_diningHall.clear();
		this.breakfast_diningHall.addAll(newBfList);
	}
	
	public void addBreakfastDiningHall(String newDiningHall){
		this.breakfast_diningHall.add(newDiningHall);
	}

	//for Lunch Dining Hall list
	public ArrayList<String> getLunchDiningHall(){
		return this.lunch_diningHall;
	}
	public void setLunchDiningHall(ArrayList<String> newLunchList){
		lunch_diningHall.clear();
		this.lunch_diningHall.addAll(newLunchList);
	}	
	public void addLunchDiningHall(String newDiningHall){
		this.lunch_diningHall.add(newDiningHall);
	}

	//for Dinner Dining Hall list
	public ArrayList<String> getDinnerDiningHall(){
		return this.dinner_diningHall;
	}
	public void setDinnerDiningHall(ArrayList<String> newDinnerList){
		dinner_diningHall.clear();
		this.dinner_diningHall.addAll(newDinnerList);
	}
	public void addDinnerDiningHall(String newDiningHall){
		this.dinner_diningHall.add(newDiningHall);
	}
	
	//For Nutrition Information
	public ArrayList<String> getNutInfo(){
		return this.nutInfo;
	}
	public void setNutInfo(ArrayList<String> newNutInfoList){
		nutInfo.clear();
		this.nutInfo.addAll(newNutInfoList);
	}
	public void addNutInfo(String newNutInfo){
		this.nutInfo.add(newNutInfo);
	}
	
}
