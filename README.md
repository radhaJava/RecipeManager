# FoodMora RecipeManager
## About
Foodmora is a small Swedish startup company with roots in the Swedish town of Mora. As they failed to get traction and outcompete leksandknäcke with their mora-knäcke they have now pivot into the food-tech market. Essentially, they want to create an app that helps you generate a schedule for what to eat each day. They are in the process of designing an app but need a prototype. They have settled for using a terminal app.
##Introduction -Requirement gathering
The requirement gathering started with  User Stories and Task creation and then UML Usecase diagram and Class diagram.The documents about the user stories, tasks ,case diagram and Class diagram are available in file "requiremnt_documents(RecipeManager).docx near to readme file
## Usage
The application is a command line interface (terminal) application.There are two user roles (Dietician and Customer ) they navigates by typing a number from an option list and then press enter to execute the command. There is a option to go the main menu and also exit the application.


#### The Dietician role have option like 
        -Create recipe,        
        -View recipe Pool and        
        -Edit recipe
        
        
#### The Customer role have option like
        -Generate week recipe,
        -View Specific recipe and
        -View old week 
                
				

                 
##### The work flow and design 
The application loads with some preinserted recipes in recipePool for easy access of applicion to view recipes in pool and edit that recipes and also assign the recipes to cutomer while generating the week.The flow starts by creating recipes, they can see the created recipe added to the recipe list which is available in  view recipoolsand also can edit the recipes using recipe number. This all done through a manual assign of recipes to the arraylist in recipePool which is started along with the application is started.The customer is allowed to generate week which was done by using calender function and random assign of weekly recipe using random function and then the customer can view the recipes for the week and he can also view old week recipes ( which is in-progree of fixing )

				
## Technologies
        -User Stories(Jira)
        -UML Usecase diagram(Lucidchart)
        -UML Class diagram (PlantUML)
        -Developed code in IntellliJ Idea Community version using Java 17.0.2


 The above diagrams are find in "requiremnt_documents(RecipeManager).docx along with some link .Step by step creation of how project requiremnt started with User Stories and task and also usecases and then class diagram and then to code is shown in screenshots.The entire process followed agile methodology.
## Launch
To run the Jar file in commandprompt(cmd) using java -jar foodmora-RecipeManager.jar .






## Room for Improvements
-Multiple users each with its own RecipeWeek.
-Persistence, store recipes and RecipeWeek in a file so that next time the user logs in the changes are still there.
-Add tags to the recipes so that a User may choose to generate a RecipeWeek with only the vegetarian tag. Or maybe limit the number of recipes based on pasta to 3 each week.
-Summaries ingredients for the week such that you have a simple shopping list. 
