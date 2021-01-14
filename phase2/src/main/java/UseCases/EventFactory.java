package main.java.UseCases;

import main.java.Entities.Event;
import main.java.Entities.PanelDiscussion;
import main.java.Entities.Talk;

import java.time.LocalDateTime;

/**
 * Class for creating an event of a particular type
 */
public class EventFactory {
    /**
     * Returns an Event with the following attributes. Returned object can be of type Event/Talk/PanelDiscussion
     * @param title title of event
     * @param loc name of room event is taking place in
     * @param datetime date and time the event starts
     * @param dur duration of event in minutes
     * @param eventCap maximum event capacity
     * @param type type of event
     * @return a new Event/Talk/PanelDiscussion object with the specified attributes
     */
    public Event getEvent(String title, String loc, LocalDateTime datetime, int dur, int eventCap, String type){
        if(type == null) return null;

        switch(type){
            case "event":
                return new Event(title, loc, datetime, dur, eventCap);
            case "talk":
                return new Talk(title, loc, datetime, dur, eventCap);
            case "panelDiscussion":
                return new PanelDiscussion(title, loc, datetime, dur, eventCap);
        }
        return null;
    }
}
