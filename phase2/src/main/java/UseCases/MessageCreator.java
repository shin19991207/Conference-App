package main.java.UseCases;

import main.java.Entities.*;

/**
 * <h1>Message Creator</h1>
 * This use case creates the message of the item at hand and acts as a bridge between the Message entity and
 * message controller
 * @author Utkarsh Mali
 */

public class MessageCreator {
    /**
     * The username of sending user as a string.
     */
    protected String receiver;
    /**
     * The message entity to be created or changed.
     */
    protected Message message;
    /**
     * To be called by Message Controller, use case creates a Message entity. This use case is saved as an object
     * inside Message Controller which then changes and manipulates parameters surrounding the message.
     *
     * @param toBeMessage the message as a string to be created into a message entity.
     * @param receiver the username of the receiving user.
     * @param senderUserid the username of the person sending the message.
     */
    public MessageCreator(String toBeMessage, String receiver, String senderUserid){
        message = new Message(toBeMessage, senderUserid, receiver);
        this.receiver = receiver;
    }
    /**
     * Gets the time sent of the specific message
     * @return time sent of the message
     */
    public java.time.LocalDateTime getTimeSent(){
        return message.getTime_sent();
    }
    /**
     * Gets the contents of the Message Entity
     * @return message entity of use case
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns the String representing the receiver of the Message.
     * @return String
     */
    public String getReceiver(){return this.receiver;}
}
