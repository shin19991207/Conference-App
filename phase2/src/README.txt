## Set Up

   The program starts by running the class named "Main" under package "sample".


## Login and Register

   * New User Account Registration:
     Select 'User Type: New User' > Choose an 'User Role' > Enter the 'Username' and 'Password' you want to use for your account

   * Returning User Login:
     Select 'User Type: Returning User' > Enter your 'Username' and 'Password'


## User Interface

   - This is the interface the user gets to after logging in
   - There is an 'Actions' menu where user can choose the actions they would like to do
   - Attendees and Speakers can only to do all General Actions
   - Organizers and Admins can do all General Actions plus all Organizer Actions

   ## General Actions
      Actions available for all types of user

      * See Event Schedule
        - 'Full Schedule': See the schedule of events that can be signed up for
        - 'Your Schedule': See the schedule of the events for which you have signed up for
        - Search for specific events by event 'ID', 'Title', 'Location', 'Date Time', 'Type', 'Speaker' using the choice box and the search box
        - 'Download Full Schedule' onto the user's local computer

      * Manage My Events
        - 'Sign Up' and 'Leave Event' by entering an event ID (copy from 'See Event Schedule')

      * Message
      - 'Review Message': See all messages in your inbox
      - 'Send Message': Send a message to 'Username'

      * Manage Friends List
      - 'Add friend' and 'Remove friend' by entering 'Friend's username'

      * LogOut
      - Log out of your account

   ## Organizer Actions
      Actions available for organizers and admins only

      * Create User Account
      - Create a new account for a specific use

      * Add Room
      - Add a new event location

      * Schedule Speaker
      - Schedule speaker(s) to an event

      * Remove Event: Remove a scheduled event

      * Message Event Attendees: message all attendees of a specific event

      * Create Event: Schedule a new event


## Saving and Persistence of Information

   Information is auto-saved into the data bases under package "DB" whenever an action is done


########################################################################################################################
## Text UI Version of the Program

The program starts by running the class named "Run"

Once the program runs, 
1. You will be asked if you are a new user or you have registered before. Type N if you are new or R for you have an account.

2. It will ask if you are an organizer or an attendee. Speakers can login as an attendee.
If you are an organizer, type O. If you are a speaker or an attendee, type A.

3. Enter your username and password.

4. Attendees have a menu of General Actions and Organizers have a menu of Organizer Specific Actions in addition to General Actions. You can choose any actions available.

Once you are logged in:

Organizers are able to:
- enter rooms into the system by inputting 10 to select Add Room option. Prompts to input room name, opening time, closing time for the room. The room will be open from opening time to closing time so events can be added to that room without time conflict in that time period.
- create speaker accounts by inputting 9 to select Create Speaker option.
- schedule the speakers to event in rooms by inputting 11 to select Schedule Speaker. Enter speaker name and enter the name of an existing event to add the speaker to.
- Allow the system to support additional user requests (e.g. dietary restrictions, accessibility requirements) where organizers can tag a request as "pending" or "addressed". All organizers see the same list of requests, so if one tags a request as addressed, all other organizers can log in and see that.

Speakers are able to:
- see a list of talks that they are giving by inputting 15 to select View Lists Of My Events.
- message all Attendees by inputting 16. Prompts for choosing the event, sending message one attendee or multiple attendees will show up.

Attendees are able to:
- see a schedule of events by inputting 1 to select See Event Schedule.
- sign up for events by inputting 3 to select Sign Up For Event. Enter the name of the existing event you want to participate.
- cancel their enrolment in an event by inputting 4 to select Cancel Event attendance. Enter the name of the existing event you want to opt out from.
- see the schedule of events for which they are signed up for by inputting 2 to select Review Your Events Schedule.
- send messages by inputting 5 to select Send Message. Enter the username of the user you want to send message to, then enter the message content.
- view the received messages by inputting 6. You will view your inbox.