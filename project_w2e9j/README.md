# **MyFridge**

## *Keep track of the items in your fridge and never have food expire again!*

  

### **Overview**
My application is called MyFridge. The program will allow users to keep track of the items in their fridge and their 
respective expiry dates. Users will be able to add items to their fridge and access a variety of information about each 
item such as:
- Expiry date
- Food type
- Cost

This application will be useful to individuals who want to know exactly what items are in their fridge, so they can 
prepare specific meals and for those who want to make sure none of their food expires and goes to waste. Users with many
items in their fridge will benefit most as they can easily look at the application and see a list of every item in their
fridge, instead of scouring their crowded fridge for specific items.

### **Why I chose this project**
This year, I moved into a house with three of my friends. Because I am busy with schoolwork, I find that items that I 
buy often expire and go to waste before I can use them. This program is of interest to me for two main reasons:
1. Checking which items in my fridge are going to expire soon will allow me to systematically use the items that will 
expire first in order to save money and avoid food waste.
2. Keeping an accurate record of all the food in my fridge will help me determine whether I have the right ingredients 
for a specific meal that I want to cook.

### **User Stories**
How can a user use my application to achieve a certain outcome? Below are some User Stories for my application.
- As a user, I want to be able to add multiple food items to my fridge.
- As a user, I want to be able to remove an item from my fridge.
- As a user, I want to be able to see how many days I have until an item in my fridge expires.
- As a user, I want to be able to view the dollar value of all items in my fridge.

### **Phase 2 User Stories**
The user stories below relate to data persistence and the need for users to be able to save and load data from a file.
- As a user, I want to be able to save my Fridge to file.
- As a user, I want to be able to load my Fridge to file.

### **Phase 4: Task 2**
Below is a representative sample of the event log printed when a user quits the application:
Thu Nov 25 20:08:25 PST 2021
Loaded fridge from ./data/fridge.json

Thu Nov 25 20:08:25 PST 2021
Added bread to fridge

Thu Nov 25 20:08:25 PST 2021
Added carrots to fridge

Thu Nov 25 20:08:25 PST 2021
Added milk to fridge

Thu Nov 25 20:08:25 PST 2021
Added cheddar to fridge

Thu Nov 25 20:08:25 PST 2021
Added cilantro to fridge

Thu Nov 25 20:08:25 PST 2021
Added juice to fridge

Thu Nov 25 20:08:35 PST 2021
Removed cilantro from fridge

Thu Nov 25 20:08:42 PST 2021
Checked expiry date of bread

Thu Nov 25 20:08:46 PST 2021
Saved fridge to ./data/fridge.json

### **Phase 4: Task 3**
After completing my UML diagram, I thought of how I could refactor my project. I realized that my JTextPane displaying 
the list of items in a user's fridge updated continually through the update() method in the MyFridgeGUI class. If I were
to refactor my project, I would instead add an observer that notifies MyFridgeGUI everytime an item is added/removed to 
update the JTextPane.
