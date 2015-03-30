package com.example.testdininghall;



import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;


public class DiningHallActivity extends Activity implements OnClickListener{
        Button diningHall_btn, meal_btn, allMenu_btn, favorite_btn, home_btn;
    	Button search_btn;
    	EditText search_text;
        MyCustomAdapter dataAdapter = null;
        static MenuDatabase menuDatabase = new MenuDatabase(); 
    	Spinner hall_spinner, meal_spinner;
    	String[] halls = {"Covel Commons", "De Neve", "Feast", "Bruin Plate", "Hedrick"};
    	String[] meals = {"All", "Breakfast", "Lunch", "Dinner"};
    	String current_hall = "Covel Commons";
    	String current_meal = "All";
		boolean firstTime_dhspin = true;
		boolean firstTime_mealspin = true;
		ArrayList<MenuItem> menu;
		MenuDatabaseSQLite info= new MenuDatabaseSQLite(this);
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// for dining hall selected from home page
    	String val = getIntent().getStringExtra("DiningHall");
    	if(val != null)
        	current_hall = val;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dininghall);
        displayListView(current_hall, current_meal);
        
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
        ArrayAdapter<String> hall_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, halls);
        ArrayAdapter<String> meal_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, meals);
        
		// Alternative layout for spinner:
        // R.layout.simple.spinner_item is another built-in layout for spinner, where appearing square box for each item is smaller
        // ArrayAdapter<String> meal_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, meals);
        
        hall_spinner = (Spinner)findViewById(R.id.dh_spinner);
        hall_spinner.setAdapter(hall_adapter);
        hall_spinner.setOnItemSelectedListener(new DiningHall_spinner());
        
        meal_spinner = (Spinner)findViewById(R.id.meal_spinner);
        meal_spinner.setAdapter(meal_adapter);
        meal_spinner.setOnItemSelectedListener(new Meal_spinner());
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
                //get button information of 5 dining hall buttons so I can change their background color
                if (clickedBtnId == R.id.diningHall_button)
                {
                        Intent ToDiningHall = new Intent (this, DiningHallActivity.class); // Intent is how we switch between screens (Activities)
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
        			displaySearchResult(current_hall, current_meal, search_text.getText().toString());
        		}
  
        }
       
  

			// Beginning of Listeners for Spinners =======================================================================================
     		//New class for the dining hall spinner
     		public class DiningHall_spinner implements OnItemSelectedListener {
     			public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
     					long id) {
     				if(!firstTime_dhspin){ //onItemSlected is fired when activity starts, so we want prevent this from affecting other methods.
	     				String selectedHall = parent.getItemAtPosition(pos).toString();
	     				current_hall = selectedHall;
	     				displayListView(selectedHall, current_meal);
     				}
    				else
     					firstTime_dhspin = false;
     			}
     			
     			public void onNothingSelected(AdapterView<?> arg0) {
     				// TODO Auto-generated method stub
     				//I don't need to do anything here	
     			}

     		}
     		
     		// New class for the meal spinner
     		public class Meal_spinner implements OnItemSelectedListener {
     			public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
     					long id) {
     				if(!firstTime_mealspin){ //onItemSlected is fired when activity starts, so we want prevent this from affecting other methods.
	     				String selectedMeal = parent.getItemAtPosition(pos).toString();
	     				current_meal = selectedMeal;
	     				displayListView(current_hall, selectedMeal);
     				}
     				else
     					firstTime_mealspin = false;
     			}
     		
     			public void onNothingSelected(AdapterView<?> arg0) {
     				// TODO Auto-generated method stub 
     				//I don't need to do anything here	
     			}
     			
     		}
     // End of Listeners for Spinners =======================================================================================

     		
       private void displaySearchResult(String diningHall, String mealTime, String searchStr) {
    	   ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();  //list to display on the screen
           
   		//get menu list from the database
           info.open();
           menu = info.getData();   
           info.close();
           
           if(diningHall.equals("Covel Commons")) //put Covel Common's menu into menuItemList
           {
                   for(MenuItem m : menu){
                	  if(m.getName().contains(searchStr)){
                           //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
		           		if (mealTime.equals("All")){
		   					if (m.getBreakfastDiningHall().contains("Covel") || 
		   							m.getLunchDiningHall().contains("Covel") || m.getDinnerDiningHall().contains("Covel") ) 
		   							menuItemList.add(m);		
		       				}
		       				else if (mealTime.equals("Breakfast")){
		       					if (m.getBreakfastDiningHall().contains("Covel")) 
		       						menuItemList.add(m);		
		       				}
		       				else if (mealTime.equals("Lunch")){
		       					if (m.getLunchDiningHall().contains("Covel") ) 
		       						menuItemList.add(m);		
		       				}
		       				else if (mealTime.equals("Dinner")){
		       					if (m.getDinnerDiningHall().contains("Covel") ) 
		       						menuItemList.add(m);		
		       				}
                	  }//end of if getName
		            }//end of for loop
                   	
           }
           
           else if(diningHall.equals("De Neve"))//put De neve's menu into menuItemList
           {
                   for(MenuItem m : menu){
                	if(m.getName().contains(searchStr)){
	                           //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
	               		if (mealTime.equals("All")){
	               			if (m.getBreakfastDiningHall().contains("De Neve") || 
	   							m.getLunchDiningHall().contains("De Neve") || m.getDinnerDiningHall().contains("De Neve") ) 
	   						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Breakfast")){
	       					if (m.getBreakfastDiningHall().contains("De Neve")) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Lunch")){
	       					if (m.getLunchDiningHall().contains("De Neve") ) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Dinner")){
	       					if (m.getDinnerDiningHall().contains("De Neve") ) 
	       						menuItemList.add(m);		
	       				}            
                	}//end of if getName
                  }//end of for loop
           }
           
           else if(diningHall.equals("Bruin Plate"))//put Bruin Plate's menu into menuItemList
           {
                   for(MenuItem m : menu){
                	if(m.getName().contains(searchStr)){
	                           //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
	                   	if (mealTime.equals("All")){
	       					if (m.getBreakfastDiningHall().contains("Bruin Plate") || 
	       							m.getLunchDiningHall().contains("Bruin Plate") || m.getDinnerDiningHall().contains("Bruin Plate") ) 
	       						menuItemList.add(m);		
	       				}
	   					else if (mealTime.equals("Breakfast")){
	       					if (m.getBreakfastDiningHall().contains("Bruin Plate")) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Lunch")){
	       					if (m.getLunchDiningHall().contains("Bruin Plate") ) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Dinner")){
	       					if (m.getDinnerDiningHall().contains("Bruin Plate") ) 
	       						menuItemList.add(m);		
	       				}                   
                	}//end of if getName
                   }
           }
           
           else if(diningHall.equals("Feast"))//put Reiber's menu into menuItemList
           {
               	for(MenuItem m : menu){
               		if(m.getName().contains(searchStr)){
	                           //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
	                       if (mealTime.equals("All")){
	       					if (m.getBreakfastDiningHall().contains("Feast") || 
	       							m.getLunchDiningHall().contains("Feast") || m.getDinnerDiningHall().contains("Feast") ) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Breakfast")){
	       					if (m.getBreakfastDiningHall().contains("Feast")) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Lunch")){
	       					if (m.getLunchDiningHall().contains("Feast") ) 
	       						menuItemList.add(m);		
	       				}
	       				else if (mealTime.equals("Dinner")){
	       					if (m.getDinnerDiningHall().contains("Feast") ) 
	       						menuItemList.add(m);		
	       				}         
               		}//end of if getName
               	}//end of for loop
           }
           
           else if(diningHall.equals("Hedrick"))//put Hedrick menu into menuItemList
           {
                   for(MenuItem m : menu){
                	if(m.getName().contains(searchStr)){
		               	if (mealTime.equals("All")){
		   					if (m.getBreakfastDiningHall().contains("Hedrick") || 
		   							m.getLunchDiningHall().contains("Hedrick") || m.getDinnerDiningHall().contains("Hedrick") ) 
		   						menuItemList.add(m);		
		   				}
		   				else if (mealTime.equals("Breakfast")){
		   					if (m.getBreakfastDiningHall().contains("Hedrick")) 
		   						menuItemList.add(m);		
		   				}
		   				else if (mealTime.equals("Lunch")){
		   					if (m.getLunchDiningHall().contains("Hedrick") ) 
		   						menuItemList.add(m);		
		   				}
		   				else if (mealTime.equals("Dinner")){
		   					if (m.getDinnerDiningHall().contains("Hedrick") ) 
		   						menuItemList.add(m);		
		   				}            
                     }//end of if getName lloop
                   }//end of for loop
           }
           
           
           //Create Array Adapter
           dataAdapter = new MyCustomAdapter(this,R.layout.listview_row, menuItemList);
           ListView listView = (ListView) findViewById(R.id.listView1);
           //Assign adapter to ListView
           listView.setAdapter(dataAdapter);
   //
   			
   		}//end of displaySearchResult	
     		
        private void displayListView(String diningHall, String mealTime) {
                ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();  //list to display on the screen
                
        		//get menu list from the database
                info.open();
                menu = info.getData();   
                info.close();
                
                if(diningHall.equals("Covel Commons")) //put Covel Common's menu into menuItemList
                {
                        for(MenuItem m : menu){
                                //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
                    		if (mealTime.equals("All")){
            					if (m.getBreakfastDiningHall().contains("Covel") || 
            							m.getLunchDiningHall().contains("Covel") || m.getDinnerDiningHall().contains("Covel") ) 
            						menuItemList.add(m);		
	            				}
	            				else if (mealTime.equals("Breakfast")){
	            					if (m.getBreakfastDiningHall().contains("Covel")) 
	            						menuItemList.add(m);		
	            				}
	            				else if (mealTime.equals("Lunch")){
	            					if (m.getLunchDiningHall().contains("Covel") ) 
	            						menuItemList.add(m);		
	            				}
	            				else if (mealTime.equals("Dinner")){
	            					if (m.getDinnerDiningHall().contains("Covel") ) 
	            						menuItemList.add(m);		
	            				}
                        	}//end of for loop
                        	
                }
                
                else if(diningHall.equals("De Neve"))//put De neve's menu into menuItemList
                {
                        for(MenuItem m : menu){
                                //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
                    		if (mealTime.equals("All")){
                    			if (m.getBreakfastDiningHall().contains("De Neve") || 
        							m.getLunchDiningHall().contains("De Neve") || m.getDinnerDiningHall().contains("De Neve") ) 
        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Breakfast")){
	        					if (m.getBreakfastDiningHall().contains("De Neve")) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Lunch")){
	        					if (m.getLunchDiningHall().contains("De Neve") ) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Dinner")){
	        					if (m.getDinnerDiningHall().contains("De Neve") ) 
	        						menuItemList.add(m);		
	        				}                                 
                       }//end of for loop
                }
                
                else if(diningHall.equals("Bruin Plate"))//put Bruin Plate's menu into menuItemList
                {
                        for(MenuItem m : menu){
                                //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
                        	if (mealTime.equals("All")){
            					if (m.getBreakfastDiningHall().contains("Bruin Plate") || 
            							m.getLunchDiningHall().contains("Bruin Plate") || m.getDinnerDiningHall().contains("Bruin Plate") ) 
            						menuItemList.add(m);		
            				}
        					else if (mealTime.equals("Breakfast")){
            					if (m.getBreakfastDiningHall().contains("Bruin Plate")) 
            						menuItemList.add(m);		
            				}
            				else if (mealTime.equals("Lunch")){
            					if (m.getLunchDiningHall().contains("Bruin Plate") ) 
            						menuItemList.add(m);		
            				}
            				else if (mealTime.equals("Dinner")){
            					if (m.getDinnerDiningHall().contains("Bruin Plate") ) 
            						menuItemList.add(m);		
            				}                           
                        }
                }
                
                else if(diningHall.equals("Feast"))//put Reiber's menu into menuItemList
                {
                    	for(MenuItem m : menu){
                                //if it contain Covel as dining hall offering this menu add it to list that will show on the screen
	                        if (mealTime.equals("All")){
	        					if (m.getBreakfastDiningHall().contains("Feast") || 
	        							m.getLunchDiningHall().contains("Feast") || m.getDinnerDiningHall().contains("Feast") ) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Breakfast")){
	        					if (m.getBreakfastDiningHall().contains("Feast")) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Lunch")){
	        					if (m.getLunchDiningHall().contains("Feast") ) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Dinner")){
	        					if (m.getDinnerDiningHall().contains("Feast") ) 
	        						menuItemList.add(m);		
	        				}                                     
                    	}
                }
                
                else if(diningHall.equals("Hedrick"))//put Hedrick menu into menuItemList
                {
                        for(MenuItem m : menu){
                        	if (mealTime.equals("All")){
	        					if (m.getBreakfastDiningHall().contains("Hedrick") || 
	        							m.getLunchDiningHall().contains("Hedrick") || m.getDinnerDiningHall().contains("Hedrick") ) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Breakfast")){
	        					if (m.getBreakfastDiningHall().contains("Hedrick")) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Lunch")){
	        					if (m.getLunchDiningHall().contains("Hedrick") ) 
	        						menuItemList.add(m);		
	        				}
	        				else if (mealTime.equals("Dinner")){
	        					if (m.getDinnerDiningHall().contains("Hedrick") ) 
	        						menuItemList.add(m);		
	        				}                                  
                        }
                }
                
                
                //Create Array Adapter
                dataAdapter = new MyCustomAdapter(this,R.layout.listview_row, menuItemList);
                ListView listView = (ListView) findViewById(R.id.listView1);
                //Assign adapter to ListView
                listView.setAdapter(dataAdapter);
        
                //I can add listener down here to make ListView respond to the click
                
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
        
                // This function is why we write custom adapter in the first place.
                // We can design how to display on the screen using this function.
                // For now, it only deal with the checkbox text.
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
                        name_button.setTag(item.getName()); //let tag of button be name of menu so we can take appropriate response
                                                                                                //when button is clicked by looking at the tag


                        //listener for the button at right side of each row
                        name_button.setOnClickListener(new View.OnClickListener()
                        {
                                @Override
                                public void onClick(View v) {
                                        Button btn = (Button) v;
                                        String menuName = (String) btn.getTag(); //tag is name of menu assigned above
                                        Bundle basket = new Bundle();                        //bundle is just way to pass data between Activities 
                                        basket.putString("name", menuName);         //we will pass name of menu that user clicked
                                        Intent ToMenuInfo = new Intent (DiningHallActivity.this, MenuInfoActivity.class);
                                        ToMenuInfo.putExtras(basket);
                                        startActivity(ToMenuInfo); //Go to MenuInfo Screen
                                }                        
                        });
                        
                        return row;                        
                }


        }//end of class MyCustomAdapter




    
}//end of DiningHall Activity
