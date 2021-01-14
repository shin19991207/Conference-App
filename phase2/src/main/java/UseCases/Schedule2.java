package main.java.UseCases;

import main.java.Entities.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;


/**
 * <h1>Room Schedule</h1>
 * The Schedule class holds a list of events in chronologically ascending order based on starting time.
 * This is also equivalent to a 'room' as each schedule represents a room.
 *
 * @author Konstantinos Papaspyridis
 * @version phase2
 */
public class Schedule2 implements Serializable {
    /**
     * The list holding the IDs of the events happening in this room
     */
    private final ArrayList<UUID> schedule;

    /**
     * time when first event starts
     */
    private LocalTime startTime;

    /**
     * time when last event finishes
     */
    private LocalTime endTime;

    /**
     * This room's capacity
     */
    private int roomCapacity;

    /**
     * The sum of the capacities of the events in this room
     */
    private int spaceUsed;

    /**
     * Constructor for Schedule2
     *
     * @param startTime time room opens
     * @param endTime   time room closes
     * @param roomCap   room capacity
     */
    public Schedule2(LocalTime startTime, LocalTime endTime, int roomCap) {
        this.schedule = new ArrayList<>();
        this.startTime = startTime; // When the room closes
        this.endTime = endTime; // When the room opens
        this.roomCapacity = roomCap;
        spaceUsed = 0;
    }

    /**
     * Add an event to this schedule of this room
     *
     * @param e This is the event to be added
     * @param em EventManager object to retrieve info about events
     * @return true if the event was successfully added, false if the event couldn't be added
     */
    public boolean addEvent(Event e, EventManager em) {
        LocalDateTime endTime = e.getDatetime().plusMinutes(e.getDuration());

        if (endTime.toLocalTime().isAfter(this.endTime) || e.getDatetime().toLocalTime().isBefore(this.startTime)
                || e.getCapacity() + spaceUsed > this.roomCapacity)
            return false;

        if (schedule.size() == 0) {
            schedule.add(e.getUuid());
            spaceUsed += e.getCapacity();
            return true;
        }

        int index = getIndex(0, schedule.size() - 1, e.getDatetime(), em);

        if (index == -1)
            return false;
        else if(index == 0){
            Event ev2 = em.getEvent(schedule.get(index));
            if (!endTime.isAfter(ev2.getDatetime())){
                schedule.add(index, e.getUuid());
                spaceUsed += e.getCapacity();
                return true;
            }
            return false;
        }else if(index == schedule.size()){
            Event ev1 = em.getEvent(schedule.get(index-1));
            if(!ev1.getDatetime().plusMinutes(ev1.getDuration()).isAfter(e.getDatetime())) {
                schedule.add(index, e.getUuid());
                spaceUsed += e.getCapacity();
                return true;
            }
            return false;
        }

        Event ev1 = em.getEvent(schedule.get(index-1));
        Event ev2 = em.getEvent(schedule.get(index));   //the event starting right after the event we wanna add

        if(ev1.getDatetime().plusMinutes(ev1.getDuration()).isAfter(e.getDatetime()))
            return false;

        if (endTime.isAfter(ev2.getDatetime()))
            return false;

        schedule.add(index, e.getUuid());
        spaceUsed += e.getCapacity();
        return true;
    }

    /**
     * Remove event with specific uuid
     * @param id the uuid of the event to remove
     * @param capacity the capacity of the room to remove
     * @return true if event was successfully removed
     */
    public boolean removeEvent(UUID id, int capacity){
        if(schedule.remove(id)) {
            spaceUsed -= capacity;
            return true;
        }
        return false;
    }

    /**
     * Changes the start hour and end hour of this room
     *
     * @param startTime The time that the first event can start
     * @param endTime   The time the last event should finish by
     */
    public void editHours(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Get room capacity
     *
     * @return room capacity
     */
    public int getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * Change max room capacity
     *
     * @param roomCapacity new room capacity
     */
    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    /**
     * Returns a string representation of the schedule
     * @param em EventManager object
     * @return a String with the string representation of the schedule
     */
    public String getToString(EventManager em){
        StringBuilder toReturn = new StringBuilder();

        for(UUID id : schedule){
            Event e = em.getEvent(id);
            if(e == null)
                continue;
            toReturn.append("\nID: ").append(id.toString()).append(" \n");
            toReturn.append(e.getTitle()).append(" at ").append(e.getLocation());
            toReturn.append(" on ").append(e.getDatetime().toString()).append("\n");
        }
        return toReturn.toString();
    }

    /**
     * Binary search to get the index of the event that starts right after the event we want to add
     *
     * @param l left index
     * @param r right index
     * @param k date and time of event we want to add
     * @param em  EventManager object to retrieve info about events
     * @return index of event starting after our event or -1 if event with same starting time already exists
     */
    private int getIndex(int l, int r, LocalDateTime k, EventManager em) {
        int mid;

        while (l <= r) {
            mid = (l + r) >>> 1;
            LocalDateTime dt = em.getEvent(schedule.get(mid)).getDatetime();
            if (dt.isEqual(k))
                return -1;
            else if (dt.isBefore(k))
                l = mid + 1;
            else
                r = mid - 1;
        }
        return l;
    }

    /**
     * Check that all events in the schedule are events that are stored in EventManager
     * to prevent inconsistencies
     * @param em EventManager object
     * @return True iff all event IDs correspond to events stored in EventManager
     */
    public boolean verify(EventManager em) {
        for(UUID id : schedule) {
            if(em.getEvent(id) == null) return false;
        }
        return true;
    }

    /*
    /**
     * Get all events the user with <code>username</code> is attending
     *
     * @param username user's username
     * @return list with the IDs of the events the user is attending

    public ArrayList<String> eventsAttending(String username) {
        ArrayList<String> events = new ArrayList<>();

        for (Event e : this.schedule) {
            if (e.getAttendees().contains(username))
                events.add(e.getTitle());
        }

        return events;
    }

    public UUID getEventUUID(Event x){
        for(Event e:schedule){
            if(e.equals(x))
                return e.getUUID();
        }
        return null;
    }

    public boolean addAttendee(UUID id, String username){
        for(Event e:schedule){
            if(e.getUUID().equals(id)) {
                if(e.getCapacity() <= e.getAttendees().size() || e.getAttendees().contains(username))
                    return false;
                e.addAttendees(username);
                return true;
            }
        }
        return false;
    }

    public boolean removeAttendee(UUID id, String username){
        for(Event e:schedule){
            if(e.getUUID().equals(id)) {
                e.removeAttendees(username);
                return true;
            }
        }
        return false;
    }

    public boolean setSpeaker(UUID id, String username){
        for(Event e:schedule){
            if(e.getUUID().equals(id)) {
                Talk t = (Talk) e;
                t.setSpeaker(username);
                return true;
            }
        }
        return false;
    }

    public boolean changeCapacity(UUID id, int newCap){
        for(Event e:schedule){
            if(e.getUUID().equals(id)) {
                e.setCapacity(newCap);
                return true;
            }
        }
        return false;
    }



    public ArrayList<Event> getEventOfSpeakerUsername(String username) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : this.schedule) {
            if (event.getType().equals("talk")) {
                Talk talk = (Talk) event;
                if (talk.getSpeaker().equals(username)) {
                    events.add(talk);
                }
            } else if (event.getType().equals("panelDiscussion")) {
                PanelDiscussion panelDiscussion = (PanelDiscussion) event;
                if (panelDiscussion.containSpeaker(username)) {
                    events.add(panelDiscussion);
                }
            }
        } return events;
    }
    */

}

//----------Testing----------
/*
class Test{
    public static void main(String[] args) {
        Schedule2 s = new Schedule2(LocalTime.of(9,0), LocalTime.of(17,0), 100);

        //normal add-should return true
        LocalDateTime t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 12, 0);
        System.out.println(s.addEvent(new Event("test1", "BA12", t1, 60, 50)));

        //normal add-should return true
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 13, 0);
        System.out.println(s.addEvent(new Event("test2", "BA13", t1, 60, 50)));

        //adding event starting at the same time as already existing event-should return false
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 12, 0);
        System.out.println(s.addEvent(new Event("test3", "BA12", t1, 60, 50)));

        //Adding event that begins at correct time, but
        // (1) ends after next event has started (return false)
        // (2) ends before next event starts (return true)
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 11, 0);
        System.out.println(s.addEvent(new Event("test4", "BA12", t1, 70, 50)));
        System.out.println(s.addEvent(new Event("test5", "BA12", t1, 60, 50)));

        //normal add-should return true
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 14, 30);
        System.out.println(s.addEvent(new Event("test6", "BA12", t1, 60, 50)));

        //add event with valid starting time between 2 events but invalid ending time (return false)
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 14, 0);
        System.out.println(s.addEvent(new Event("test6", "BA12", t1, 60, 50)));

        //add event that ends after schedule permits (return false)
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 16, 0);
        System.out.println(s.addEvent(new Event("test8", "BA12", t1, 80, 50)));

        //add event that starts after schedule permits (return false)
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 18, 0);
        System.out.println(s.addEvent(new Event("test9", "BA12", t1, 80, 50)));

        //add event that starts before schedule permits (return false)
        t1 = LocalDateTime.of(2020, Month.JANUARY, 1, 8, 0);
        System.out.println(s.addEvent(new Event("test10", "BA12", t1, 80, 50)));

        //expect 4 events to be printed
        System.out.println(s);

        System.out.println(s.getEvent("test5").getDatetime());

        System.out.println(s.eventsAttending("user1").size()==0);

        t1 = LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 0);
        Event e = new Event("A", "BA2771", t1, 30, 50);
        e.addAttendees("user1");
        s.addEvent(e);

        System.out.println(s.eventsAttending("user1"));

        System.out.println(s);

        //should be true
        System.out.println(s.removeEvent(e));

        //should be false lmao
        System.out.println(s.removeEvent(e));
    }
}
*/