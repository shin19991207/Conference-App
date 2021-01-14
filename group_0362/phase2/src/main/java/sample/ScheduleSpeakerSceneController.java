package main.java.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.Gateways.BuildingGateway;
import main.java.Gateways.EventGateway;
import main.java.Gateways.UserLoginGateway;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;
import main.java.UseCases.UserManager;

import java.util.UUID;

public class ScheduleSpeakerSceneController {

    /**
     * Unique ID of an event.
     */
    private UUID id;
    /**
     * Username of the speaker.
     */
    private String username;
    /**
     * UserManager object.
     */
    private UserManager userManager;
    /**
     * UserLoginGateway object that stores user account information
     */
    private UserLoginGateway userLoginGateway;
    /**
     * EventManager object.
     */
    private EventManager eventManager;
    /**
     * EventGateway object that stores all event information.
     */
    private EventGateway eventGateway;
    /**
     * Text field to put the speaker username.
     */
    @FXML
    private TextField speakerNameField;
    /**
     * Text field to put event ID
     */
    @FXML
    private TextField eventUUID;
    /**
     * A text to display when there is an error.
     */
    @FXML
    private Text errorText;
    /**
     * The button that is clicked to schedule the speaker.
     */
    @FXML
    private Button actionButton;

    /**
     * When the button is pressed, the method checks if the input information are valid to assign a speaker to the
     * specified event
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void actionButtonPressed(ActionEvent event) {
        String idString;
        if (eventUUID.getText() == null || speakerNameField.getText() == null){
            errorText.setText("Please fill in all of the fields.");
            return;
        }
        username = speakerNameField.getText();
        if (!userManager.checkUsername(username)){
            errorText.setText("Not a valid user");
            return;
        }
        if (!userManager.getUserRole(username).equalsIgnoreCase("speaker")){
            errorText.setText("This user is not a speaker");
            return;
        }
        idString = eventUUID.getText();
        try{
            this.id = UUID.fromString(idString);
        }catch (IllegalArgumentException e){
            errorText.setText("Invalid UUID format");
            return;
        }
        if(!eventManager.checkEvent(id)) {
            errorText.setText("Invalid event ID");
            return;
        }
        if (!eventManager.setSpeaker(id, username)){
            errorText.setText("Invalid talk/panel discussion ID or speaker already added");
            return;
        }
        if(!userManager.addTalk(username, id)){
            errorText.setText("Speaker already added");
            return;
        }
        errorText.setText("Speaker set!");
        this.eventGateway.save(eventManager);
        this.userLoginGateway.save(userManager);
    }
    /**
     * Setter method for the attribute eventManager.
     * @param eventManager is an instance of the EventManager class.
     */
    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }
    /**
     * Setter method for the attribute eventGateway.
     * @param eventGateway is an instance of the EventGateway class.
     */
    public void setEventGateway(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    /**
     * A setter to change the value of the attribute userManager.
     * @param userManager is an instance of UserManager class.
     */
    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }
    /**
     * A setter to change the value of the attribute userLoginGateway
     * @param userLoginGateway is an instance of UserLoginGateway class
     */
    public void setUserLoginGateway(UserLoginGateway userLoginGateway){
        this.userLoginGateway = userLoginGateway;
    }
    /**
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @FXML
    void initialize() {
        assert speakerNameField != null : "fx:id=\"speakerNameField\" was not injected: check your FXML file 'scheduleSpeakerScene.fxml'.";
        assert eventUUID != null : "fx:id=\"eventUUID\" was not injected: check your FXML file 'scheduleSpeakerScene.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'scheduleSpeakerScene.fxml'.";
        assert actionButton != null : "fx:id=\"actionButton\" was not injected: check your FXML file 'scheduleSpeakerScene.fxml'.";

    }
}
