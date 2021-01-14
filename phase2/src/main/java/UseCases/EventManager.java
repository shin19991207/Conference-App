package main.java.UseCases;

import main.java.Entities.Event;
import main.java.Entities.PanelDiscussion;
import main.java.Entities.Talk;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * <h1>Event Manager</h1>
 * Stores all the events created
 * @author Konstantinos Papaspyridis
 * @version phase2
 */
public class EventManager implements Serializable {

    private final HashMap<UUID, Event> events = new HashMap<>();
    private final HashMap<UUID, Talk> talks = new HashMap<>();
    private final HashMap<UUID, PanelDiscussion> panelDiscussions = new HashMap<>();

    /**
     *  Attempts to create the new Event and returns a boolean based on the outcome.
     * @param title Title of the potential Event.
     * @param loc Location of the potential Event.
     * @param datetime The Date and TIme of the potential Event.
     * @param dur The total Duration in minutes of the potential Event.
     * @param eventCap The capacity of the potential Event.
     * @param type The type of the potential Event.
     * @param bm THe building the Event is going to be added to.
     * @return boolean
     */
    public boolean addEvent(String title, String loc, LocalDateTime datetime, int dur, int eventCap, String type, BuildingManager bm){
        EventFactory eventFactory = new EventFactory();
        Event e = eventFactory.getEvent(title, loc, datetime, dur, eventCap, type);
        if(e == null) return false;
        if(!bm.addEvent(e, this)) return false;

        switch (type) {
            case "event":
                events.put(e.getUuid(), e);
                break;

            case "talk":
                talks.put(e.getUuid(), (Talk) e);
                break;

            case "panelDiscussion":
                panelDiscussions.put(e.getUuid(), (PanelDiscussion) e);
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Return the desired Event
     * @param id The UUID associated with the Event.
     * @return Event
     */
    protected Event getEvent(UUID id){
        Event e = events.get(id);
        if(e!=null) return e;
        e = talks.get(id);
        if(e!=null) return e;
        e = panelDiscussions.get(id);
        return e;
    }

    /**
     * @return an array list containing all events happening in the building
     */
    public ArrayList<Event> getEvents(){
        ArrayList<Event> toReturn = new ArrayList<>();

        for(UUID id:events.keySet())
            toReturn.add(events.get(id));

        for(UUID id:talks.keySet())
            toReturn.add(talks.get(id));

        for(UUID id:panelDiscussions.keySet())
            toReturn.add(panelDiscussions.get(id));

        return toReturn;
    }

    /**
     * Remove the Event from the system.
     * @param eventId The UUID associated with the Event.
     * @return boolean
     */
    public boolean deleteEvent(UUID eventId){
        if(events.remove(eventId) != null) return true;
        if(talks.remove(eventId) != null) return true;
        return panelDiscussions.remove(eventId) != null;
    }

    /**
     * Set the Capacity of the Event to the new desired capacity.
     * @param eventId The UUID associated with the Event
     * @param newCap The new desired capacity of the Event, as an integer.
     * @return boolean
     */
    public boolean changeCapacity(UUID eventId, int newCap){
        Event e;

        e = events.get(eventId);
        if(e==null) e = talks.get(eventId);
        if(e==null) e = panelDiscussions.get(eventId);
        if(e==null) return false;

        e.setCapacity(newCap);
        return true;
    }

    /**
     * @param id ID of the event
     * @return the DateTime object of the event with this ID
     */
    public LocalDateTime getEventStartTime(UUID id){
        Event e = events.get(id);
        if(e != null) return e.getDatetime();
        e = talks.get(id);
        if(e != null) return e.getDatetime();
        e = panelDiscussions.get(id);
        if(e != null) return e.getDatetime();
        return null;
    }

    /**
     * @param id ID of the event
     * @return the duration of the event with this ID
     */
    public int getEventDuration(UUID id){
        Event e = events.get(id);
        if(e != null) return e.getDuration();
        e = talks.get(id);
        if(e != null) return e.getDuration();
        e = panelDiscussions.get(id);
        if(e != null) return e.getDuration();
        return -1;
    }

    /**
     * @param id ID of the event
     * @return the capacity of the event with this ID
     */
    public int getEventCapacity(UUID id){
        Event e = events.get(id);
        if(e != null) return e.getCapacity();
        e = talks.get(id);
        if(e != null) return e.getCapacity();
        e = panelDiscussions.get(id);
        if(e != null) return e.getCapacity();
        return -1;
    }

    /**
     * Returns the String representation of the title of the Event.
     * @param id The UUID associated with the Event.
     * @return String
     */
    public String getEventTitle(UUID id){
        Event e = events.get(id);
        if(e != null) return e.getTitle();
        e = talks.get(id);
        if(e != null) return e.getTitle();
        e = panelDiscussions.get(id);
        if(e != null) return e.getTitle();
        return null;
    }

    /**
     * @param id ID of the event
     * @return the room the event with this ID is taking place in
     */
    public String getEventLocation(UUID id){
        Event e = events.get(id);
        if(e != null) return e.getLocation();
        e = talks.get(id);
        if(e != null) return e.getLocation();
        e = panelDiscussions.get(id);
        if(e != null) return e.getLocation();
        return null;
    }

    /**
     * Returns an ArrayList of the titles of the Events the User is attending.
     * @param username The String that represents the User.
     * @return list of events this user is attending
     */
    public ArrayList<String> eventsAttending(String username){
        ArrayList<String> e = new ArrayList<>();

        for(UUID id : events.keySet()){
            if(events.get(id).getAttendees().contains(username))
                e.add(events.get(id).getTitle());
        }
        for(UUID id : talks.keySet()){
            if(talks.get(id).getAttendees().contains(username))
                e.add(talks.get(id).getTitle());
        }
        for(UUID id : panelDiscussions.keySet()){
            if(panelDiscussions.get(id).getAttendees().contains(username))
                e.add(panelDiscussions.get(id).getTitle());
        }
        return e;
    }

    /**
     * Adds the user to the desired Event's list of attendees..
     * @param id The UUID of the Event.
     * @param username The String that represents the user.
     * @return boolean
     */
    public boolean addAttendee(UUID id, String username){
        Event e = events.get(id);
        if(e != null) return e.addAttendees(username);

        e = talks.get(id);
        if(e != null) return e.addAttendees(username);

        e = panelDiscussions.get(id);
        if(e != null) return e.addAttendees(username);

        return false;
    }

    /**
     * Removes the User from the Event's list of attendees.
     * @param id The UUID of the EVent.
     * @param username The String representing the User.
     * @return boolean
     */
    public boolean removeAttendee(UUID id, String username){
        Event e = events.get(id);
        if(e != null) return e.removeAttendees(username);

        e = talks.get(id);
        if(e != null) return e.removeAttendees(username);

        e = panelDiscussions.get(id);
        if(e != null) return e.removeAttendees(username);

        return false;
    }

    /**
     * Sets the Speaker of the desired Talk.
     * @param id The UUID of the Talk.
     * @param username The String representing th Speaker.
     * @return boolean
     */
    public boolean setSpeaker(UUID id, String username){
        Talk talk = talks.get(id);

        if(talk != null){
            talk.setSpeaker(username);
            return true;
        }

        PanelDiscussion p = panelDiscussions.get(id);
        if(p != null){
            return p.addSpeaker(username);
        }
        return false;
    }

    /**
     * Returns String representation of the Speaker's associated Talks.
     * @param username The String representing the Speaker.
     * @return String
     */
    public String getEventsOfSpeakerUsernameToString(String username){
        StringBuilder eventsString = new StringBuilder();

        for(UUID id : talks.keySet()){
            if(talks.get(id).containSpeaker(username))
                eventsString.append(talks.get(id).toString());
        }
        for(UUID id : panelDiscussions.keySet()){
            if(panelDiscussions.get(id).containSpeaker(username))
                eventsString.append(panelDiscussions.get(id).toString());
        }
        return eventsString.toString();
    }

    /**
     * Returns an ArrayList of the Users who are attending the EVent.
     * @param id The UUId of the Event.
     * @return list with attendee usernames for those attending this event
     */
    public ArrayList<String> getAttendees(UUID id){
        Event e = events.get(id);
        if(e!=null) return e.getAttendees();
        e = talks.get(id);
        if(e!=null) return e.getAttendees();
        e = panelDiscussions.get(id);
        if(e!=null) return e.getAttendees();
        return null;
    }

    /**
     * Returns an ArrayList with the IDs of the events with no attendees
     * @return ArrayList with UUIDs
     */
    public ArrayList<UUID> getEventIDNoAttendees(){
        ArrayList<UUID> toReturn = new ArrayList<>();

        for(UUID id : events.keySet()){
            if(events.get(id).getAttendees().size() == 0)
                toReturn.add(id);
        }
        for(UUID id : talks.keySet()){
            if(talks.get(id).getAttendees().size() == 0)
                toReturn.add(id);
        }
        for(UUID id : panelDiscussions.keySet()){
            if(panelDiscussions.get(id).getAttendees().size() == 0)
                toReturn.add(id);
        }

        return toReturn;
    }

    /**
     * Check if event with given ID exists
     * @param id the UUID of the event
     * @return true if event exists
     */
    public boolean checkEvent(UUID id) {
        return events.get(id) != null || talks.get(id) != null || panelDiscussions.get(id) != null;
    }
}
