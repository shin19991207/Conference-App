package main.java.Gateways;

import main.java.Entities.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Message Gateway</h1>
 * MessageGateway is a gateway class that interacts with outside file to store messages as inbox and outbox of users.
 * @author Qi Zheng
 * @version Phase2
 */
public class MessageGateway {
    protected HashMap<String, ArrayList<Message>> inbox; //received messages

    protected HashMap<String, ArrayList<Message>> outbox; //sent messages

    private final String inboxPath;
    private final String outboxPath;

    /**
     * Instantiated without any arguments.
     * Creates an instance of MessageGateway to access the file named Message.ser outside the program.
     */
    public MessageGateway(){
        this.inboxPath = "phase2/src/main/java/DB/InboxMessage.ser";
        this.outboxPath = "phase2/src/main/java/DB/OutboxMessage.ser";
        this.inbox = getInbox();
        this.outbox = getOutbox();
    }

    /**
     * Takes sender, receiver and message as parameters.
     * Adds the message to the inbox of the receiver and outbox of the sender.
     * Creates adds a new key to the hashmap if there is no existing sender or receiver.
     * @param sender is the user that sent the message
     * @param receiver is the user that received the message
     * @param message is the object that is created by sender, sent to receiver.
     */
    public void addNewMessage(String sender, String receiver, Message message){
        if (!this.inbox.containsKey(receiver)){
            this.inbox.put(receiver, new ArrayList<>());
        }
        ArrayList<Message> receiverInbox = this.inbox.get(receiver);
        receiverInbox.add(message);
        this.inbox.replace(receiver, receiverInbox);

        if (!this.outbox.containsKey(sender)){
            this.outbox.put(sender, new ArrayList<>());
        }
        ArrayList<Message> senderOutbox = this.outbox.get(sender);
        senderOutbox.add(message);
        this.outbox.replace(sender, senderOutbox);
    }

    /**
     * Takes sender, receiver and message as parameters.
     * Removes the message from both the inbox of the receiver and outbox of the sender.
     * Does not do anything if the message does not exist in both inbox and outbox.
     * @param sender is the user that sent the message
     * @param receiver is the user that received the message
     * @param message is the object that is created by sender, sent to receiver.
     * @return true if message was removed
     */
    public boolean removeMessage(String sender, String receiver, Message message){
        if(!this.outbox.containsKey(sender) || !this.inbox.containsKey(receiver)) return false;
        boolean b1 = false, b2 = false;

        ArrayList<Message> senderOutbox = this.outbox.get(sender);

        for(Message m:senderOutbox){
            if(m.equals(message)) {
                senderOutbox.remove(m);
                this.outbox.replace(sender, senderOutbox);
                b1 = true;
                break;
            }
        }

        ArrayList<Message> receiverInbox = this.inbox.get(receiver);

        for(Message m:receiverInbox) {
            if (m.equals(message)) {
                receiverInbox.remove(message);
                this.inbox.replace(receiver, receiverInbox);
                b2 = true;
                break;
            }
        }

        return b1 & b2;
    }

    /**
     * Retrieve and return the inbox hashmap that is stored in the file located outside the program.
     * Takes no argument.
     * @return inbox
     */
    public HashMap<String, ArrayList<Message>> getInbox() {
        try {
            InputStream file = new FileInputStream(this.inboxPath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            inbox = (HashMap<String, ArrayList<Message>>) input.readObject();
            input.close();
        }  catch (EOFException e) { //database file is empty
            inbox = new HashMap<>();
        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in inbox database. Clearing file...");
            clearFileContentsUtil(inboxPath);
            inbox = new HashMap<>();
        } catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from inbox database file.");
            e.printStackTrace();
            inbox = new HashMap<>();
        }
        return this.inbox;
    }

    /**
     * Retrieve and return the outbox hashmap that is stored in the file located outside the program.
     * Takes no argument.
     * @return outbox
     */
    public HashMap<String, ArrayList<Message>> getOutbox(){
        try{
            InputStream file = new FileInputStream(this.outboxPath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            this.outbox = (HashMap<String, ArrayList<Message>>) input.readObject();
            input.close();
        }catch (EOFException e) { //database file is empty
            outbox = new HashMap<>();
        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in outbox database. Clearing file...");
            clearFileContentsUtil(outboxPath);
            outbox = new HashMap<>();
        } catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from outbox database file.");
            e.printStackTrace();
            outbox = new HashMap<>();
        }
        return this.outbox;
    }

    /**
     * Saves the current inbox hashmap, that is stored in the inbox attribute, into the file located at inboxPath.
     */
    public void setInbox() {
        try{
            OutputStream file = new FileOutputStream(this.inboxPath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(this.inbox);
            output.close();

        }
        catch (IOException e){
            System.err.println("Could not save inbox data to database.");
            e.printStackTrace();
        }
    }

    /**
     * Saves the current outbox hashmap, that is stored in the outbox attribute, into the file located at outboxPath.
     */
    public void setOutbox() {
        try{
            OutputStream file = new FileOutputStream(this.outboxPath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(outbox);
            output.close();

        }catch (IOException e){
            System.err.println("Could not save outbox data to database.");
            e.printStackTrace();
        }
    }

    /**
     * Utility method to clear file contents if file contains corrupt data
     * @param dbPath path to database file
     */
    private void clearFileContentsUtil(String dbPath) {
        try {
            PrintWriter writer = new PrintWriter(dbPath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unexpected error when accessing the user database file.");
            e.printStackTrace();
        }
    }
}
