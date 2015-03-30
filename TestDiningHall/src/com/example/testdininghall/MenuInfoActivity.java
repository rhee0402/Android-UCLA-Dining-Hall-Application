package com.example.testdininghall;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuInfoActivity extends Activity implements OnClickListener{

	Button diningHall_btn, meal_btn, allMenu_btn, favorite_btn, home_btn;
//	Button back_btn;
	static MenuDatabase menuDatabase = new MenuDatabase(); 
	ArrayList<MenuItem> menu;
	MenuDatabaseSQLite info= new MenuDatabaseSQLite(this);
	String menuName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuinfo);
        info.open();
        //get data
        menu = info.getData();   
        info.close();
      //
        
        ArrayList<String> nutInfoList;
        String menuInfo ="";
        CheckBox menuInfo_checkbox = (CheckBox) findViewById(R.id.info_checkbox);
        TextView menuInfo_name= (TextView) findViewById(R.id.info_name);
        TextView menuInfo_textView= (TextView) findViewById(R.id.info_textView);
        ImageView menuInfo_imageView = (ImageView) findViewById(R.id.nutinfo_imageView);
        
        home_btn=(Button)findViewById(R.id.home_button); 
        diningHall_btn=(Button)findViewById(R.id.diningHall_button);
        meal_btn=(Button)findViewById(R.id.meal_button); 
        favorite_btn=(Button)findViewById(R.id.favorite_button);
 //       back_btn=(Button)findViewById(R.id.back_button);
  
        home_btn.setOnClickListener(this);
        diningHall_btn.setOnClickListener(this);
        meal_btn.setOnClickListener(this);
        favorite_btn.setOnClickListener(this);
  //      back_btn.setOnClickListener(this);
       
        menuInfo_checkbox.setOnClickListener(this);
        

      
        //receive name of the menu that we want to display on the screen
        Bundle gotBasket = getIntent().getExtras();
        menuName = gotBasket.getString("name");
        
        int i;
	//	for(i=0; i<menuDatabase.getDatabaseSize();i++){
	//		menuItem = menuDatabase.getMenuItem(i);
        MenuItem menuItem = new MenuItem();
        for(MenuItem m: menu){
        	menuItem = m;
			//find menu information by looking up by name, if found, break out for loop
			if (menuName.equals(menuItem.getName()))			
				break;
		 }
		menuInfo_name.setText(menuName); 						//type name
		menuInfo_checkbox.setChecked(menuItem.isFavorite()); 	//check if it's favorite menu
		
		
		// Printing out which dining hall is offerring this menu and at what time period.
		ArrayList<String> bfList = menuItem.getBreakfastDiningHall();
		ArrayList<String> lunchList = menuItem.getLunchDiningHall();
		ArrayList<String> dinnerList = menuItem.getDinnerDiningHall();
		
		menuInfo = menuInfo + "Breakfast: ";
		for(i=0; i<bfList.size();i++){
			menuInfo = menuInfo + bfList.get(i);
			if (i+1<bfList.size()) //if there is still another dining hall, put comma
				menuInfo= menuInfo + ", ";
		}
		menuInfo = menuInfo +"\n";
		menuInfo = menuInfo +"Lunch: ";
		for(i=0; i<lunchList.size();i++){
			menuInfo = menuInfo + lunchList.get(i);
			if (i+1<lunchList.size()) //if there is still another dining hall, put comma
				menuInfo= menuInfo + ", ";
		}
		menuInfo = menuInfo +"\n";
		menuInfo = menuInfo +"Dinner: ";
		for(i=0; i<dinnerList.size();i++){
			menuInfo = menuInfo + dinnerList.get(i);
			if (i+1<dinnerList.size()) //if there is still another dining hall, put comma
				menuInfo= menuInfo + ", ";
		}
		menuInfo = menuInfo +"\n"; // "\n\n" should be used if we are going to use string nutrition info not image file
		
		nutInfoList = menuItem.getNutInfo();
		String nutInfo = "";
		for(String new_nutInfo: nutInfoList){
			nutInfo = nutInfo + new_nutInfo;
			nutInfo = nutInfo + '\n';
		}
		menuInfo = menuInfo + nutInfo; 			//put Dining hall information and nut information together
		menuInfo_textView.setText(menuInfo); 	//print them out to screen
  
        String imageName = menuName.replaceAll(" ", "_").toLowerCase(); //replace white space into underscore and make it all lowercase
        imageName = imageName.replaceAll("&", "_"); //replace additional strange character into underscore
        imageName = imageName.replaceAll(",", "_");
        imageName = imageName.replaceAll("/", "_");
        imageName = imageName.replaceAll("-", "_");
       
        int imageId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageId > 0) //0 or less than 0 means imagefile with imageName does not exist
        	menuInfo_imageView.setImageResource(imageId);
        else{
        	menuInfo_imageView.setImageResource(R.drawable.not_available2); //if not available, display this image
        }																	//here I used image not_available2 because not_available image file was too small
        	
        
		
		
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dining_hall, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int clickedBtnId=arg0.getId();

		if (clickedBtnId == R.id.diningHall_button)
		{
			Intent ToDiningHall = new Intent (this, DiningHallActivity.class);
			startActivity(ToDiningHall);
		}
		
		else if (clickedBtnId == R.id.meal_button)
		{
			Intent ToMeal1 = new Intent (this, MealActivity.class);
			startActivity(ToMeal1);
		}

		
		else if (clickedBtnId == R.id.favorite_button)
		{
			Intent ToFavorit = new Intent (this, FavoriteActivity.class);
			startActivity(ToFavorit);
		}
		
		else if (clickedBtnId == R.id.home_button)
		{
			Intent ToHome = new Intent (this, HomeActivity.class);
			startActivity(ToHome);
		}
		/* cause of many bugs
		else if (clickedBtnId == R.id.back_button){ //return button
			MenuInfoActivity.this.finish();
		}*/
		
		else if (clickedBtnId == R.id.info_checkbox){
			info.open();
			info.changeFavorite(menuName); //change favorite state in the database for that menu (info is menuDatabaseSQL)
			info.close();
		}
		
	}
    
}
