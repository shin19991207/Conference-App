package main.java.Gateways;

import main.java.UseCases.EventManager;

import java.io.*;

/**
 * <h1>EventGateway</h1>
 * This Gateway class is responsible for retrieving and storing a copy of the event manager in the Events.ser file.
 * @author Zachary Werle
 * @version Phase 2
 */
public class EventGateway extends DatabaseGateway<EventManager>{

    public EventGateway() { super("phase2/src/main/java/DB/Events.ser"); }

    /**
     * This method retrieves a BuildingManager object from Events.ser.
     * @return A BuildingManager object.
     */
    @Override
    public EventManager read() {
        EventManager events;
        try {
            InputStream file = new FileInputStream(getDbPath());
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            events = (EventManager) input.readObject();
            input.close();
        } catch (EOFException e) { //database file is empty
            events = new EventManager();
            new BuildingGateway().clearFileContentsUtil("building");
        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in event database. Clearing file...");
            clearFileContentsUtil("event");
            new BuildingGateway().clearFileContentsUtil("building");
            events = new EventManager();
        }
        catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from events database file.");
            new BuildingGateway().clearFileContentsUtil("building");
            e.printStackTrace();
            events = new EventManager();
        }
        return events;
    }

    /**
     * This method stores a BuildingManager object in Events.ser.
     * @param obj A BuildingManager object.
     */
    @Override
    public void save(EventManager obj) {
        clearFileContentsUtil("event");
        try {
            OutputStream file = new FileOutputStream(getDbPath());
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(obj);
            output.close();
        } catch (IOException ex) {
            System.err.println("Could not save event data to database.");
            ex.printStackTrace();
        }
    }
}
