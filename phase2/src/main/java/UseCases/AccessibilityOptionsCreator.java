package main.java.UseCases;

import main.java.Entities.AccessibilityOptions;

import java.time.LocalDateTime;

/**
 * <h1>AccessibilityOptions Creator</h1>
 * This use case creates the accessibility requirement of the user.
 * @version phase2
 * @author Yuteng Gao
 */

public class AccessibilityOptionsCreator {

    /**
     * The accessibility option entity to be created or changed.
     */
    protected AccessibilityOptions accessibilityOption;

    /**
     * The controller of AccessibilityOptionsCreator.
     * The user can only choose one of "food", "transportation" and "vision" as their accessibility requirement.
     * If other requests are provided, it will show an error message.
     */
    public AccessibilityOptionsCreator(){
        this.accessibilityOption = new AccessibilityOptions("0", "0");
    }

    public void createRequest(String sender, String request){
        if (request.equals("food") || request.equals("transportation") || request.equals("vision")){
            this.accessibilityOption = new AccessibilityOptions(request, sender);
        }
        else {
            System.err.println("This is not included in our accessibility options.");
        }
    }

    /**
     * Getter for the send time of the accessibility request.
     * @return the time when the request was sent
     */
    public LocalDateTime getTimeSent(){
        return accessibilityOption.getTime_sent();
    }

    /**
     * Getter for the request object.
     * @return return the AccessibilityOptions entity
     */
    public AccessibilityOptions getRequest() {
        return accessibilityOption;
    }

}
