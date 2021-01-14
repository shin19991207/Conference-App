package main.java.sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.Gateways.BuildingGateway;
import main.java.Gateways.EventGateway;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventCreatorSceneController {

    /**
     * BuildingManager object
     */
    private BuildingManager buildingManager;
    /**
     * BuildingGateway object that stores the building information.
     */
    private BuildingGateway buildingGateway;
    /**
     * Starting date and time of the event
     */
    private LocalDateTime startDate;
    /**
     * Ending date and time of the event
     */
    private LocalDateTime endDate;
    /**
     * Title of the room
     */
    private String roomTitle;
    /**
     * Title of the event
     */
    private String eventTitle;
    /**
     * Type of the event
     */
    private String type;
    /**
     * Duration of the event
     */
    private int duration;
    /**
     * Attendee capacity of the event
     */
    private int eventCapacity;
    /**
     * EventManager object.
     */
    private EventManager eventManager;
    /**
     * EventGateway object that stores the events.
     */
    private EventGateway eventGateway;
    /**
     * ChoiceBox that displays the possible types of events
     */
    @FXML
    private ChoiceBox<String> eventTypeChoiceBox;
    /**
     * TextField to put the capacity information of the event
     */
    @FXML
    private TextField capacityPrompt;
    /**
     * TextField to put the title of the event
     */
    @FXML
    private TextField eventTitlePrompt;
    /**
     * TextField to put the name of the room that the event is held
     */
    @FXML
    private TextField roomNamePrompt;
    /**
     * Calendar to choose the date of the event
     */
    @FXML
    private DatePicker eventDatePrompt;
    /**
     * TextField to put the starting hour of the event
     */
    @FXML
    private TextField startHourField;
    /**
     * TextField to put the ending minute of the event
     */
    @FXML
    private TextField startMinuteField;
    /**
     * TextField to put the ending hour of the event
     */
    @FXML
    private TextField durationField;
    /**
     * Button to be clicked when information are ready to be submitted to create the event
     */
    @FXML
    private Button createEventButton;
    /**
     * Text that is displayed if event creation is unsuccessful.
     */
    @FXML
    private Text errorText;

    /**
     * When the Create event button is pressed, the method checks if the input information suits the
     * expected input format.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void createEventButtonPressed(ActionEvent event) {
        //LocalDate rawDate = this.datePicker.getValue();
        int startHour;
        int duration;
        int startMinute;
        LocalDate rawStartDate;
        if (startHourField.getText() == null || startMinuteField.getText() == null || durationField.getText() == null
                || roomNamePrompt.getText() == null || capacityPrompt.getText() == null ||
                eventTitlePrompt.getText() == null || roomNamePrompt.getText() == null ||
                eventDatePrompt.getValue() == null || eventTypeChoiceBox.getValue() == null){
            errorText.setText("Please fill in every field!");
            return;
        }
        try{
            startHour = Integer.parseInt(startHourField.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid start hour");
            return;
        }
        try{
            this.eventCapacity = Integer.parseInt(capacityPrompt.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid capacity");
            return;
        }
        try{
            startMinute = Integer.parseInt(startMinuteField.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid starting minute");
            return;
        }
        if(startHour < 0 || startHour > 23) {
            errorText.setText("Please input a valid starting hour");
            return;
        }
        if(startMinute<0 || startMinute > 59){
            errorText.setText("Please input a valid starting minute");
            return;
        }

        rawStartDate = eventDatePrompt.getValue();

        this.startDate = rawStartDate.atTime(startHour, startMinute);

        try{
            duration = Integer.parseInt(durationField.getText());
        }catch (NumberFormatException e){
            errorText.setText("Please input a valid duration");
            return;
        }
//        try{
//            durMinute = Integer.parseInt(endMinuteField.getText());
//        }catch (NumberFormatException e){
//            errorText.setText("Please input a valid ending minute");
//            return;
//        }

        //this.endDate = rawDate.atTime(startHour + duration, 0);
        this.roomTitle = roomNamePrompt.getText();
        this.type = eventTypeChoiceBox.getValue();
        this.eventTitle = eventTitlePrompt.getText();
        this.duration = duration;
        createEvent();
    }
    /**
     * When the Create event button is pressed, the method checks the input information, and allows or rejects the
     * organizer to create an event, based on existing events that are created in the conference. It is called by
     * the method createEventButtonPressed so that the method is called when the Create event button is clicked.
     */
    private void createEvent(){
        if(this.type.equalsIgnoreCase("No speaker")){
            if(!eventManager.addEvent(this.eventTitle, this.roomTitle, this.startDate, this.duration, eventCapacity,
                    "event", this.buildingManager)){
                errorText.setText("Event could not be created!");
                return;
            }

        }else if(this.type.equalsIgnoreCase("Talk")){
            if(!eventManager.addEvent(this.eventTitle, this.roomTitle, this.startDate, this.duration, eventCapacity,
                    "talk", this.buildingManager)) {
                errorText.setText("Event could not be created!");
                return;
            }

        }else if(this.type.equalsIgnoreCase("Panel discussion")){
            if(!eventManager.addEvent(this.eventTitle, this.roomTitle, this.startDate, this.duration, eventCapacity,
                    "panelDiscussion", this.buildingManager)) {
                errorText.setText("Event could not be created!");
                return;
            }
        }
        errorText.setText("Event Successfully Created");
        this.eventGateway.save(this.eventManager);
        this.buildingGateway.save(this.buildingManager);
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
     * Shows the options of event types that the organizer can create.
     */
    public void showOptions(){
        eventTypeChoiceBox.getItems().addAll("No speaker", "Talk", "Panel discussion");
    }

    /**
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @FXML
    void initialize() {
        assert eventTitlePrompt != null : "fx:id=\"eventTitlePrompt\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert roomNamePrompt != null : "fx:id=\"roomNamePrompt\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert eventDatePrompt != null : "fx:id=\"eventDatePrompt\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert startHourField != null : "fx:id=\"startHourField\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert startMinuteField != null : "fx:id=\"startMinuteField\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert durationField != null : "fx:id=\"durationField\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        //assert endMinuteField != null : "fx:id=\"endMinuteField\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'EventCreatorScene.fxml'.";
        Platform.runLater(this::showOptions);
    }
}
