package main.java.Gateways;

import main.java.UseCases.BuildingManager;

import java.io.*;

/**
 * <h1>BuildingGateway</h1>
 * This Gateway class is responsible for retrieving and storing a copy of the building in the Events.ser file.
 * @author Konstantinos Papaspyridis
 * @version Phase 2
 */
public class BuildingGateway extends DatabaseGateway<BuildingManager>{

    public BuildingGateway() { super("phase2/src/main/java/DB/Building.ser"); }

    /**
     * This method retrieves a BuildingManager object from Building.ser.
     * @return A BuildingManager object.
     */
    @Override
    public BuildingManager read() {
        BuildingManager events;
        try {
            InputStream file = new FileInputStream(getDbPath());
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            events = (BuildingManager) input.readObject();
            input.close();
        } catch (EOFException e) { //database file is empty
            events = new BuildingManager("Building");
            new EventGateway().clearFileContentsUtil("events"); //make sure that the events database is also empty to prevent errors

        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in event database. Clearing file...");
            clearFileContentsUtil("building");
            new EventGateway().clearFileContentsUtil("events");
            events = new BuildingManager("Building");
        } catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from events database file.");
            new EventGateway().clearFileContentsUtil("events");
            e.printStackTrace();
            events = new BuildingManager("Building");
        }
        return events;
    }

    /**
     * This method stores a BuildingManager object in Building.ser.
     * @param obj A BuildingManager object.
     */
    @Override
    public void save(BuildingManager obj) {
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

    /**
     * Construct the text file Schedule.txt from database Building.ser.
     * @param scheduleString the schedule in string format
     */
    public void constructScheduleTxt(String scheduleString) {
        FileWriter scheduleWriter;
        try{
            scheduleWriter = new FileWriter("phase2/src/main/java/DB/Schedule.txt");
            scheduleWriter.write(scheduleString);
            scheduleWriter.close();
        }catch(IOException e){
            System.err.println("IO error when constructing schedule text file :(");
        }
    }
}