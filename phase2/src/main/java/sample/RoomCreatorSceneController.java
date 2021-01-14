package main.java.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.Gateways.BuildingGateway;
import main.java.UseCases.BuildingManager;

import java.time.DateTimeException;
import java.time.LocalTime;

public class RoomCreatorSceneController {
    /**
     * BuildingManager object
     */
    private BuildingManager buildingManager;
    /**
     * BuildingGateway object that stores the building information.
     */
    private BuildingGateway buildingGateway;
    /**
     * Starting hour of the availability of the room
     */
    private int startHour;
    /**
     * Ending hour of the availability of the room
     */
    private int endHour;
    /**
     * Starting minute of the availability of the room
     */
    private int startMinute;
    /**
     * Ending minute of the availability of the room
     */
    private int endMinute;
    /**
     * Title of the room
     */
    private String roomTitle;
    /**
     * Capacity of the room
     */
    private int capacity;
    /**
     * TextField to put starting time
     */
    @FXML
    private TextField startTimePrompt;
    /**
     * TextField to put duration
     */
    @FXML
    private TextField durationPrompt;
    /**
     * TextField to put event title
     */
    @FXML
    private TextField eventTitlePrompt;
    /**
     * TextField to put capacity
     */
    @FXML
    private TextField capacityPrompt;
    /**
     * Error text
     */
    @FXML
    private Text errorText;
    /**
     * Button to submit the information.
     */
    @FXML
    private Button createButton;
    /**
     * TextField to put starting minute
     */
    @FXML
    private TextField startingMinutePrompt;
    /**
     * TextField to put ending minute
     */
    @FXML
    private TextField endingMinutePrompt;


    /**
     * When the button is pressed, the method will check if the input information are valid/meets the required input
     * format.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void createButtonPressed(ActionEvent event) {
        //LocalDate rawDate = this.datePicker.getValue();
        int startHour = 0;
        int capacity = 0;
        if (startTimePrompt.getText() == null || startingMinutePrompt.getText() == null || durationPrompt.getText() == null
        || endingMinutePrompt.getText() == null || capacityPrompt.getText() == null || eventTitlePrompt.getText() == null){
            errorText.setText("Please fill in every field!");
            return;
        }
        try{
            startHour = Integer.parseInt(startTimePrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid start hour");
            return;
        }
        try{
            this.startMinute = Integer.parseInt(startingMinutePrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid starting minute");
            return;
        }
        //this.date = rawDate.atTime(startHour, 0);
        this.startHour = startHour;
        try{
            this.endHour = Integer.parseInt(durationPrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid duration");
            return;
        }
        try{
            this.endMinute = Integer.parseInt(endingMinutePrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid ending minute");
            return;
        }
        //this.endDate = rawDate.atTime(startHour + duration, 0);
        this.startHour = startHour;
        this.roomTitle = eventTitlePrompt.getText();
        try{
            capacity = Integer.parseInt(capacityPrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid capacity");
            return;
        }
        this.capacity = capacity;
        try{
            createRoom();
        }catch (DateTimeException e){
            errorText.setText("Invalid date given, please try again");
        }
    }

    /**
     * Allows or rejects the organizer to create a new room, based on existing rooms.
     */
    private void createRoom(){
        if (buildingManager.addRoom(roomTitle, LocalTime.of(startHour, startMinute),
                LocalTime.of(endHour, endMinute), this.capacity)){
            errorText.setText("Room Created!");
            this.buildingGateway.save(this.buildingManager);
        }else {
            errorText.setText("Room Creation Failed!");
        }
    }
    /**
     * Setter method for the attribute buildingManager.
     * @param buildingManager is an instance of the BuildingManager class.
     */
    public void setBuildingManager(BuildingManager buildingManager){
        this.buildingManager = buildingManager;
    }
    /**
     * Setter method for the attribute buildingGateway.
     * @param buildingGateway is an instance of the BuildingGateway class.
     */
    public void setBuildingGateway(BuildingGateway buildingGateway){
        this.buildingGateway = buildingGateway;
    }
    /**
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @FXML
    void initialize() {
        //assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert startTimePrompt != null : "fx:id=\"startTimePrompt\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert durationPrompt != null : "fx:id=\"durationPrompt\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert eventTitlePrompt != null : "fx:id=\"eventTitlePrompt\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert capacityPrompt != null : "fx:id=\"capacityPrompt\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'RoomCreatorScene.fxml'.";

    }
}
