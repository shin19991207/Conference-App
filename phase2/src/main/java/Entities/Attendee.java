package main.java.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * <h1>Attendee</h1>
 * Represents an Attendee in the system.
 *
 * @author Sehajroop Singh Bath
 */
public class Attendee implements Serializable {

    /**
     * The username of current user.
     */
    private final String username;

    /**
     * The password of current user.
     */
    private String password;

    /**
     * A List that stores friend's username of current user.
     */
    private final ArrayList<String> friendList;

    /**
     * The role of current user.
     */
    private final String role;

    /**
     * The IDs of the events this user is registered in
     */
    private final ArrayList<UUID> eventsRegistered;

    /**
     * Construct an Attendee object when signed up.
     * Initialized with a username, a password, an empty list of friends, and an empty list of
     * inbox messages.
     * loggedIn is initialized to be false and role is initialized to be "attendee".
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @param role the role of the attendee (attendee, admin, organizer)
     */
    public Attendee(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
        this.friendList = new ArrayList<>();
        this.eventsRegistered = new ArrayList<>();
    }

    /**
     * Adds the given event ID to this user's list of registered events
     * @param id the event ID
     * @return true if the event ID was not already in the list
     */
    public boolean registerForEvent(UUID id) {
        if(!eventsRegistered.contains(id))
            return eventsRegistered.add(id);
        return false;
    }

    /**
     * Remove the given event ID from this user's list of registered events
     * @param id the ID of the event
     * @return true if the given ID was removed
     */
    public boolean cancelEnrollment(UUID id) {return eventsRegistered.remove(id);}

    /**
     * Return username.
     *
     * @return username of current user.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Return password
     *
     * @return password of current user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Change this user's password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return role
     *
     * @return role of current user.
     */
    public String getRole(){
        return role;
    }

    /**
     * Return number of friends in friendList of current user.
     *
     * @return number of friends in friendList.
     */
    public int getNumOfFriends() { return friendList.size(); }

    /**
     * Return friendList.
     *
     * @return friendList of current user.
     */
    public ArrayList<String> getFriendList() { return new ArrayList<>(friendList); }

    /**
     * Add the given username to this user's friend list
     * @param username the username to add
     * @return true if the username was added to the friend list
     */
    public boolean addFriend(String username){
        if(!friendList.contains(username))
            return friendList.add(username);
        return false;
    }

    /**
     * Removes the given username from this user's friend list
     * @param username the username to remove
     * @return true if the username was removed
     */
    public boolean removeFriend(String username){
        return friendList.remove(username);
    }


    // test to check class works as expected
/*
    public static void main(String[] args)
    {

        // Generate new user
        Entities.Attendee user1 = new Entities.Attendee("user1", "pass");
        Entities.Attendee user2 = new Entities.Attendee("user2", "pass");

        // Get and display the alphanumeric string
        System.out.println("username is " + user1.username + "\n" + "password is " + user1.password + "\n" +
                "userID is " + user1.userid + "\n" + "\n" +
                "username is " + user2.username + "\n" +
                "password is " + user2.password + "\n" +
                "userID is " + user2.userid + "\n" + "\n" +
                "user1 userID is " + user1.userid );
    }*/
}

