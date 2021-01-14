package main.java.sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.Gateways.BuildingGateway;
import main.java.Gateways.EventGateway;
import main.java.Gateways.UserLoginGateway;
import main.java.UseCases.BuildingManager;
import main.java.UseCases.EventManager;
import main.java.UseCases.UserManager;

import java.io.IOException;

public class HomeScreenController{


    /**
     * The main pane of the scene
     */
    @FXML
    public AnchorPane mainPane;
    /**
     * The top pain of the scene
     */
    @FXML
    public AnchorPane topPane;
    /**
     * BuildingManager object
     */
    public BuildingManager buildingManager;
    /**
     * EventManager object.
     */
    public EventManager eventManager;
    /**
     * BuildingGateway object that stores the building information.
     */
    BuildingGateway buildingGateway;
    /**
     * UserLoginGateway object that stores all users' information
     */
    UserLoginGateway userLoginGateway;
    /**
     * UserManager object.
     */
    public UserManager userManager;
    /**
     * EventGateway object that stores the events.
     */
    EventGateway eventGateway;
    /**
     * Username of the user
     */
    private String username;
    /**
     * Password of the user
     */
    private String password;
    /**
     * Role of the user
     */
    private String role;
    /**
     * The text that says welcome
     */
    @FXML
    private Text welcomeText;
    /**
     * Text of instruction prompt
     */
    @FXML
    private Text instructionPrompt;
    /**
     * MenuButton that contains all the options
     */
    @FXML
    private MenuButton menuOptions;
    /**
     * Option to review event schedule
     */
    @FXML
    private MenuItem reviewEventSchedule;
    /**
     * Option to take message actions
     */
    @FXML
    public MenuItem Message;
    /**
     * Option to log out
     */
    @FXML
    public MenuItem logout;
    /**
     * Option to see event schedule of the conference.
     */
    @FXML
    public MenuItem seeEventSchedule;
    /**
     * Option to sign up for or leave an event
     */
    @FXML
    public MenuItem signUpForEvent;
    /**
     * Option to manage friends list
     */
    @FXML
    private MenuItem manageFriendsList;
    /**
     * Option to create a user account
     */
    @FXML
    private MenuItem createUserAccount;

    /**
     * Option to add room
     */
    @FXML
    private MenuItem addRoom;
    /**
     * Option to schedule/assign a speaker to an event
     */
    @FXML
    private MenuItem scheduleSpeaker;
    /**
     * Option to remove an event
     */
    @FXML
    private MenuItem removeEvent;
    /**
     * Option to message event attendees
     */
    @FXML
    private MenuItem messageEventAttendees;
    /**
     * Option to create an event
     */
    @FXML
    private MenuItem createEvent;



    /**
     * The method is called as soon as the program is run to prepare the necessary attributes before the scene that the
     * controller is associated with is not reached.
     */
    public void initialize(){
        eventGateway = new EventGateway();
        userLoginGateway = new UserLoginGateway();
        buildingGateway = new BuildingGateway();
        buildingManager = buildingGateway.read();
        eventManager = eventGateway.read();
        if(!buildingManager.verifyBuilding(eventManager)){
            buildingGateway.clearFileContentsUtil("building");
            eventGateway.clearFileContentsUtil("events");
            eventManager  = new EventManager();
            buildingManager = new BuildingManager("Building");
        }
        userManager = userLoginGateway.read();
        Platform.runLater(this::showOptions);
    }

    /**
     * Register a new user
     * @param role user's roles
     * @param username the username of the user
     * @param password the password of the user
     * @return the username of the registered user
     */
    public String register(String role, String username, String password){

        if (this.userManager.registerUser(username, password, role)) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.userLoginGateway.save(this.userManager);
            welcomeText.setText("Welcome " + this.username + "!");
            return username;
        }
        return null;

    }

    /**
     * Shows a menu of possible actions depending on the role of the user/account.
     */
    private void showOptions() {
        try {
            if (this.role.equalsIgnoreCase("attendee")) {
                createUserAccount.setVisible(false);
                addRoom.setVisible(false);
                scheduleSpeaker.setVisible(false);
                removeEvent.setVisible(false);
                messageEventAttendees.setVisible(false);
                createEvent.setVisible(false);
            }
            else if (this.role.equalsIgnoreCase("speaker")){
                createUserAccount.setVisible(false);
                addRoom.setVisible(false);
                scheduleSpeaker.setVisible(false);
                removeEvent.setVisible(false);
                messageEventAttendees.setVisible(false);
                createEvent.setVisible(false);
            }
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * This is how a user will log in. Here we call the log in menu prompt.
     * @param username the username of the user
     * @param password the password of the user
     * @return username of user logged. Null if couldn't log in.
     */
    public String login(String username, String password) {


        if (this.userManager.loginUser(username, password).equalsIgnoreCase("loggedin")) {
            this.username = username;
            this.password = password;
            this.role = this.userManager.getUserRole(username);
            welcomeText.setText("Welcome " + this.username + "!");
            return username;
        }
        return null;
    }


    /**
     * Logs the user out when the logout option is clicked and displays the login screen.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("OpeningScene.fxml"));
        primaryStage = (Stage) menuOptions.getScene().getWindow();
        Scene openingScene = new Scene(root,300,275);
        primaryStage.setScene(openingScene);
        primaryStage.show();
        eventGateway.save(eventManager);
        buildingGateway.save(buildingManager);
        userLoginGateway.save(userManager);
    }


    /**
     * Opens a new window, when Manage friends option is pressed,
     * where the user can either add or remove friend from their friendlist.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void manageFriends(ActionEvent event) throws IOException {
        Stage EventStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("friendsScene.fxml"));
        Parent root = loader.load();
        friendsController fController = loader.getController();
        fController.setUserManager(this.userManager);
        fController.setUsername(this.username);
        EventStage = new Stage();
        EventStage.setScene(new Scene(root,500,500));
        EventStage.show();
    }

    /**
     * Opens a new window when Add room option is pressed,
     * where the organizer can add a room by entering required information.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void addRoomPressed(ActionEvent event) throws IOException {
        Stage EventStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("RoomCreatorScene.fxml"));
        Parent root = loader.load();
        RoomCreatorSceneController RCSC = loader.getController();
        RCSC.setBuildingManager(this.buildingManager);
        RCSC.setBuildingGateway(this.buildingGateway);
        EventStage = new Stage();
        EventStage.setScene(new Scene(root,700,500));
        EventStage.show();
    }

    /**
     * Opens a new window when Create event option is pressed, where the organizer can create
     * an event by inputting required information.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void createEventPressed(ActionEvent event) throws IOException {
        Stage eventCreatorStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("EventCreatorScene.fxml"));
        Parent root = loader.load();
        EventCreatorSceneController ECSC = loader.getController();
        ECSC.setBuildingManager(this.buildingManager);
        ECSC.setBuildingGateway(this.buildingGateway);
        ECSC.setEventGateway(this.eventGateway);
        ECSC.setEventManager(this.eventManager);
        eventCreatorStage = new Stage();
        eventCreatorStage.setScene(new Scene(root,700,500));
        eventCreatorStage.show();
    }

    /**
     * Opens a new window when Create user account option is pressed, where organizers can create
     * an account for other people in the conference.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void createUserAccountPressed(ActionEvent event) throws IOException {
        Stage CreateUserStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("createUserScene.fxml"));
        Parent root = loader.load();
        CreateUserSceneController CSC = loader.getController();
        CSC.setUserManager(this.userManager);
        CSC.setUserLoginGateway(this.userLoginGateway);
        CreateUserStage = new Stage();
        CreateUserStage.setScene(new Scene(root,500,500));
        CreateUserStage.show();
    }

    /**
     * Opens a new window when Message event attendees option is pressed, where the organizer can
     * message attendees of a particular event.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void messageEventAttendeesPressed(ActionEvent event) {

    }

    /**
     * Opens a new window when Remove Event option is pressed, where the organizer can remove the event.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.     */
    @FXML
    void removeEventPressed(ActionEvent event) throws IOException {
        Stage eventRemoverStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("EventRemoverScene.fxml"));
        Parent root = loader.load();
        EventRemoverSceneController ERSC = loader.getController();
        ERSC.setBuildingManager(this.buildingManager);
        ERSC.setBuildingGateway(this.buildingGateway);
        ERSC.setEventGateway(this.eventGateway);
        ERSC.setEventManager(this.eventManager);
        eventRemoverStage = new Stage();
        eventRemoverStage.setScene(new Scene(root,700,500));
        eventRemoverStage.show();
    }

    /**
     * Opens a new window when Schedule speaker option is clicked, where the organizer can add a speaker to an event.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void scheduleSpeakerPressed(ActionEvent event) throws IOException {
        Stage scheduleSpeakerStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("scheduleSpeakerScene.fxml"));
        Parent root = loader.load();
        ScheduleSpeakerSceneController SSSC = loader.getController();
        SSSC.setEventGateway(this.eventGateway);
        SSSC.setEventManager(this.eventManager);
        SSSC.setUserManager(this.userManager);
        SSSC.setUserLoginGateway(this.userLoginGateway);
        scheduleSpeakerStage = new Stage();
        scheduleSpeakerStage.setScene(new Scene(root,700,500));
        scheduleSpeakerStage.show();
    }

    /**
     * Opens a new window when message option is clicked, where the user can review their inbox or send a new message
     * to others.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void Message(ActionEvent event) throws IOException {
        Stage messageStage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MessageScene.fxml"));
        Parent root = loader.load();
        MessageController MC = loader.getController();
        MC.setSender(this.username);
        messageStage = new Stage();
        messageStage.setScene(new Scene(root,500,500));
        messageStage.show();

    }

    /**
     * Opens a new window and shows the schedule/program of events of the conference.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void showSchedule(ActionEvent event) throws IOException {
        Stage scheduleStage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ScheduleTable.fxml"));
        Parent root = loader.load();
        ScheduleTableController scheduleTableController = loader.getController();
        scheduleTableController.setUsername(this.username);

        scheduleStage = new Stage();
        scheduleStage.setScene(new Scene(root,940,610));
        scheduleStage.show();
    }

    /**
     * Opens a new window and let the user to sign up for an event.
     * @param event is the action of the button, that is associated with the method, is clicked.
     * @throws IOException to handle a possible exception in the input or the output.
     */
    @FXML
    void signUp(ActionEvent event) throws IOException {
//        Stage EventStage;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpScene.fxml"));
//        loader.load();
//        signUpController SUC = loader.getController();
//        SUC.setBuilding(this.buildingManager);
//        SUC.setUserManager(this.userManager);
//        Parent root = FXMLLoader.load(getClass().getResource("signUpScene.fxml"));
//        EventStage = new Stage();
//        EventStage.setScene(new Scene(root,500,500));
//        EventStage.show();
        Stage EventStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("signUpScene.fxml"));
        Parent root = loader.load();
        signUpController SUC = loader.getController();
        SUC.setUserManager(this.userManager);
        SUC.setBuildingGateway(this.buildingGateway);
        SUC.setUsername(this.username);
        SUC.setBuilding(this.buildingManager);
        SUC.setEventManager(this.eventManager);
        SUC.setEventGateway(this.eventGateway);
        EventStage = new Stage();
        EventStage.setScene(new Scene(root,500,500));
        EventStage.show();
    }


}
