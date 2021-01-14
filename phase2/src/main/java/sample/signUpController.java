package main.java.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import main.java.Gateways.BuildingGateway;
import main.java.Gateways.EventGateway;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;
import main.java.UseCases.UserManager;

import java.util.UUID;

public class signUpController {

    /**
     * A BuildingManager object of the conference.
     */
    private BuildingManager building;
    /**
     * EventGateway object that stores the events.
     */
    private EventGateway eventGateway;
    /**
     * BuildingGateway object that stores the building information.
     */
    private BuildingGateway buildingGateway;
    /**
     * Username of the account that logged in.
     */
    private String username;
    /**
     * UserManager object.
     */
    private UserManager userManager;
    /**
     * EventManager object.
     */
    private EventManager eventManager;

    /**
     * A boolean of the pop up window to specify if the action is to sign up for or opt out from an event.
     */
    private boolean signup;
    /**
     * Unique ID of an event.
     */
    @FXML
    private TextField EventID;
    /**
     * A text.
     */
    @FXML
    private Text printSchedule;
    /**
     * A text.
     */
    @FXML
    private Text errorMessageText;
    /**
     * The option that displays a pane to input information that the user would ike to sign up for.
     */
    @FXML
    private RadioButton signUpButton;
    /**
     * ToggleGroup of radio buttons of either to sign up for or leave an event.
     */
    @FXML
    private ToggleGroup eventChoice;
    /**
     * The option that displays a pane to input information that the user would like to leave.
     */
    @FXML
    private RadioButton leaveEventButton;
    /**
     * The button that is clicked to take an action of signing up for or leave an event.
     */
    @FXML
    private Button actionButton;


    /**
     * When the button is pressed, the method will sign the user up for the specified event, or rejects it depending
     * on the input information
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void actionButtonPressed(ActionEvent event) {
        UUID id;
        try{
            id = UUID.fromString(EventID.getText());
        }catch(IllegalArgumentException e){
            errorMessageText.setText("Invalid UUID format");
            errorMessageText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            return;
        }
        if (signup){
            if (userManager.signUpForEvent(username, id) & eventManager.addAttendee(id, username)){
                errorMessageText.setText("You are now attending ..."); // add to
                errorMessageText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            }else{
                errorMessageText.setText("Event with UUID: " + id.toString() + " does not exist or is full.");
                errorMessageText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            }
        }else{
            if (userManager.cancelEnrollment(username, id) & eventManager.removeAttendee(id, username)){
                errorMessageText.setText("You have successfully left ...");
                errorMessageText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            }else{
                errorMessageText.setText("Event with UUID: " + id.toString() +
                        " does not exist or you are not attending");
                errorMessageText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

            }
        }
        this.buildingGateway.save(this.building);
        this.eventGateway.save(this.eventManager);

    }
    /**
     * When Sign Up option is selected, the button text is set to Sign Up, and the signup variable is set to true
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void leaveEventSelected(ActionEvent event) {
        actionButton.setText("Leave Event");
        signup = false;
    }

    /**
     * When Sign Up option is selected, the button text is set to Sign Up, and the signup variable is set to true
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void signUpSelected(ActionEvent event) {
        actionButton.setText("Sign Up");
        signup = true;
    }
    /**
     * Setter method for the attribute buildingManager.
     * @param building is an instance of the BuildingManager class.
     */
    public void setBuilding(BuildingManager building){
        this.building = building;
    }
    /**
     * Setter method for the attribute buildingGateway.
     * @param buildingGateway is an instance of the BuildingGateway class.
     */
    public void setBuildingGateway(BuildingGateway buildingGateway){
        this.buildingGateway = buildingGateway;
    }
    /**
     * A setter to change the value of the attribute userManager.
     * @param userManager is an instance of UserManager class.
     */
    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }
    /**
     * Setter to change the value of the attribute username.
     * @param username the username of a user account.
     */
    public void setUsername(String username){
        this.username = username;
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
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @FXML
    void initialize() {
        assert EventID != null : "fx:id=\"EventID\" was not injected: check your FXML file 'Untitled'.";
        assert printSchedule != null : "fx:id=\"printSchedule\" was not injected: check your FXML file 'Untitled'.";
        assert errorMessageText != null : "fx:id=\"errorMessageText\" was not injected: check your FXML file 'Untitled'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'Untitled'.";
        assert eventChoice != null : "fx:id=\"eventChoice\" was not injected: check your FXML file 'Untitled'.";
        assert leaveEventButton != null : "fx:id=\"leaveEventButton\" was not injected: check your FXML file 'Untitled'.";
        assert actionButton != null : "fx:id=\"actionButton\" was not injected: check your FXML file 'Untitled'.";

    }
}
