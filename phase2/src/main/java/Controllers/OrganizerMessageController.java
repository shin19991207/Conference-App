package main.java.Controllers;

import main.java.Controllers.MessageController;
import main.java.Entities.Attendee;
import main.java.UseCases.UserManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * <h1>OrganizerMessageController</h1>
 * This Controller is responsible for sending Messages to various users.
 * This should only interact with the AttendeeMenuController.
 * @author Zachary Werle
 * @version Phase 1
 */
public class OrganizerMessageController {

    /**
     * The username of the organizer
     */
    private final String organizer;

    /**
     * This constructor creates a new instance of OrganizerMessageController given the current Organizer.
     * @param organizer The organizer's username
     */
    public OrganizerMessageController(String organizer){
        this.organizer = organizer;
    }

    /**
     * This method sends Messages to one Speaker.
     * @param oneSpeaker The Speaker receiving the Message.
     * @param inputMessage The Message the speaker is to receive.
     */
    public void toOneSpeaker(String oneSpeaker, String inputMessage) {
        MessageController send = new MessageController(organizer, oneSpeaker, inputMessage);
        send.sendMessage();
    }

    /**
     * This method sends Messages to one Attendee.
     * @param oneAttendee The Attendee receiving the Message.
     * @param inputMessage The Message the Attendee is to receive.
     */
    public void toOneAttendee(String oneAttendee, String inputMessage) {
        MessageController send = new MessageController(organizer, oneAttendee, inputMessage);
        send.sendMessage();
    }

    /**
     * This method sends Messages to Attendees' Friends.
     * @param inputMessage The Message the Friends are receiving.
     */
//    public void toFriends(String inputMessage, int numOfFriends) {
//        for(int i = 0; i < numOfFriends; i ++){
//            MessageController send = new MessageController(organizer, , inputMessage);
//            send.sendMessage();
//        }
//    }

    /**
     * This method sends Messages to all Speakers.
     * @param inputMessage The Message the Speakers are to receive.
     * @param manager The LoginUserManager that will be used to send the Messages to the Speakers.
     */
    public void toAllSpeaker(String inputMessage, UserManager manager) {
        HashMap<String, Attendee> users = manager.getCredentialsMap();
        for(String username : users.keySet()){
            if(manager.getUserRole(username).equals("speaker")){
                MessageController send = new MessageController(organizer, username, inputMessage);
                send.sendMessage();
            }
        }
    }

    /**
     * This method sends Messages to all Attendees.
     * @param inputMessage The Message the Attendees are to receive.
     * @param manager The LoginUserManager that will be used to send the Messages to the Attendees.
     */
    public void toAllAttendee(String inputMessage, UserManager manager) {
        HashMap<String, Attendee> users = manager.getCredentialsMap();
        for (String username : users.keySet()){
            if(manager.getUserRole(username).equals("attendee")){
                MessageController send = new MessageController(organizer, username, inputMessage);
                send.sendMessage();
            }
        }
    }
}


