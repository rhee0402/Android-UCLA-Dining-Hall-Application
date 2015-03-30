package com.example.testdininghall;


import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {

	Button diningHall_btn, meal_btn,favorite_btn, home_btn, covel_btn, reiber_btn, hedrick_btn, deneve_btn, bplate_btn;
	TextView home_tv;
	ArrayList<MenuItem> menu;
	MenuDatabaseSQLite info= new MenuDatabaseSQLite(this);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
       
        home_tv = (TextView)findViewById(R.id.home_textView);
       
        home_btn=(Button)findViewById(R.id.home_button);
        home_btn.setOnClickListener(this);
        
        diningHall_btn=(Button)findViewById(R.id.diningHall_button);
        diningHall_btn.setOnClickListener(this);
        
        meal_btn=(Button)findViewById(R.id.meal_button);
        meal_btn.setOnClickListener(this);
        
        favorite_btn=(Button)findViewById(R.id.favorite_button);
        favorite_btn.setOnClickListener(this);
        
        info.open();
        //get data
        menu = info.getData();
        info.close();
        int limit=0;
        String news="Today's favorite menu:\n";
        for(MenuItem item: menu){
    		if(limit>=4){ //only display 4 favorite menu
    			news += "...and for more, go to favorites";
    			break;
    		}
        	if(item.isFavorite() && item.getBreakfastDiningHall().contains("Covel") || item.getLunchDiningHall().contains("Covel") || item.getDinnerDiningHall().contains("Covel") ||
            		item.getBreakfastDiningHall().contains("De Neve") || item.getLunchDiningHall().contains("De Neve") || item.getDinnerDiningHall().contains("De Neve") ||
            		item.getBreakfastDiningHall().contains("Feast") || item.getLunchDiningHall().contains("Feast") || item.getDinnerDiningHall().contains("Feast") ||
            		item.getBreakfastDiningHall().contains("Hedrick") || item.getLunchDiningHall().contains("Hedrick") || item.getDinnerDiningHall().contains("Hedrick") ||
            		item.getBreakfastDiningHall().contains("Bruin Plate") || item.getLunchDiningHall().contains("Bruin Plate") || item.getDinnerDiningHall().contains("Bruin Plate"))
        		news += " " + item.getName()  + "\n";
        		limit++;

        }
        home_tv.setText(news);
        
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
			Intent ToFavorite = new Intent (this, FavoriteActivity.class);
			startActivity(ToFavorite);
		}
		
		else if (clickedBtnId == R.id.home_button)
		{
			Intent ToHome = new Intent (this, HomeActivity.class);
			startActivity(ToHome);
		}
		

		
	} 
}
