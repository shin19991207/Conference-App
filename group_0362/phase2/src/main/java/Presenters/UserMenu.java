package main.java.Presenters;

import main.java.Controllers.MessageController;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * <h1>User Menu</h1>
 * This presenter contains the possible print statements the user should see in the user menu.
 * This should only be seen by the user after they have successfully logged in.
 * This is used in the console version of our program.
 * @author Blake Gigiolio
 */
public class UserMenu {

    /**
     * Once the user logs in to the program this is the first thing they should see, listing their
     * possible actions.
     */
    public void optionsAttendee() {
        System.out.println("---General Actions---");
        System.out.println("[1] See Event Schedule\n" +
                "[2] Review Your Events Schedule\n" +
                "[3] Sign Up For Event\n" +
                "[4] Cancel Event Attendance\n" +
                "[5] Send Message\n" +
                "[6] Review Messages\n" +
                "[7] Manage Friends List\n" +
                "[8] Logout\n"+
                "[20] Make a Request.\n" +
                "[q] Quit\n");
    }

    /**
     * If the user is an organizer, this should also be displayed along with optionsAttendee()
     */
    public void optionsOrganizer() {
        System.out.println("---Organizer Specific Actions---");
        System.out.println("[9] Create User\n" +
                "[10] Add Room\n" +
                "[11] Schedule Speaker\n" +
                "[12] Remove Event \n" +
                "[13] Message Event Attendees \n" +
                "[14] Create Event\n" +
                "[15] Modify Event Capacity. \n" +
                "[16] Get List of Attendees for Event. \n" +
                "[17] Get List of Requests.");
    }

    /**
     * If the user is a speaker, this should be displayed along with optionsAttendee()
     */
    public void optionsSpeaker() {
        System.out.println("---Speaker Specific Actions---");
        System.out.println("[9] View List of My Events\n"+
                "[10] Send Message");
    }

    public void optionsAdmin() {
        System.out.println("---Admin Specific Actions---");
        System.out.println("[9] Delete message\n"+
                "[10] Delete event with no attendees");
    }

    /**
     * Prompts the user to input a response
     */
    public void awaitResponse() {
        System.out.println("What would you like to do?");
    }

    /**
     * This is what the user should see if their response was invalid.
     */
    public void invalidResponse() {
        System.out.println("That is not a valid response. Please try again!");
    }

    /**
     * This prints the schedule for a given building.
     * @param building Which building we want to see the schedule of.
     * @param em  EventManager object to retrieve info about events
     */
    public void printBuildingSchedule(BuildingManager building, EventManager em) {
        System.out.println(building.getToString(em));
    }

    /**
     * This is what the user should see if they choose to sign up for or cancel enrollment in an event.
     * @param action the action the user chose (sign up/cancel registration)
     */
    public void eventPrompt(String action) {
        if (action.equals("sign up")) {
            System.out.println("Please enter the name of event you want to sign up for:");
        } else { System.out.println("Please enter the name of event you want to cancel:"); } }


    /**
     * This is what the user should see after cancelling their enrollment in an event.
     * @param eventTitle the event's title
     * @param status status
     */
    public void cancelEnrolStatus(String eventTitle, String status) {
        if (status.equals("1")) {
            System.out.println("You have successfully cancelled your enrollment in " + eventTitle + ".");
        } else {
            System.out.println("You did not sign up for the event " + eventTitle + ". \n" +
                    "[1] Go back \n[2] Enter another event");
        }
    }

    /**
     * This is what the user should see if they choose to remove an event.
     */
    public void manageEvent(){
        System.out.println("Which event would you like to remove?");
    }

    /**
     * This is what the user should see if they choose to send a message to a specific user.
     */
    public void sendMessageUser(){
        System.out.println("Which user would you like to send a message to?");
    }

    /**
     * This is what the user should when they are asked to input a to message content.
     */
    public void sendMessageContent(){
        System.out.println("What would you like to send them?");
    }

    /**
     * Print print on screen.
     * @param print what to print
     */
    public void printSomething(String print){
        System.out.println(print);
    }

    /**
     * This is what the user should see for inputting a room name if they choose to create an event.
     */
    public void createRoomName(){
        System.out.println("What will this room be called?");
    }

    /**
     * This is what the user should see for inputting the event start time if they choose to create an event.
     */
    public void createRoomStart(){
        System.out.println("When should this room open?");
        System.out.println("Please enter the hour (0-23) followed by the minute (0-59)");
    }

    /**
     * This is what the user should see for inputting the event end time if they choose to create an event.
     */
    public void createRoomEnd(){
        System.out.println("When should this room close?");
        System.out.println("Please enter the hour (0-23) followed by the minute (0-59)");
    }

    /**
     * This is what the user sees when they need to assign a type to the newly created Event.
     */
    public void createUserType() {
        System.out.println("Is the new user an Attendee, Admin, Organizer or Speaker?");
        System.out.println("Enter 'U' for Attendee, 'A' for Admin, 'O' for Organizer or 'S' for Speaker:");
    }

    /**
     * This is what the user sees when they need to assign a Speaker to a newly created Event.
     */
    public void createSpeakerName(){
        System.out.println("Enter Speaker name:");
    }

    /**
     * This is what the user sees when they are logging in.
     */
    public void createUserName(){
        System.out.println("Enter User name:");
    }

    /**
     * This is what the user sees when they have successfully made a new Speaker account.
     * @param uname The String representing the Speaker.
     */
    public void speakerMade(String uname){
        System.out.println("Speaker account for " + uname + " successfully created!");
        System.out.println("Default password is 'password'");
    }

    /**
     * This is what the user sees when they have successfully made a new Organizer account.
     * @param uname The String representing the Organizer.
     */
    public void organizerMade(String uname){
        System.out.println("Organizer account for " + uname + " successfully created!");
        System.out.println("Default password is 'password'");
    }

    /**
     * This is what the user sees when they have successfully made a new Attendee account.
     * @param uname The String representing the Attendee.
     */
    public void attendeeMade(String uname){
        System.out.println("Attendee account for " + uname + " successfully created!");
        System.out.println("Default password is 'password'");
    }

    /**
     * This is what the user sees when they have successfully made a new Admin account.
     * @param uname The String representing the Admin.
     */
    public void adminMade(String uname){
        System.out.println("Admin account for " + uname + " successfully created!");
        System.out.println("Default password is 'password'");
    }

    /**
     * This is what the user sees when an Event's name is required.
     */
    public void enterEvent(){ System.out.println("Enter existing Event name:"); }

    /**
     * This prints out the String representation of the Messages as a list.
     * @param messages The messages from DB.
     */
    public void printMessages(ArrayList<String> messages){
        StringBuilder builder = new StringBuilder();
        builder.append("Your messages: \n");
        for (String i : messages){
            builder.append(i);
        }
        System.out.println(builder);
    }

    /**
     * This is what the user sees when they need to enter the name of the new Event.
     */
    public void createEventName(){
        System.out.println("What should this event be named?");
    }

    /**
     * This is what the user sees when they need to enter the new Event's designated room.
     */
    public void createEventRoom(){
        System.out.println("Which room is this event taking place in?");
    }

    /**
     * This is what the user sees when they need to enter a Year for the new Event.
     */
    public void createEventYear(){
        System.out.println("What year is this event taking place in?");
    }

    /**
     * This is what the user sees when they need to enter a Month for the new Event.
     */
    public void createEventMonth(){
        System.out.println("What month is this event in?");
        System.out.println("please answer as a number between 1 and 12");
    }

    /**
     * This is what the user sees after performing an action.
     */
    public void promptagainonly(){
        System.out.println("Type A to see menu again");
    }

    /**
     * This is what the user sees after performing an action successfully.
     */
    public void operationComplete(){System.out.println("Operation Completed!");}

    /**
     * This is what the user sees when they need to enter a Day for the new Event.
     */
    public void createEventDay(){
        System.out.println("What day is this event taking place in?");
        System.out.println("please answer as a number between 1 and 31");
    }

    /**
     * This is what the user sees when they need to enter the hour when the new Event starts..
     */
    public void createEventHour(){
        System.out.println("What hour is this event taking place");
        System.out.println("Please enter a number between 0 and 23");
    }

    /**
     * This is what the user sees when they need to enter the minute the new Event starts.
     */
    public void createEventMinute(){
        System.out.println("What minute is this event taking place");
        System.out.println("Please enter a number between 0 and 59");
    }

    /**
     * This is what the user sees when they need to enter duration of the Event.
     */
    public void createEventDuration(){
        System.out.println("What is the duration of this event in minutes?");
    }

    /**
     * This is what the user sees after they have requested the friends list.
     */
    public void friendsList(){
        System.out.println("Would you like to add or remove someone from the friends list? Type A or R");
    }

    /**
     * This si what the user sees when they need to enter a another user's username.
     */
    public void friendsListUsername(){
        System.out.println("What is their username?");
    }

    /**
     * This is what the user sees when they have successfully logged out.
     */
    public void logoutSuccess() {
        System.out.println("You Have Successfully Logged Out Of Your Account.");
    }

    /**
     * This is what the user sees when needing to set the Capacity of a new Event.
     */
    public void createEventCapacity() {
        System.out.println("What is the maximum occupancy for this event?");
    }

    /**
     * This is what the user sees when needing to set the Capacity of a new room.
     */
    public void createRoomCapacity() {
        System.out.println("What is the maximum occupancy for this room?");
    }

    /**
     * This is what the user sees when wanting to set a new Capacity for an Event.
     */
    public void modifyEventCapacity(){
        System.out.println("What is the new Capacity?");
    }

    /**
     * This is what the user sees after calling for the schedule.
     */
    public void scheduleDownload() { System.out.println("[1] Download schedule\n[2] Go back");}

    /**
     * This is what the user sees when they need to enter a UUId for an Event.
     */
    public void enterEventID() { System.out.println("What is the Event's ID?");}

    /**
     * This is what a user sees when they have entered an invalid UUID format.
     */
    public void invalidID() { System.out.println("Invalid ID format entered."); }

    /**
     * This prints the String representation of the List of Attendees for an Event.
     * @param eventAttendees The String representation of the List of Attendees for an Event.
     */
    public void printAttendees(String eventAttendees) {
        System.out.println(eventAttendees);
    }

    /**
     * This prints a list of Events that specifically have no users in their lists of attendees.
     * @param em The current Event Manager initialized in the program.
     */
    public void displayEventsWithNoAttendees(EventManager em){
        System.out.println("Here are the events with no attendees:");
        System.out.println("Event ID\t\t\t\t\t\t\t   Event name");
        for(UUID id : em.getEventIDNoAttendees())
            System.out.println(id.toString() + "   " + em.getEventTitle(id));
        System.out.println("\n\nEnter the ID of the event you want to delete or 'q' to exit:");
    }

    /**
     * This prints out the String representation of the Events the Speaker, text, is speaking at.
     * @param text The String representing the speaker.
     */
    public void viewSpeakerEvents(String text){
        System.out.println("List of events I'm talking at:\n" + text);
    }

    /**
     * This prints out the String representation of all of the Messages stored in the DB.
     * @param mc The current MessageController instance.
     */
    public void viewAllMessages(MessageController mc){
        System.out.println("Here are all the messages sent in the system:");
        System.out.println(mc.getAllMessages());
    }

    /**
     * This is what the user sees when they want to delete a Message.
     */
    public void deleteMessagePrompt(){
        System.out.println("To delete a message, enter the sender, the receiver and the message body:");
    }

    /**
     * This is what the user sees when they want to make a request to the System.
     */
    public void request() { System.out.println("Is your request [food], [transportation], or [vision]?");
    }

    /**
     * This is what the user sees after calling for the List of Requests.
     */
    public void requestAction() {System.out.println("Would you like to [Reject], [Address], or [Leave]?");
    }

    /**
     * This is what the User sees when they need to enter a username to perform an action on the request.
     */
    public void enterUsername() { System.out.println("Please enter the username associated with the desired request");
    }

    /**
     * This is what the user sees when they need to enter the number of the request they wish to perform the action on.
     */
    public void enterRequestNum() { System.out.println("Please enter the associated num with the desired request.");
    }

    /**
     * This is what the user sees when they have successfully downloaded the Schedule of Events.
     * @param destination The String representing the file path where the Schedule is to be delivered.
     */
    public void downloadSuccess(String destination) { System.out.println("Schedule has been successfully downloaded to " + destination + ".");}

    /**
     * This is what the user sees when the Schedule of Events failed to download.
     */
    public void downloadFailed() { System.out.println("Failed to download schedule due to server error."); }

    /**
     * This is what the user sees when they have called the menu again.
     */
    public void selectAnotherOption() { System.out.println("Please select another menu option."); }

    /**
     * This is what the user sees when the file location for the Schedule has not been given yet.
     */
    public void downloadLocationNotSelected() { System.out.println("No file location was selected."); }
}
