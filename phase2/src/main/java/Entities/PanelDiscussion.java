package main.java.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PanelDiscussion extends Event {

    /**
     * the usernames of the speakers (multiple)
     */
    private final ArrayList<String> speakers = new ArrayList<>();

    /**
     * Constructor for <code>PanelDiscussion</code> class
     * @param title is the title of the event
     * @param location is the location where the event will be held.
     * @param datetime tells when the event is happening.
     * @param duration how long this event will be
     * @param capacity max event capacity
     */
    public PanelDiscussion(String title, String location,
                           LocalDateTime datetime, int duration, int capacity) {
        super(title, location, datetime, duration, capacity, "panelDiscussion");
    }

    /**
     * Add multiple speakers to the list of speakers
     * @param speakers the list of speaker usernames to add
     * @return true if at least one speaker was added
     */
    public boolean addSpeakers(ArrayList<String> speakers) {
        ArrayList<String> toAdd = new ArrayList<>();

        for(String s: speakers){
            if(!this.speakers.contains(s))
                toAdd.add(s);
        }

        return speakers.addAll(toAdd);
    }

    /**
     * Add a single speaker to the list of speakers
     * @param username the speaker's username
     * @return true if the speaker was added
     */
    public boolean addSpeaker(String username){
        if(!speakers.contains(username)){
            speakers.add(username);
            return true;
        }
        return false;
    }

    @Override
    public String getSpeaker(){
        if(speakers.size()==0) return null;
        StringBuilder toRet = new StringBuilder();
        int i;

        for(i = 0; i<speakers.size()-1; ++i)
            toRet.append(speakers.get(i)).append(", ");

        toRet.append(speakers.get(i));

        return toRet.toString();
    }

    /**
     * Remove multiple speakers from the list of speakers
     * @param speakers the list of speaker usernames to remove
     * @return true if at least one speaker was removed
     */
    public boolean removeSpeakers(ArrayList<String> speakers) {return this.speakers.removeAll(speakers);}

    /**
     * @param username the speaker's username
     * @return true if this panel discussion contains the given speaker username in its list of speakers
     */
    @Override
    public boolean containSpeaker(String username) { return this.speakers.contains(username);}
}
