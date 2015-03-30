# UCLA dining hall application for Android device
UCLA dining hall application for Android device:

Due to the fact that UCLA has several dining halls and official UCLA dining hall website had some usability issues on mobile devices, this application was trying to provide easier access to the UCLA dining hall menu with an additional features. 

Overview of Important files:
*all soruce files are in /src/com/example/testdininghall
*xml files for user interface are in /res

DiningHall.java: extract menu information from official UCLA dining hall website using JTidy and XPath.

StarterActivity: executed when application start; if database is empty or outdated, obtain up to date information from website and create new date database and delete outdated database.

HomeActivity.java: display home screen of this application with an additional features on user interface.

DiningHallActivity.java: display menu seperated by different dining hall offering those menu with an additional features on user interface.

MealActivity.java: display menus seperated by the meal time(i.e. breakfast, lunch, dinner) with an additional features on user interface.

AllMenuActivity.java: display entire menu available with an additional features on user interface.

FavoriteActivity.java: display only user's favorite menu with an additional features on user interface.

MenuInfoActivity.java: display selected menu's nutrition information.

MenuItem.java: class that contain single menu's information such as name, dining hall offering this menu at different meal time, and nutrition information.

MenuDatabase.java: contain entire menu information loaded from the database.

MenuDatabaseSQLite.java: create SQL database and update database if requested. Also return menu information as an ArrayList.


