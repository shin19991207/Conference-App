package main.java.Gateways;

import main.java.Entities.AccessibilityOptions;
import main.java.UseCases.EventManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>AccessibilityOptions Gateway</h1>
 * This is a gateway class that can store updated request list into database and add and remove
 * requests from the requirements list.
 * @author Yuteng Gao
 * @version Phase2
 */

public class AccessibilityOptionsGateway implements Serializable{

    /**
     * The request list as a Hashmap consisted of the Sting username and the Arraylist of AccessibilityOptions objects.
     */
    protected HashMap<String, ArrayList<main.java.Entities.AccessibilityOptions>> requestList;
    /**
     * The Sting represents the path of the requireList file.
     */
    private final String requireListPath;


    /**
     * The constructor of the AccessibilityOptionsGateway object. Connect the object with the database by file path.
     */
    public AccessibilityOptionsGateway(){
        this.requireListPath = "phase2/src/main/java/DB/RequestList.ser";
        this.requestList = this.getRequestList();
    }


    /**
     * This method add request to the request list.
     * @param sender the username of the sender
     * @param request the type of the request
     */
    public void addRequest(String sender, main.java.Entities.AccessibilityOptions request){
        if (this.requestList.containsKey(sender)){
            this.requestList.get(sender).add(request);
        }else{
            this.requestList.put(sender, new ArrayList<AccessibilityOptions>());
            ArrayList<AccessibilityOptions> senderRequest = this.requestList.get(sender);
            senderRequest.add(request);
            this.requestList.replace(sender, senderRequest);
        }
    }


    /**
     * This method remove request in the request list.
     * @param sender the username of the sender of the request
     * @param request the name of request that will be removed
     */
    public void removeRequest(String sender, main.java.Entities.AccessibilityOptions request){
        ArrayList<AccessibilityOptions> senderRequest = this.requestList.get(sender);
        senderRequest.remove(request);
        this.requestList.replace(sender, senderRequest);
    }


    /**
     * This method set the status of the request
     * @param sender the username of sender
     * @param num number of the request
     * @param status the current status of request
     */
    public void setStatus(String sender, int num, String status){
        this.requestList.get(sender).get(num).setStatus(status);
    }


    /**
     * The getter for the request list.
     * @return the request list
     */
    public HashMap<String, ArrayList<AccessibilityOptions>> getRequestList(){
        HashMap<String, ArrayList<AccessibilityOptions>> requests = new HashMap<String, ArrayList<AccessibilityOptions>>();
        try {
            InputStream file = new FileInputStream(this.requireListPath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            requests = (HashMap<String, ArrayList<AccessibilityOptions>>) input.readObject();
            input.close();
        } catch (EOFException e) { //database file is empty
            clearFileContentsUtil();
        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in RequestList database. Clearing file...");
            clearFileContentsUtil();
        }
        catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from RequestList database file.");
            clearFileContentsUtil();
            e.printStackTrace();
        }
        if (requests == null){
            return new HashMap<String, ArrayList<AccessibilityOptions>>();
        }
        return requests;
    }


    /**
     * save the request list into database
     */
    public void saveRequestList(){
        clearFileContentsUtil();
        try {
            OutputStream file = new FileOutputStream(this.requireListPath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(this.requestList);
            output.close();
        } catch (IOException ex) {
            System.err.println("Could not save event data to database.");
            ex.printStackTrace();
        }
    }


    /**
     * clear the requests in the request list
     */
    protected void clearFileContentsUtil() {
        try {
            PrintWriter writer = new PrintWriter(this.requireListPath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unexpected error when accessing the " + "RequireList" + " database file.");
            e.printStackTrace();
        }
    }
}

