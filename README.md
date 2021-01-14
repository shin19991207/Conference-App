# ConferenceApp
- This is a group project for CSC207 Software Design course at the University of Toronto St. George.

- This is a program built in Java that allows people at a conference, for example, a tech conference or an employment fair, to communicate with each other in specific ways (CSC207 Project Specification). 

# Set Up

## Clone this Repository
`git clone https://github.com/shin19991207/ConferenceApp.git`

## Prerequisites
This application requires Java JDK8.

- Install IntelliJ IDEA Community Edition: https://www.jetbrains.com/idea/download/#section=mac

- Install JDK 8: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

## Build and Run

- phase2 > Right Click src > Mark Directory as Sources Root

- The program starts by running the class named "Main" under package "sample" (phase2 > src > main.java > sample > Main).

# Functions

## Login and Register

   * New User Account Registration:
     Select 'User Type: New User' > Choose an 'User Role' > Enter the 'Username' and 'Password' you want to use for your account

   * Returning User Login:
     Select 'User Type: Returning User' > Enter your 'Username' and 'Password'


## User Interface

   - This is the interface the user gets to after logging in
   - There is an 'Actions' menu where user can choose the actions they would like to do
   - Attendees and Speakers can only to do all General Actions
   - Organizers and Admins can do all General Actions plus all Organizer Actions

   ### General Actions
   
   Actions available for all types of user

   * See Event Schedule
        - 'Full Schedule': See the schedule of events that can be signed up for
        - 'Your Schedule': See the schedule of the events for which you have signed up for
        - Search for specific events by event 'ID', 'Title', 'Location', 'Date Time', 'Type', 'Speaker' using the choice box and the search box
        - 'Download Full Schedule' onto the user's local computer

   * Manage My Events
        - 'Sign Up' and 'Leave Event' by entering an event ID (copy from 'See Event Schedule')

   * Message
      - 'Review Message': See all messages in your inbox
      - 'Send Message': Send a message to 'Username'

   * Manage Friends List
      - 'Add friend' and 'Remove friend' by entering 'Friend's username'

   * LogOut
      - Log out of your account

   ### Organizer Actions
   Actions available for organizers and admins only

   * Create User Account
      - Create a new account for a specific use

   * Add Room
      - Add a new event location

   * Schedule Speaker
      - Schedule speaker(s) to an event

   * Remove Event: Remove a scheduled event

   * Message Event Attendees: message all attendees of a specific event

   * Create Event: Schedule a new event

## Saving and Persistence of Information

   Information is auto-saved into the data bases under package "DB" whenever an action is done

## Acknowledgements
CSC207 Fall 2020 group_0362
* Sehajroop Singh Bath
* Morgan Chang
* Yuteng Gao
* Blake Alexander Gigiolio
* Utkarsh Vidyadhar Mali
* Konstantinos Papaspyridis
* Zachary Scott Werle
* Qi Zheng

## Preview
- Login Scene

![ScreenShot](https://github.com/shin19991207/ConferenceApp/blob/main/images/loginUI.png)

- Home Scence 

![ScreenShot](https://github.com/shin19991207/ConferenceApp/blob/main/images/homeUI.png)

- Schedule Scene

![ScreenShot](https://github.com/shin19991207/ConferenceApp/blob/main/images/scheduleUI.png)
