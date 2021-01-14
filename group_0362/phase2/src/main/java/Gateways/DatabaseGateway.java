package main.java.Gateways;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * <h1>DatabaseGateway</h1>
 * Abstract gateway class to provide a common interface and max code reuse
 * @version phase2
 * @author Konstantinos Papaspyridis
 */
public abstract class DatabaseGateway<T> {

    /**
     * Stores the path to the database file
     */
    private String dbPath;

    /**
     * Initializes path to database file
     * @param dbPath path to database file
     */
    public DatabaseGateway(String dbPath){
        this.dbPath = dbPath;
    }

    /**
     * Getter for database path
     * @return the database path
     */
    public String getDbPath(){
        return this.dbPath;
    }

    /**
     * Saves an object to the database
     * @param obj object to save
     */
    public abstract void save(T obj);

    /**
     * Reads an object from the database
     * @return the object read
     */
    public abstract T read();

    /**
     * Utility method to clear file contents if file contains corrupt data
     * @param db what kind of database is accessed (e.g. events, messages, etc.)
     */
    public void clearFileContentsUtil(String db) {
        try {
            PrintWriter writer = new PrintWriter(dbPath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unexpected error when accessing the " + db + " database file.");
            e.printStackTrace();
        }
    }
}
