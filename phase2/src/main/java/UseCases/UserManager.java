package main.java.UseCases;

import main.java.Entities.*;

import java.io.*;
import java.util.*;

/**
 * <h1>UserManager</h1>
 * Maps users' username to corresponding Attendee object in a hashmap.
 * @author Morgan Chang
 * @version phase2
 */
public class UserManager implements Serializable {

    /**
     * A Hashmap that maps a user's username to its corresponding Attendee object.
     */
    private final HashMap<String, Attendee> credentialsMap;  //need for organizer message controller

    /**
     * Construct a UserManager object.
     * Initialized with an empty credentialsMap.
     */
    public UserManager(){
        this.credentialsMap = new HashMap<>();
    }

    /**
     * Add a new user to the list of registered users in credentialsMap
     * @param username the username of the user
     * @param password the user's password
     * @param role the user's role
     * @return True if user was successfully registered, False otherwise
     */
    public boolean registerUser(String username, String password, String role) {
        if (!credentialsMap.containsKey(username)) {

            if(role.equalsIgnoreCase("attendee") || role.equalsIgnoreCase("organizer") ||
            role.equalsIgnoreCase("admin")) {
                credentialsMap.put(username, new Attendee(username, password, role));
                return true;
            }
            else if(role.equalsIgnoreCase("speaker")) {
                credentialsMap.put(username, new Speaker(username, password));
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user with following credentials can sign into the system
     * @param username user's username
     * @param password user's password
     * @return a string indicating whether this user can sign in: "usernameNotFound"/"wrongPassword"/"loggedIn"
     */
    public String loginUser(String username, String password) {
        Attendee res = credentialsMap.get(username);

        if (res == null) {
            return "usernameNotFound";
        } else if (!res.getPassword().equals(password)) {
            return "wrongPassword";
        }else {
            return "loggedIn";
        }
    }

    /**
     * Reset the password of the user with the given credentials
     * @param username user's username
     * @param newPassword user's new password
     * @return True if password was successfully changed, False if username was not located
     */
    public boolean resetPassword(String username, String newPassword) {
        Attendee user = credentialsMap.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            return true;
        } else
            return false;
    }

    /**
     * Get the role of the user with the given username
     * @param username user's username
     * @return the role of the user, null if user was not found
     */
    public String getUserRole(String username){
        Attendee res = credentialsMap.get(username);

        if (res != null) {
            return res.getRole();
        }
        else{
            return "null";
        }
    }

    /**
     * Check if given username is in credentialsMap
     * @param username the username to check
     * @return True if username is found, False otherwise
     */
    public boolean checkUsername(String username) {
        return this.credentialsMap.containsKey(username);
    }

    /**
     * Return the username of this Attendee.
     * @param user Attendee object of this user
     * @return username of this user
     */
    protected String getUsername(Attendee user){
        return user.getUsername();
    }

    /**
     * Add user2 to user1's friend list
     * @param user1 username of user1
     * @param user2 username of user2
     * @return true if user1 and user2 are valid users and user2 was added to user1's friend list
     */
    public boolean addFriend(String user1, String user2){
        Attendee at1 = credentialsMap.get(user1);

        if(at1 == null || credentialsMap.get(user2) == null)
            return false;

        return at1.addFriend(user2);
    }

    /**
     * Remove user2 from user1's friend list
     * @param user1 username of user1
     * @param user2 username of user2
     * @return true if both users are valid and user2 was removed from user1's friend list
     */
    public boolean removeFriend(String user1, String user2){
        Attendee at1 = credentialsMap.get(user1);

        if(at1 == null || credentialsMap.get(user2) == null)
            return false;

        return at1.removeFriend(user2);
    }

    /**
     * Return the number of friends in this user's friend list
     * @param username the username of the current user
     * @return the number of friends, -1 if username is not registered
     */
    public int getNumOfFriends(String username){
        Attendee user = credentialsMap.get(username);

        if(user != null)
            return user.getNumOfFriends();
        return -1;
    }

    /**
     * Sign up user of username for the event of event id
     * @param username of this user
     * @param id of the event to sign up for
     * @return true if user successfully signed up for the event
     */
    public boolean signUpForEvent(String username, UUID id){
        Attendee a = credentialsMap.get(username);
        if(a!=null)
            return a.registerForEvent(id);
        return false;
    }

    /**
     * Cancel event enrolment of event id for the user of username
     * @param username of this user
     * @param id of the event to cancel enrolment from
     * @return true if user is valid and was removed the event from their registered events list
     */
    public boolean cancelEnrollment(String username, UUID id){
        Attendee a = credentialsMap.get(username);
        if(a!=null)
            return a.cancelEnrollment(id);
        return false;
    }

    /**
     * Return the credentialMap of this UserManager
     * @return credentialMap of this UserManager
     */
    public HashMap<String, Attendee> getCredentialsMap() {
        return this.credentialsMap;
    }

    /**
     * Add a talk to this speaker's list of talks
     * @param username the username of the speaker
     * @param id the ID of the talk
     * @return true if the operation was successful
     */
    public boolean addTalk(String username, UUID id){
        if(!credentialsMap.get(username).getRole().equals("speaker"))
            return false;
        Speaker s = (Speaker) credentialsMap.get(username);
        return s.addTalk(id);
    }
}