package main.java.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    /**
     * ToggleGroup for the radio buttons of sending message and reviewing message.
     */
    @FXML
    ToggleGroup action;
    /**
     * RadioButton to send message.
     */
    @FXML
    RadioButton sendMessage;
    /**
     * RadioButton to review inbox
     */
    @FXML
    RadioButton reviewMessage;
    /**
     * The pane of send message.
     */
    @FXML
    AnchorPane sendPane;
    /**
     * The pane of review message.
     */
    @FXML
    AnchorPane reviewPane;
    /**
     * Button to send the message to the specified user.
     */
    @FXML
    Button sendButton;
    /**
     * The user who will receive the message.
     */
    @FXML
    TextField toUsername;
    /**
     * The user who sends the message.
     */
    @FXML
    String sender;
    /**
     * The message
     */
    @FXML
    TextArea message;
    /**
     * User's received messages box
     */
    @FXML
    TextArea inbox;
    /**
     * Label to tell the user that the message is sent.
     */
    @FXML Label sentValid;
    /**
     * Label to tell the user that the message i not sent.
     */
    @FXML Label sentInvalid;
    /**
     * MessageController class that is in the Controllers package.
     */
    main.java.Controllers.MessageController messageController = new main.java.Controllers.MessageController();

    /**
     * Setter method to change the value of the attribute sender.
     * @param sender is the username string of the account who sends the message.
     */
    public void setSender(String sender){
        this.sender = sender;
    }
    /**
     * The method is called as soon a the program is run to prepare the controller for the scene before the scene is
     * reached.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * When the user clicks anywhere in the pane of the send tab, the message sending confirmation message will
     * disappear.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    public void handleSendPane(MouseEvent event){
        sentValid.setVisible(false);
        sentInvalid.setVisible(false);
    }

    /**
     * When send button is clicked, the method will try to send the message to the specified user, will tell the sender
     * whether the message is successfully sent or failed.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */
    public void handleSendButton(ActionEvent event) {

        messageController.setMessageSystem(this.message.getText(), this.toUsername.getText(),this.sender);
        if (messageController.sendMessage()){
            sentValid.setVisible(true);
            sentInvalid.setVisible(false);

        }
        else{
            sentInvalid.setVisible(true);
            sentValid.setVisible(false);

        }


    }
    /**
     * Switches the tab of send message or review message, depending on the option that the user selects.
     * @param event is the action of the button, that is associated with the method, is clicked.
     */

    public void handleMessageAction(ActionEvent event) {
        if (action.getSelectedToggle() == sendMessage){
            sendPane.setVisible(true);
            reviewPane.setVisible(false);
        }
        else{ //action.getSelectedToggle() == reviewMessage
            sendPane.setVisible(false);
            reviewPane.setVisible(true);

            StringBuilder builder = new StringBuilder();

            for ( String i : messageController.getMessageForMe(this.sender)){
                builder.append(i).append("\n");
            }
            this.inbox.setText(String.valueOf(builder));

        }
    }
}
