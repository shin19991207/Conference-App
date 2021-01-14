package main.java.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import main.java.UseCases.*;


public class friendsController {
    /**
     * UserManager object.
     */
    private UserManager userManager;
    /**
     * Username of the account
     */
    private String username;
    /**
     * RadioButton to be clicked to add friend
     */
    @FXML
    private RadioButton addFriendButton;
    /**
     * ToggleGroup of actions to choose
     */
    @FXML
    private ToggleGroup actionChoice;
    /**
     * RadioButton to be clicked to remove friend
     */
    @FXML
    private RadioButton removeFriendButton;
    /**
     * Button that is pressed to submit adding or removing friend action
     */
    @FXML
    private Button actionButton;
    /**
     * Text of informing an error
     */
    @FXML
    private Text errorMessageText;
    /**
     * TextField to put the username of the friend
     */
    @FXML
    private TextField friendUsername;
    /**
     * Boolean to check if the action is for removing or adding friend.
     */
    private boolean add;

    /**
     * When Add friend option is selected, the method changes the button text to "add friend" and sets the instance
     * variable "add" to true.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void addFriendSelected(ActionEvent event) {
        actionButton.setText("Add friend");
        add = true;
    }
    /**
     * When Remove friend option is selected, the method changes the button text to "Remove friend" and sets the instance
     * variable "add" to false.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void removeFriendSelected(ActionEvent event) {
        actionButton.setText("Remove friend");
        add = false;
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
     * Depending on the add or remove friend option selected, the method will either add or remove a specified user
     * to/from the friendlist.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    @FXML
    void actionButtonPressed(ActionEvent event) {
        String fusername = friendUsername.getText();
        if (add){
            if (userManager.addFriend(this.username, fusername)){ //& eventManager.addAttendee(id, username)){
                String num = Integer.toString(this.userManager.getNumOfFriends(this.username));
                errorMessageText.setText(fusername + " added to your friends. You now have " + num + " friend(s)." );
                errorMessageText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            }
            else{
                errorMessageText.setText("User with username " + fusername + " does not exist.");
                errorMessageText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            }
        }
        else{
            if (userManager.removeFriend(this.username, fusername)){
                String num = Integer.toString(this.userManager.getNumOfFriends(this.username));
                errorMessageText.setText(fusername + " removed from your friends. You now have " + num + " friend(s)." );
                errorMessageText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            }
            else{
                errorMessageText.setText("User with username " + fusername + " does not exist in your friends.");
                errorMessageText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

            }
        }


    }
    /**
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @FXML
    void initialize(){
        assert friendUsername != null : "fx:id=\"friendUsername\" was not injected: check your FXML file 'Untitled'.";
        assert errorMessageText != null : "fx:id=\"errorMessageText\" was not injected: check your FXML file 'Untitled'.";
        assert addFriendButton != null : "fx:id=\"addFriendButton\" was not injected: check your FXML file 'Untitled'.";
        assert actionChoice != null : "fx:id=\"actionChoice\" was not injected: check your FXML file 'Untitled'.";
        assert removeFriendButton != null : "fx:id=\"removeFriendButton\" was not injected: check your FXML file 'Untitled'.";
        assert actionButton != null : "fx:id=\"actionButton\" was not injected: check your FXML file 'Untitled'.";
    }

}