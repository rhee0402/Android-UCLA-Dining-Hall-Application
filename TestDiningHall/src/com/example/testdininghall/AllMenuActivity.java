package com.example.testdininghall;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;  
import android.widget.LinearLayout;  
import android.widget.Button;  
import android.widget.TextView;  
import android.widget.EditText;  
import android.widget.CheckBox;  


public class AllMenuActivity extends Activity implements OnClickListener{

	Button diningHall_btn, meal_btn, allMenu_btn, favorite_btn, home_btn;
	MyCustomAdapter dataAdapter = null;
	MenuItem menuItem;
	MenuDatabase menuDatabase = new MenuDatabase(); //fake database that return hard coded data
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmenu);
        displayListView();
   
        
        home_btn=(Button)findViewById(R.id.home_button); 
        diningHall_btn=(Button)findViewById(R.id.diningHall_button);
        meal_btn=(Button)findViewById(R.id.meal_button); 
        allMenu_btn=(Button)findViewById(R.id.allMenu_button);
        favorite_btn=(Button)findViewById(R.id.favorite_button);
  
        home_btn.setOnClickListener(this);
        diningHall_btn.setOnClickListener(this);
        meal_btn.setOnClickListener(this);
        allMenu_btn.setOnClickListener(this);
        favorite_btn.setOnClickListener(this);
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
		
		else if (clickedBtnId == R.id.allMenu_button)
		{
			Intent ToAllMenu = new Intent (this, AllMenuActivity.class);
			startActivity(ToAllMenu);
		}
		
		else if (clickedBtnId == R.id.favorite_button)
		{
			Intent ToFavorite = new Intent (this, FavoriteActivity.class);
			startActivity(ToFavorite);
		}
		
	}
	
	private void displayListView() {
		
	
		ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();  //list to display on the screen
	
		
		//Initial Information for the item object, we will need to read in from database in the future.
		boolean favorite = false;
		ArrayList<String> breakfast = null;
		ArrayList<String> lunch = null;
		ArrayList<String> dinner = null;
		ArrayList<String> nutInfo = null;
		int i;
		
		for(i=0; i<menuDatabase.getDatabaseSize();i++){
			menuItem = menuDatabase.getMenuItem(i);
			menuItemList.add(menuItem); //display all menu in the database to the screen		 
		}
		
		for(i=0; i<14; i++){
			String name = "Menu "+i;
			if(i==0)
				favorite=true;
			else if(i==3)
				favorite=false;
			menuItem = new MenuItem(name, favorite, breakfast, lunch, dinner, nutInfo);
			menuItemList.add(menuItem);
		}
		
		//Create Array Adapter
		dataAdapter = new MyCustomAdapter(this,R.layout.listview_row, menuItemList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		//Assign adapter to ListView
		listView.setAdapter(dataAdapter);
	
		//I can add listener down here to make ListView respond to the click of each "row"
		//But checkbox or button click listener must be handle in getView method.
		
	}
	
	//Adapter is used to display information in the "List Format" onto the ListView Screen.
	//Normal type like "String" is supported by default adapter but 
	//I need to make custom adapter that can support MenuItem type of object.
	class MyCustomAdapter extends ArrayAdapter<MenuItem>
	{

		private ArrayList<MenuItem> menuList;
		MenuItem item;

		public MyCustomAdapter(Context context, int resourceID,
				ArrayList<MenuItem> newMenuList) 
		{
			super(context, resourceID, newMenuList);
			this.menuList = new ArrayList<MenuItem>();
			this.menuList.addAll(newMenuList);
		}
	
		//This function is why we write custom adapter in the first place.
		//We can design how to display on the screen using this function.
		public View getView(int position, View convertView, ViewGroup parent) 
		{
	
			View row=convertView;
			item = menuList.get(position);
			//inflater are expensive, 20~30ms, checking for row==null, we can save some time
			if (row==null){
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.listview_row, null);
			}
			
			CheckBox checkbox = (CheckBox) row.findViewById(R.id.listview_row_checkbox);
			Button info_button = (Button) row.findViewById(R.id.listview_row_button);
			
			checkbox.setChecked(item.isFavorite());
			checkbox.setText(item.getName());
			info_button.setTag(item.getName()); //let tag of button be name of menu so we can take appropriate response
												//when button is clicked by looking at the tag
	

			info_button.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					Button btn = (Button) v;
					String menuName = (String) btn.getTag();
					Bundle basket = new Bundle();
					basket.putString("name", menuName);
					Intent ToMenuInfo = new Intent (AllMenuActivity.this, MenuInfoActivity.class);
					ToMenuInfo.putExtras(basket);
					startActivity(ToMenuInfo);
				}			
			});
			
		
			return row;			
		}

}


    
}
