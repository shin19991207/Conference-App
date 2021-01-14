package main.java.UseCases;

import main.java.Entities.Event;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;


/**
 * <h1>Building Manager</h1>
 * The Building Manager holds a map of Schedules keyed to their rooms.
 * There should likely be only one building manager per program.
 * @author Blake Gigiolio
 * @version Phase2
 */
public class BuildingManager implements Serializable{
    private final HashMap<String, Schedule2> building;
    private final String buildingName;

    /**
     * This constructor takes in the desired name for the building and creates an empty
     * map of schedules.
     * @param buildingName The desired name of the building.
     */
    public BuildingManager(String buildingName){
        this.buildingName = buildingName;
        this.building = new HashMap<>();
    }

    /**
     * Adds a new (empty) room to this building by creating a new schedule from the parameters.
     * @param name The desired name of the room.
     * @param startHour The desired starting hour for this schedule. (See Schedule for reference)
     * @param endHour The desired ending hour for this schedule. (See Schedule for reference)
     * @param roomCapacity maximum room capacity
     * @return True if the room was successfully added and false if it wasn't.
     */
    public boolean addRoom(String name, LocalTime startHour, LocalTime endHour, int roomCapacity){
        Schedule2 addition = new Schedule2(startHour, endHour, roomCapacity);
        return addRoom(name, addition);
    }

    /**
     * Adds a new (already existing) room to this building by receiving an existing schedule and adding it.
     * @param name The desired name of the room.
     * @param sched The schedule of the room that we want to add.
     * @return True if the room was successfully added and false if it wasn't.
     */
    public boolean addRoom(String name, Schedule2 sched){
        if(!building.containsKey(name)){
            building.put(name, sched);
            return true;
        }
        return false;
    }

    /**
     * Removes a room from this building.
     * @param name The name of the room to be removed.
     */
    public void removeRoom(String name){
        building.remove(name);
    }

    /**
     * Add an event to this building
     * @param e the event
     * @param em  EventManager object to retrieve info about events
     * @return True if the event was added successfully, False otherwise
     */
    protected boolean addEvent(Event e, EventManager em){
        String loc = e.getLocation();
        if(!building.containsKey(loc))
            return false;
        return building.get(loc).addEvent(e, em);
    }

    /**
     * Deletes an event with the following uuid from the schedule
     * @param id the UUID of the event to delete
     * @param capacity the capacity of the event to remove
     * @return true if an event with this uuid was deleted from the schedule
     */
    public boolean deleteEvent(UUID id, int capacity){
        for(String room:building.keySet()){
            Schedule2 sched = building.get(room);
            if(sched.removeEvent(id, capacity))
                return true;
        }
        return false;
    }

    /**
     * Returns the string format of this building. Each room takes up two lines in the format of:
     * [Room name]
     * schedule.toString();
     * @param em  EventManager object to retrieve info about events
     * @return Returns the string format of this building.
     */
    public String getToString(EventManager em){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("List of Rooms in ").append(this.buildingName).append(": \n");

        for (String room : building.keySet()) {
            toReturn.append("\n");
            toReturn.append("[").append(room).append("] \n");
            String schedule = building.get(room).getToString(em);
            toReturn.append(schedule);
        }
        return toReturn.toString();
    }

    /**
     * Check that all events in the building are events that are stored in EventManager
     * to prevent inconsistencies
     * @param em EventManager object
     * @return True iff all event IDs correspond to events stored in EventManager
     */
    public boolean verifyBuilding(EventManager em){
        for (String room : building.keySet()) {
            if(!building.get(room).verify(em)) return false;
        }
        return true;
    }

    /**
     * The implementation of Iterator for this building.
     */
    private class ScheduleIterator implements Iterator<Schedule2>{
        private int current = 0;
        private final ArrayList<String> keys = new ArrayList<>(building.keySet());

        @Override
        public boolean hasNext() {
            return (current < building.size());
        }

        @Override
        public Schedule2 next() {
            String room;
            Schedule2 res;
            try {
                room = keys.get(current);
                res = building.get(room);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return res;
        }
    }

    /*
    public ArrayList<String> eventsAttending(String username){
        ArrayList<String> events = new ArrayList<>();
        Iterator<Schedule2> iterator = new ScheduleIterator();
        while(iterator.hasNext()){
            Schedule2 sched = iterator.next();
            events.addAll(sched.eventsAttending(username));
        }
        return events;
    }

    public UUID getUUID(String title, LocalDateTime dt, String type, String room){
        Event temp;
        switch (type) {
            case "event":
                temp = new Event(title, room, dt, 0, 0);
                break;
            case "talk":
                temp = new Talk(title, room, dt, 0, 0);
                break;
            case "panelDiscussion":
                temp = new PanelDiscussion(title, room, dt, 0, 0);
                break;
            default:
                return null;
        }

        Schedule2 sched = building.get(room);

        if(sched == null) return null;

        return sched.getEventUUID(temp);
    }

    public boolean addAttendee(String username, UUID id){
        for(String room:building.keySet()){
            Schedule2 sched = building.get(room);
            if(sched.addAttendee(id, username))
                return true;
        }
        return false;
    }

    public boolean removeAttendee(String username, UUID id) {
        for (String room : building.keySet()) {
            Schedule2 sched = building.get(room);
            if (sched.removeAttendee(id, username))
                return true;
        }
        return false;
    }

    public boolean modifyCapacity(int newCap, UUID id){
        for(String room:building.keySet()){
            Schedule2 sched = building.get(room);
            if(sched.changeCapacity(id, newCap))
                return true;
        }
        return false;
    }

    public boolean changeSpeaker(UUID id, String username){
        for(String room:building.keySet()){
            Schedule2 sched = building.get(room);
            if(sched.setSpeaker(id, username))
                return true;
        }
        return false;


    }

    public String getEventAttendees(UUID id){
        StringBuilder printout = new StringBuilder("List of Attendees: ");
        ArrayList<String> attendees = new ArrayList<>();
        for(String room:building.keySet()){
            Schedule2 sched = building.get(room);
            if(sched.getEvent(id) != null){
                attendees = sched.getEvent(id).getAttendees();
            }
        }
        for (String a: attendees){
            printout.append(a).append(", ");
        }
        return printout.toString();
    }

    public String eventsOfSpeakerUsernameToString(String username) {
        StringBuilder eventsString = new StringBuilder();
        for (String room : building.keySet()) {
            Schedule2 schedule = building.get(room);
            ArrayList<Event> events = schedule.getEventOfSpeakerUsername(username);
            for (Event event : events) {
                eventsString.append(event.toString());
            }
        }
        return eventsString.toString();
    }
     */
}
