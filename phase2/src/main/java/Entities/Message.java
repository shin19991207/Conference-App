package main.java.Entities;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * <h1>Message</h1>
 * This Entity is responsible for creating and storing all pf the data associated with a message.
 * This should only be called by the Message Creator.
 * @author Zachary Werle
 * @version Phase 1
 */
public class Message implements Serializable {
    private final String content;
    private final String sender;
    private final LocalDateTime time_sent;
    private final String receiver;

    /**
     * This constructor creates a new instance of Message.
     * @param content The content of the Message.
     * @param sender The user who sent the Message.
     * @param receiver the user who receives the message
     */
    public Message(String content, String sender, String receiver){
        this.content = content;
        this.sender = sender;
        time_sent = LocalDateTime.now(ZoneId.of("America/Toronto"));
        this.receiver = receiver;
    }

    /**
     * This method returns the content of the Message.
     * @return The content of the Message as a String object.
     */
    public String getContent(){
        return content;
    }

    /**
     * This method returns the sender of the Message.
     * @return The sender of the Message as a String object.
     */
    public String getSender(){
        return sender;
    }

    /**
     * This method returns the time that the Message was sent.
     * @return The time the Message was sent as a LocalDateTime object.
     */
    public LocalDateTime getTime_sent() {
        return time_sent;
    }

    /**
     * This method returns the String representation of the Message.
     * @return A String representing the Message.
     */
    @Override
    public String toString(){
        String message = "";
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a");
        message += "From " + sender + ", to " + receiver + ", Time Sent: " + getTime_sent().format(f) + "\n";
        message += getContent();
        return message;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Message that = (Message) obj;
        if (!this.sender.equals(that.sender)) return false;
        if (!this.receiver.equals(that.receiver)) return false;
        return this.content.equals(that.content);
    }
}
