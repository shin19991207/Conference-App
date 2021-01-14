package main.java.Controllers;

import main.java.Gateways.BuildingGateway;
import main.java.Gateways.EventGateway;
import main.java.Presenters.UserMenu;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;
import main.java.UseCases.UserManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * <h1>AttendeeMenuController</h1>
 * This controller takes in input from an attendee when they are presented with a menu of options
 * Used for the console interface
 * @version phase2
 */
public class AttendeeMenuController {
    private final String username;
    private final String role;
    private final UserMenu menu;
    private final BuildingManager building;
    private final UserManager userManager;
    private final EventManager eventManager;
    private final AccessibilityOptionsController accessibility;

    /**
     * This constructor takes in the parameters needed to operate the menu.
     *
     * @param username    This is the username of the user who this menu is for.
     * @param role the role of the user
     * @param building    This is the Building Manager for the building that the user is interested in.
     * @param userManager This is the user manager for the user that this menu is for.
     * @param eventManager the event event manager object for this program
     * @param accessibility the accessibility options controller for this project
     */
    public AttendeeMenuController(String username, String role, BuildingManager building, UserManager userManager,
                                  EventManager eventManager, AccessibilityOptionsController accessibility) {
        this.username = username;
        this.role = role;
        this.menu = new UserMenu();
        this.building = building;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.accessibility = accessibility;
    }

    /**
     * Displays options for each specific kind of user
     */
    public void homepage() {
        menu.optionsAttendee();
        if (role.equalsIgnoreCase("organizer"))
            menu.optionsOrganizer();
        else if (role.equalsIgnoreCase("speaker"))
            menu.optionsSpeaker();
        else if (role.equalsIgnoreCase("admin"))
            menu.optionsAdmin();
    }

    /**
     * This is what the user should see if they choose to sign up for an event.
     * @return true if user successfully signed up for this event
     */
    private boolean signUpEvent() {
        menu.eventPrompt("sign up");
        String inp = new Scanner(System.in).nextLine();
        UUID id;

        try {
            id = UUID.fromString(inp);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong format!");
            return false;
        }

        return userManager.signUpForEvent(username, id) & eventManager.addAttendee(id, username);
    }

    /**
     * Method responsible for removing user from Event.
     * @return true if user successfully cancelled their attendance
     */
    private boolean cancelEnrolEvent() {
        menu.eventPrompt("cancel");
        String inp = new Scanner(System.in).nextLine();
        UUID id;

        try {
            id = UUID.fromString(inp);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong format!");
            return false;
        }

        return userManager.cancelEnrollment(username, id) & eventManager.removeAttendee(id, username);
    }

    /**
     * Facilitates sending messages between users
     */
    public void sendMessage() {
        this.menu.sendMessageUser();
        String user = new Scanner(System.in).nextLine();
        if (!userManager.checkUsername(user)) return;
        this.menu.sendMessageContent();
        String content = new Scanner(System.in).nextLine();
        MessageController message = new MessageController(this.username, user, content);
        message.sendMessage();
    }

    /**
     * Facilitates creating a room in the building
     * @return true if room was added successfully
     */
    public boolean addRoom() {
        int startH, startM;
        int endH, endM;
        String inpStr1, inpStr2;
        Scanner cin = new Scanner(System.in);

        this.menu.createRoomName();

        String name = cin.nextLine();

        this.menu.createRoomStart();
        inpStr1 = cin.nextLine();
        inpStr2 = cin.nextLine();

        try {
            startH = Integer.parseInt(inpStr1);
            startM = Integer.parseInt(inpStr2);
        } catch (NumberFormatException e) {
            return false;
        }

        if (startH > 23 || startH < 0 || startM < 0 || startM > 59)
            return false;

        this.menu.createRoomEnd();
        inpStr1 = cin.nextLine();
        inpStr2 = cin.nextLine();

        try {
            endH = Integer.parseInt(inpStr1);
            endM = Integer.parseInt(inpStr2);
        } catch (NumberFormatException e) {
            return false;
        }

        if (endH > 23 || endH < 0 || endM < 0 || endM > 59)
            return false;

        this.menu.createRoomCapacity();
        String roomCapacityString = new Scanner(System.in).nextLine();
        int roomCapacity;

        try {
            roomCapacity = Integer.parseInt(roomCapacityString);
        } catch (NumberFormatException e) {
            return false;
        }

        if (roomCapacity < 0)
            return false;

        return building.addRoom(name, LocalTime.of(startH, startM), LocalTime.of(endH, endM), roomCapacity);
    }

    /**
     * Facilitates adding a speaker to a talk or panel discussion
     * @return true if speaker was added successfully
     */
    public boolean scheduleSpeaker() {
        this.menu.createSpeakerName();
        Scanner cin = new Scanner(System.in);

        String speakerName = cin.nextLine();
        if (!userManager.checkUsername(speakerName) || !userManager.getUserRole(speakerName).equals("speaker"))
            return false;

        this.menu.enterEventID();
        String eventID = cin.nextLine();
        UUID id;

        try {
            id = UUID.fromString(eventID);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid id format");
            return false;
        }

        return eventManager.setSpeaker(id, speakerName) & userManager.addTalk(speakerName, id);
    }

    /**
     * Attempts to send a String representation of all of the users attending an Event to the Presenter.
     * @return boolean
     */
    public boolean getListOfAttendees() {
        menu.enterEventID();
        String eventID = new Scanner(System.in).nextLine();
        UUID id;
        ArrayList<String> attendees;

        try {
            id = UUID.fromString(eventID);
        } catch (IllegalArgumentException e) {
            menu.invalidID();
            return false;
        }
        attendees = eventManager.getAttendees(id);

        StringBuilder printout = new StringBuilder("List of Attendees: ");

        for (String s : attendees)
            printout.append(s).append(", ");

        menu.printAttendees(printout.toString());
        return true;

    }

    /**
     * Attempts to get the user's request and calling the AccessibilityOptionsController to add it to the system.
     * @return boolean
     */
    public boolean addRequest() {
        this.menu.request();
        Scanner scan = new Scanner(System.in);
        String request = scan.nextLine();
        if (request.equals("food") || request.equals("transportation") || request.equals("vision")) {
            accessibility.sendRequest(this.username, request);
            accessibility.save();
            return true;
        }
        return false;
    }

    /**
     * Attempts to send a String representation of the requests stored in the DB to the Presenters.
     * @return boolean
     */
    public boolean getRequests() {
        ArrayList<String> requests = accessibility.getAllRequest();
        StringBuilder printout = new StringBuilder("List of Requests:\n");

        for (String r : requests) {
            printout.append("*").append(r).append("\n");
        }

        this.menu.printAttendees(printout.toString());
        this.menu.requestAction();

        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();

        if (action.equalsIgnoreCase("Leave")) {
            return true;
        }

        this.menu.enterUsername();
        String user = scan.nextLine();
        this.menu.enterRequestNum();
        String tempNum = scan.nextLine();

        int num;

        try {
            num = Integer.parseInt(tempNum);
        } catch (NumberFormatException e) {
            return false;
        }

        if (action.equalsIgnoreCase("Address")) {
            accessibility.addressRequest(user, num);
        } else if (action.equalsIgnoreCase("Reject")) {
            accessibility.rejectRequest(user, num);
        }
        accessibility.save();
        return true;
    }

    /**
     * Attempts to get the new capacity and replaces the old capacity for the desired Event.
     * @return boolean
     */
    public boolean modifyCapacity() {
        Scanner cin = new Scanner(System.in);
        int newCapacity;
        String eventID;
        UUID id;

        this.menu.enterEvent();
        eventID = cin.nextLine();

        try {
            id = UUID.fromString(eventID);
        } catch (IllegalArgumentException e) {
            return false;
        }

        this.menu.modifyEventCapacity();
        String tempNewCapacity = cin.nextLine();

        try {
            newCapacity = Integer.parseInt(tempNewCapacity);
        } catch (NumberFormatException e) {
            return false;
        }
        if (newCapacity < 0)
            return false;
        return eventManager.changeCapacity(id, newCapacity);
    }

    /**
     * Responsible for removing an event from the building's schedule
     * @return true if event was removed successfully
     */
    public boolean removeEvent() {
        this.menu.manageEvent();
        String eventID = new Scanner(System.in).nextLine();
        UUID id;

        try {
            id = UUID.fromString(eventID);
        } catch (IllegalArgumentException e) {
            return false;
        }
        boolean returnVal = building.deleteEvent(id, eventManager.getEventCapacity(id)) & eventManager.deleteEvent(id);
        new EventGateway().save(eventManager);
        new BuildingGateway().save(building);
        return returnVal;
    }

    /**
     * Facilitates messaging all attendees of a specific event
     */
    public void messageAttendees() {
        //TODO: Add speaker send message functionality
    }

    /**
     * Facilitates adding/removing friends to/from the friend list of a user
     */
    public void manageFriendsList() {
        menu.friendsList();
        String choice = new Scanner(System.in).nextLine();
        String user2;

        if (choice.equalsIgnoreCase("A")) {
            menu.friendsListUsername();
            user2 = new Scanner(System.in).nextLine();

            if (!userManager.checkUsername(user2))
                menu.invalidResponse();
            else
                userManager.addFriend(this.username, user2);
        } else if (choice.equalsIgnoreCase("R")) {
            this.menu.friendsListUsername();
            user2 = new Scanner(System.in).nextLine();

            if (!userManager.checkUsername(user2))
                menu.invalidResponse();
            else
                userManager.removeFriend(this.username, user2);
        } else {
            this.menu.invalidResponse();
        }
    }

    /**
     * Facilitates the creation of user accounts
     */
    public void createUser() {
        menu.createUserType();
        String userType = new Scanner(System.in).nextLine();
        menu.createUserName();
        String userName = new Scanner(System.in).nextLine();

        if (userType.equalsIgnoreCase("o")) {
            if (userManager.registerUser(userName, "password", "organizer"))
                menu.organizerMade(userName);
            else
                menu.invalidResponse();
        } else if (userType.equalsIgnoreCase("u")) {
            if (userManager.registerUser(userName, "password", "attendee"))
                menu.attendeeMade(userName);
            else
                menu.invalidResponse();
        } else if (userType.equalsIgnoreCase("a")) {
            if (userManager.registerUser(userName, "password", "admin"))
                menu.adminMade(userName);
            else
                this.menu.invalidResponse();
        } else if (userType.equalsIgnoreCase("s")) {
            if (userManager.registerUser(userName, "password", "speaker"))
                menu.speakerMade(userName);
            else
                this.menu.invalidResponse();
        }
    }

    /**
     * Facilitates the creation of events
     * @return true if an event was created
     */
    public boolean createEvent() {
        Scanner cin = new Scanner(System.in);

        this.menu.createEventName();
        String eventName = cin.nextLine();

        this.menu.createEventRoom();
        String roomName = cin.nextLine();

        this.menu.createEventCapacity();
        String tempEventCapacity = cin.nextLine();

        this.menu.createEventYear();
        String yearString = cin.nextLine();

        this.menu.createEventMonth();
        String monthString = cin.nextLine();

        this.menu.createEventDay();
        String dayString = cin.nextLine();

        this.menu.createEventHour();
        String hourString = cin.nextLine();

        this.menu.createEventMinute();
        String minuteString = cin.nextLine();

        this.menu.createEventDuration();
        String durationStr = cin.nextLine();

        System.out.println("Enter <1> for no-speaker event, <2> talk, <3> panel discussion:");
        String type = cin.nextLine();

        int year;
        int month;
        int day;
        int hour;
        int minute;
        int duration;
        int eventCapacity;
        int choice;

        try {
            year = Integer.parseInt(yearString);
            month = Integer.parseInt(monthString);
            day = Integer.parseInt(dayString);
            hour = Integer.parseInt(hourString);
            minute = Integer.parseInt(minuteString);
            duration = Integer.parseInt(durationStr);
            eventCapacity = Integer.parseInt(tempEventCapacity);
            choice = Integer.parseInt(type);
        } catch (NumberFormatException e) {
            return false;
        }
        LocalDateTime d;
        try {
            d = LocalDateTime.of(year, month, day, hour, minute, 0);
        } catch (java.time.DateTimeException e) {
            return false;
        }

        System.out.println(d);

        if (choice == 1) {
            if (!eventManager.addEvent(eventName, roomName, d, duration, eventCapacity, "event", building))
                return false;
        } else if (choice == 2) {
            if (!eventManager.addEvent(eventName, roomName, d, duration, eventCapacity, "talk", building))
                return false;
        } else if (choice == 3) {
            if (!eventManager.addEvent(eventName, roomName, d, duration, eventCapacity, "panelDiscussion", building))
                return false;
        }

        new EventGateway().save(eventManager);
        new BuildingGateway().save(building);
        return true;
    }

    /**
     * Responsible for sending a message to all attendees
     */
    public void organizerMessageAll() {
        menu.sendMessageContent();
        String content = new Scanner(System.in).nextLine();
        new OrganizerMessageController(this.username).toAllAttendee(content, userManager);
    }

    /**
     * Facilitates downloading the schedule of the conference
     */
    public void downloadScheduleTxt() {
        menu.scheduleDownload();
        String option = new Scanner(System.in).nextLine();
        if (option.equals("1")) {
            new BuildingGateway().constructScheduleTxt(building.getToString(eventManager));
            downloader();
        } else if (!option.equals("2")) {
            menu.invalidResponse();
            downloadScheduleTxt();
        }
    }

    /**
     * Handle the action of downloading schedule Schedule.txt to the user's local computer.
     */
    public void downloader() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("*.txt", ".txt"));
            int option = chooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File destination = chooser.getSelectedFile();
                if (!destination.getName().endsWith(".txt")) {
                    destination = new File(destination + ".txt");
                }
                try {
                    InputStream file = new FileInputStream("phase2/src/main/java/DB/Schedule.txt");
                    Files.copy(file, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    menu.downloadSuccess(destination.toString());
                    menu.selectAnotherOption();
                    menu.promptagainonly();
                } catch (Exception ex) {
                    menu.downloadFailed();
                    menu.selectAnotherOption();
                    menu.promptagainonly();
                }
            } else {
                menu.downloadLocationNotSelected();
                menu.selectAnotherOption();
                menu.promptagainonly();
            }
        });
    }

    /**
     * This is where the user will decide what they want to do. The possible options are:
     * [1] See Event Schedule | added to GUI
     * [2] Review Your Events Schedule | added to GUI
     * [3] Sign Up For Event | added to GUI
     * [4] Cancel Event | added to GUI
     * [5] Send Message
     * [6] Review Messages
     * [7] Manage Friends List
     * [8] Logout | added to GUI
     * [20] Make Request
     * [q] Quit
     * ---AVAILABLE FOR ORGANIZERS ONLY---
     * [9] Create User Account | added to GUI
     * [10] Add Room | added to GUI
     * [11] Schedule Speaker
     * [12] Remove Event
     * [13] Message Event Attendees
     * [14] Create Event | added to GUI
     * [15] Modify Event Capacity
     * [16] Get List of Attendees
     * [17] Get List of Requests
     *
     * @return true if user chose to exit program, false if user just logged out
     */
    public boolean menuSelection() {
        Scanner uname = new Scanner(System.in);
        int choice;
        String response;

        while (true) {
            response = uname.nextLine();

            //quit
            if (response.equalsIgnoreCase("q")) return true;

                //display homepage
            else if (response.equalsIgnoreCase("a")) {
                homepage();
                continue;
            }

            try {
                choice = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 8) {
                menu.logoutSuccess();
                return false;
            }

            if (!attendeeSwitch(choice)) {
                switch (role) {
                    case "speaker":
                        speakerSwitch(choice);
                        break;
                    case "organizer":
                        organizerSwitch(choice);
                        break;
                    case "admin":
                        adminSwitch(choice);
                        break;
                }
            }
            menu.promptagainonly();
        }
    }

    /**
     * Helper method which matches user input to attendee actions
     * @param choice the user's input
     * @return true if the user's input matches one of the attendee actions
     */
    private boolean attendeeSwitch(int choice) {
        switch (choice) {
            case 1:
                menu.printBuildingSchedule(building, eventManager);
                downloadScheduleTxt();

            case 2:
                StringBuilder toPrint = new StringBuilder();
                toPrint.append("Events you are attending: \n");
                try {
                    for (String i : eventManager.eventsAttending(username)) {
                        toPrint.append(i).append("\n");
                    }
                } catch (NullPointerException e) {
                    toPrint.replace(0, toPrint.length(), "You are not attending any events");
                }
                String sPrint = toPrint.toString();
                menu.printSomething(sPrint);
                menu.operationComplete();
                break;

            case 3:
                if (signUpEvent())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;

            case 4:
                if (cancelEnrolEvent())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;

            case 5: //send message
                sendMessage(); //TODO: Add case where receiver user doesn't exist
                break;

            case 6: //review messages
                MessageController message = new MessageController();
                try {
                    menu.printMessages(message.getMessageForMe(username));
                } catch (NullPointerException e) {
                    menu.printSomething("You have no messages");
                }
                break;

            case 7: //Manage Friends List
                manageFriendsList();
                menu.operationComplete();
                break;
            case 20:
                if (!addRequest()) {
                    this.menu.invalidResponse();
                } else {
                    this.menu.operationComplete();
                }
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Helper method which matches user input to organizer actions
     * @param choice the user's input
     */
    private void organizerSwitch(int choice) {
        switch (choice) {
            case 9: //create user account
                createUser();
                menu.operationComplete();
                break;

            case 10: //add room
                if (!addRoom()) menu.invalidResponse();
                else menu.operationComplete();
                break;

            case 11: //schedule speaker
                if (!scheduleSpeaker()) this.menu.invalidResponse();
                else menu.operationComplete();
                break;

            case 12: //Remove Event
                if (!removeEvent())
                    this.menu.invalidResponse();
                else
                    menu.operationComplete();
                break;

            case 13: //Message All Attendees
                organizerMessageAll();
                menu.operationComplete();
                break;

            case 14: //add event
                if (createEvent())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;

            case 15: // modify event capacity
                if (modifyCapacity())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;

            case 16: // get List of Attendees for Event
                if (getListOfAttendees())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;
            case 17:
                if (getRequests())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;
            default:
                this.menu.invalidResponse();
                break;
        }
    }

    /**
     * Helper method which matches user input to administrator actions
     * @param choice the user's input
     */
    private void adminSwitch(int choice) {
        switch (choice) {
            case 9:     //delete messages
                MessageController mc = new MessageController();
                String un, pw, content;
                Scanner cin = new Scanner(System.in);
                menu.viewAllMessages(mc);
                menu.deleteMessagePrompt();
                un = cin.nextLine();
                pw = cin.nextLine();
                content = cin.nextLine();
                mc = new MessageController(un, pw, content);
                if (mc.deleteMessage())
                    menu.operationComplete();
                else
                    menu.invalidResponse();
                break;
            case 10:    //delete event with no attendees
                menu.displayEventsWithNoAttendees(eventManager);
                String id = new Scanner(System.in).nextLine();
                if (id.equals("q"))
                    return;

                UUID uuid;
                try {
                    uuid = UUID.fromString(id);
                } catch (IllegalArgumentException e) {
                    menu.invalidResponse();
                    return;
                }
                if (eventManager.checkEvent(uuid)) {
                    building.deleteEvent(uuid, eventManager.getEventCapacity(uuid));
                    eventManager.deleteEvent(uuid);
                    menu.operationComplete();
                    return;
                }
                menu.invalidResponse();
                break;
            default:
                this.menu.invalidResponse();
                break;
        }
    }

    /**
     * Helper method which matches user input to speaker actions
     * @param choice the user's input
     */
    private void speakerSwitch(int choice) {
        switch (choice) {
            case 9:     //view list of my events
                menu.viewSpeakerEvents(eventManager.getEventsOfSpeakerUsernameToString(username));
                menu.operationComplete();
                break;
            case 10:    //send message
                messageAttendees();
                menu.operationComplete();
                break;
            default:
                this.menu.invalidResponse();
                break;
        }
    }
}
