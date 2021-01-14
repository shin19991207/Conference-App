package main.java.Controllers;

//import main.java.Entities.Organizer;
import main.java.Gateways.AccessibilityOptionsGateway;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This AccessibilityOptions controller is responsible for sending and deleting request by users, and
 * managing these requests by organizers.
 * @author Yuteng Gao
 * @version phase2
 */

public class AccessibilityOptionsController {

    /**
     * the request list as an AccessibilityOptionsGateway object
     */
    private final AccessibilityOptionsGateway requestList;
    /**
     * creator of the new request
     */
    private final main.java.UseCases.AccessibilityOptionsCreator requestCreator;

    /**
     * the constructor of the Accessibility Options controller
     */
    public AccessibilityOptionsController(){
        this.requestList = new AccessibilityOptionsGateway();
        this.requestCreator = new main.java.UseCases.AccessibilityOptionsCreator();
    }


    /**
     * This method allow the organizers to address the request in request list
     * @param sender the username of the request sender
     * @param num the number of the person's requests
     */
    public void addressRequest(String sender, int num){
        if (this.requestList.getRequestList().containsKey(sender)){
            this.requestList.setStatus(sender, num, "addressed");
        }
    }


    /**
     * This method allow the organizers to reject the request in request list
     * @param sender the username of the request sender
     * @param num the number of person's requests
     */
    public void rejectRequest(String sender, int num){
        if (this.requestList.getRequestList().containsKey(sender)){
            this.requestList.setStatus(sender, num, "rejected");
        }
    }


    /**
     * Print all accessibility requests to user
     * @return the complete request list
     */
    public ArrayList<String> getAllRequest(){
        ArrayList<String> allRequest = new ArrayList<>();
        HashMap<String, ArrayList<main.java.Entities.AccessibilityOptions>> list = this.requestList.getRequestList();
        for(String key : list.keySet()){
            String senderName = key;
            ArrayList<main.java.Entities.AccessibilityOptions> senderRequest = list.get(key);
            for(int i = 0; i<senderRequest.size(); i++){
                allRequest.add(key + " Request # " + i + " : " + senderRequest.get(i).getRequest() + " send at " +
                        senderRequest.get(i).getTime_sent().format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")) + " Satus: "
                        + senderRequest.get(i).getStatus());
            }
        }
        return allRequest;
    }


    /**
     * send the request from the sender to request list
     * @param sender the sender of the request
     * @param request the content of the request
     */
    public void sendRequest(String sender, String request){
        this.requestCreator.createRequest(sender, request);
        this.requestList.addRequest(sender, this.requestCreator.getRequest());
    }

    /**
     * delete the request from the request list
     */
    public void deleteRequest(){
        this.requestList.removeRequest("0", this.requestCreator.getRequest());
    }

    /**
     * save the requestList
     */
    public void save(){
        this.requestList.saveRequestList();
    }

}


