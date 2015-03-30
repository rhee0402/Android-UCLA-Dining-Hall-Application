package com.example.testdininghall;


import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteActivity extends Activity implements OnClickListener{

	Button diningHall_btn, meal_btn, allMenu_btn, favorite_btn, home_btn;
	Button search_btn;
	EditText search_text;
	MyCustomAdapter dataAdapter = null;
	static MenuDatabase menuDatabase = new MenuDatabase(); 
	ArrayList<MenuItem> menu;
	MenuDatabaseSQLite info= new MenuDatabaseSQLite(this);
	ArrayList<MenuItem> menuItemList;  //list to display on the screen
	//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);  	
        displayListView();
        
        home_btn=(Button)findViewById(R.id.home_button); 
        diningHall_btn=(Button)findViewById(R.id.diningHall_button);
        meal_btn=(Button)findViewById(R.id.meal_button); 
        favorite_btn=(Button)findViewById(R.id.favorite_button);
        search_btn =(Button)findViewById(R.id.search_button);
        search_text =(EditText)findViewById(R.id.search_editText);
  
        home_btn.setOnClickListener(this);
        diningHall_btn.setOnClickListener(this);
        meal_btn.setOnClickListener(this);
        favorite_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
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
			Intent ToFavorite = new Intent (this, FavoriteActivity.class);
			startActivity(ToFavorite);
		}
		
		else if (clickedBtnId == R.id.home_button)
		{
			Intent ToHome = new Intent (this, HomeActivity.class);
			startActivity(ToHome);
		}
		
		else if (clickedBtnId == R.id.search_button)
		{
			displaySearchResult(search_text.getText().toString());
		}
		
		
	}
	//
	//basically same as displayListView except got one more restriction
	private void displaySearchResult(String searchStr) {	
		menuItemList = new ArrayList<MenuItem>();  //list to display on the screen
        info.open();
        //get data
        menu = info.getData();
        info.close();
        
		for (MenuItem m: menu){
			// if it is favroite (i.e favorite==true) 
			// and it is offered by any one of meal time
			// then add it to the list that is displayed on the screen
//			if (m.isFavorite() && !(m.getBreakfastDiningHall()==null && m.getLunchDiningHall()==null && m.getLunchDiningHall()==null)) //this will only display offered favorite menu
			if (m.isFavorite()) //display all favorite menu
				if(m.getName().contains(searchStr))
					menuItemList.add(m);					 
		} //end for for
		
		//Create Array Adapter
		dataAdapter = new MyCustomAdapter(this,R.layout.listview_row, menuItemList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		//Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		
	}//end of displaySearchListView

	
	
	

	private void displayListView() {
		
		menuItemList = new ArrayList<MenuItem>();  //list to display on the screen
		
        info.open();
        //get data
        menu = info.getData();   
        info.close();
        
		for (MenuItem m: menu){
			// if it is favroite (i.e favorite==true) 
			// and it is offered by any one of meal time
			// then add it to the list that is displayed on the screen
//			if (m.isFavorite() && !(m.getBreakfastDiningHall()==null && m.getLunchDiningHall()==null && m.getLunchDiningHall()==null)) //this will only display offered favorite menu
			if (m.isFavorite()) //display all favorite menu
				menuItemList.add(m);					 
		} //end for for
		
		//Create Array Adapter
		dataAdapter = new MyCustomAdapter(this,R.layout.listview_row, menuItemList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		//Assign adapter to ListView
		listView.setAdapter(dataAdapter);
	
	}

	
	//Adapter is used to display information in the "List Format" onto the ListView Screen.
	//Normal type like "String" is supported by default adapter but 
	//I need to make custom adapter that can support MenuItem type of object.
	class MyCustomAdapter extends ArrayAdapter<MenuItem>
	{

		private ArrayList<MenuItem> menuList;

		public MyCustomAdapter(Context context, int resourceID,
				ArrayList<MenuItem> newMenuList) 
		{
			super(context, resourceID, newMenuList);
			this.menuList = new ArrayList<MenuItem>();
			this.menuList.addAll(newMenuList);
		}
	
		//This function is why we write custom adapter in the first place.
		//We can design how to display on the screen using this function.
		//For now, it only deal with the checkbox and checkbox text.
		public View getView(int position, View convertView, ViewGroup parent) 
		{
	
			View row=convertView;
			MenuItem item = menuList.get(position);
			//inflater are expensive, 20~30ms, checking for row==null, we can save some time
			if (row==null){
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.listview_row, null);
			}
		
			CheckBox checkbox = (CheckBox) row.findViewById(R.id.listview_row_checkbox);																 
			checkbox.setChecked(item.isFavorite());
            checkbox.setTag(item.getName());
            checkbox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox ckbox = (CheckBox) v;
					String menuName = (String)ckbox.getTag();
					info.open();
					info.changeFavorite(menuName); //change favorite state in the database for that menu (info is menuDatabaseSQL)
					info.close();
				}
			});
			
			TextView name_button = (TextView) row.findViewById(R.id.listview_row_button);
            name_button.setText(item.getName());
            name_button.setTextColor(Color.parseColor("#ffc3c3c3"));
           // if(item.getBreakfastDiningHall().contains("Empty") && item.getLunchDiningHall().contains("Empty") && item.getDinnerDiningHall().contains("Empty"))
           // 		name_button.setTextColor(Color.parseColor("#ffc3c3c3"));
            if(item.getBreakfastDiningHall().contains("Covel") || item.getLunchDiningHall().contains("Covel") || item.getDinnerDiningHall().contains("Covel") ||
            		item.getBreakfastDiningHall().contains("De Neve") || item.getLunchDiningHall().contains("De Neve") || item.getDinnerDiningHall().contains("De Neve") ||
            		item.getBreakfastDiningHall().contains("Feast") || item.getLunchDiningHall().contains("Feast") || item.getDinnerDiningHall().contains("Feast") ||
            		item.getBreakfastDiningHall().contains("Hedrick") || item.getLunchDiningHall().contains("Hedrick") || item.getDinnerDiningHall().contains("Hedrick") ||
            		item.getBreakfastDiningHall().contains("Bruin Plate") || item.getLunchDiningHall().contains("Bruin Plate") || item.getDinnerDiningHall().contains("Bruin Plate"))
            		name_button.setTextColor(Color.BLACK); //if it is offered, color black
            
   //         if(position==0) //******there is bug that first item in the favorite list is always get true in the above case so need to color again in black
   //         	name_button.setTextColor(Color.BLACK);
            name_button.setTag(item.getName()); //let tag of button be name of menu so we can take appropriate response
                                                                                    //when button is clicked by looking at the tag
			name_button.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					Button btn = (Button) v;
					String menuName = (String) btn.getTag();
					Bundle basket = new Bundle();
					basket.putString("name", menuName);
					Intent ToMenuInfo = new Intent (FavoriteActivity.this, MenuInfoActivity.class);
					ToMenuInfo.putExtras(basket);
					startActivity(ToMenuInfo);
				}			
			});
			return row;			
		}

}
    
}
