package main.java.UseCases;

import main.java.Entities.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <h1>Room Schedule</h1>
 * The Schedule class holds a map of events keyed to their corresponding times.
 * This is also equivalent to a 'room' as each schedule represents a room
 *
 * @author Blake Gigiolio
 * @version phase1
 */
public class Schedule implements Serializable {
    /**
     * Store event and time
     */
    private final HashMap<LocalDateTime, Event> scheduleMap = new HashMap<>();

    /**
     * time when last event finishes
     */
    private int endHour;

    /**
     * time when first event starts
     */
    private int startHour;

    private int roomCapacity;

    /**
     * The constructor requires a start hour and end hour for how long the room is available
     * @param startHour The first hour (inclusive) that an event can be scheduled
     * @param endHour The hour (exclusive) when an event can not be created in this room
     * @param roomCapacity the max room capacity
     */
    public Schedule(int startHour, int endHour, int roomCapacity){
        this.endHour = endHour; // When the room closes
        this.startHour = startHour; // When the room opens
        this.roomCapacity = roomCapacity;
    }

    /**
     * Add an event to this schedule of this room
     *
     * @param event This is the event to be added
     * @return true if the event was successfully added, false if the event couldn't be added
     */
    public boolean addEvent(Event event){
        LocalDateTime eventTime = event.getDatetime();
// TODO: these lines result in nullPointerException, need to modify!!
//        if ((scheduleMap.containsKey(eventTime) && event.getLocation().equals(scheduleMap.get(eventTime).getLocation()))
//                || eventTime.getHour() >= endHour || eventTime.getHour() < startHour)
//            return false;
        scheduleMap.put(eventTime, event);
        return true;
    }



    /**
     * Get an event object from its title
     *
     * @param event A string that is the name of an event.
     * @return Returns an event if the specified event is within this schedule, returns null if it isnt
     */
    public Event getEvent(String event){
        Iterator<Event> iterator = new EventIterator();
        while(iterator.hasNext()){
            Event current = iterator.next();
            if (current.getTitle().equals(event)){
                return current;
            }
        }
        return null;
    }

    /**
     * Removes an event from this schedule.
     *
     * @param event The event to be removed.
     * @return true if the event was removed, false if the event couldn't be removed
     */
    public boolean removeEvent(Event event){
        if (scheduleMap.containsKey(event.getDatetime())) {
            scheduleMap.remove(event.getDatetime());
            return true;
        }
        else
            return false;
    }

    /**
     * Get all events the user with <code>username</code> is attending
     * @param username user's username
     * @return list with the IDs of the events the user is attending
     */
    public ArrayList<String> eventsAttending(String username){
        ArrayList<String> events = new ArrayList<>();
        Iterator<Event> iterator = new EventIterator();
        while(iterator.hasNext()){
            Event current = iterator.next();
            if (current.getAttendees().contains(username)){
                events.add(current.getTitle());
            }
        }
        return events;
    }

    /**
     * Changes the start hour and end hour of this room
     *
     * @param startHour The first hour (inclusive) that an event can be scheduled
     * @param endHour The hour (exclusive) when an event can not be created in this room
     */
    public void editHours(int startHour, int endHour){
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * Returns the schedule in string format. Each event takes up a line in the format of:
     * [Title] at [Location] at [DateTime]
     *
     * @return returns the string format of the schedule
     */
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
//        try {
            for (LocalDateTime time : scheduleMap.keySet()){
                String event = scheduleMap.get(time).getTitle();
                String location = scheduleMap.get(time).getLocation();
                String construct = event + " at " + location + " at " + time + "\n";
                toReturn.append(construct); }
//            for (Map.Entry<LocalDateTime, Event> i : this.scheduleMap.entrySet()) {
//                if (i != null && i.getValue() != null && i.getKey() != null) {
//                    String key = i.getKey().toString();
//                    String event = i.getValue().getTitle();
//                    String location = i.getValue().getLocation();
//                    String construct = event + " at " + location + " at " + key + "\n";
//                    toReturn.append(construct);
//                }


        //catch(NullPointerException e){
         //   return "There are no events scheduled";
        //}
        return toReturn.toString();
    }

    /**
     * Gives the iterator for this schedule.
     *
     * @return Returns the iterator for this schedule.
     */
    public Iterator<Event> iterator() {
        return new EventIterator();
    }

    /**
     * This is the Schedule class' implementation of Iterator
     */
    private class EventIterator implements Iterator<Event>{
        /**
         * current position in set of datetimes in this schedule
         */
        private int current = 0;

        /**
         * The set of datetimes in this schedule
         */
        private final List<LocalDateTime> keys = new ArrayList<>(scheduleMap.keySet());

        @Override
        public boolean hasNext() {
            return (current < scheduleMap.size());
        }
        @Override
        public Event next() {
            LocalDateTime date;
            Event res;
            try {
                date = keys.get(current);
                res = scheduleMap.get(date);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return res;
        }
    }

    public HashMap<LocalDateTime, Event> getScheduleMap(){
        return this.scheduleMap;
    }
}
