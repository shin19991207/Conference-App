package main.java.Presenters;

/**
 * <h1>Starting Menu</h1>
 * This presenter contains the possible print statements that can occur while creating an account or signing in.
 * This is the menu that should be shown to a user immediately when they start up the program.
 * This is what is used for the console version of our program.
 * @author Blake Gigiolio
 */
public class StartingMenu {
    /**
     * Prints the first prompt the user should see when the program is started.
     */
    public void initialPrompt(){
        System.out.println("Are you a new user or a returning user?");
        System.out.println("Type:\n\t[N] for new\n\t[R] for returning");
    }

    /**
     * This is what the user should see when they input an invalid response.
     */
    public void failedPrompt(){
        System.out.println("Invalid response, try again!");
    }

    /**
     * This is what the user should see if the account they are trying to make already exists.
     */
    public void alreadyRegistered() {
        System.out.println("User already registered. Try logging in...");
    }

    /**
     * This is what the user should see when creating account asking them what their desired role is.
     */
    public void rolePrompt(){
        System.out.println("Is this account for an organizer, an attendee, or an administrator?");
        System.out.println("Type [O] for organizer, type [U] for attendee, or type [A] for administrator.");
    }

    /**
     * This is what the user should see if they properly logged in.
     */
    public void loggedInPrompt(){
        System.out.println("Account successfully logged into");
    }

    /**
     * This is what the user should see when they need to input a username.
     */
    public void uPrompt(){
        System.out.println("Please enter your username: ");
    }

    /**
     * This is what the user should see when they need to input a password.
     */
    public void pPrompt(){
        System.out.println("Please enter your password: ");
    }

    /**
     * This is what the the user sees when they have failed to enter a unique username.
     */
    public void usernameUsed() { System.out.println("Username already exists! Please enter another username: ");}

    /**
     * This is what the user sees when they have successfully created an account.
     */
    public void newUserCreated() {System.out.println("Account successfully created");}

    /**
     * This is what the user sees if they enter an username that does not exists.
     */
    public void usernameNotFoundPrompt() {
        System.out.println("That account doesn't exist.\n[1] Enter a different account\n[2] Get a new one");
    }

    /**
     * This is what the user sees when they have entered a password that does not exist.
     */
    public void wrongPasswordPrompt() {
        System.out.println("Incorrect password.\n[1] Try again\n[2] Forgot password?");
    }

    /**
     * This is what the user sees when they have selected to reset their password.
     */
    public void resetPasswordPrompt() {
        System.out.println("Please enter your new password: ");
    }

    /**
     * This is what the user sees when they have selected the wrong role associated with their login.
     * @param role The String representing the user's selected role.
     */
    public void wrongRole(String role) {
        System.out.println("The username entered belongs to a(n) " + role + " account. Please try logging in again.");
    }

    /**
     * This is what the user sees after they have successfully reset their password.
     */
    public void passwordResetSuccess() {
        System.out.println("You have successfully reset your password. You may now use your new password to log in.");
    }

    /**
     * This prints the welcome message for the current user.
     * @param username The String representing the currently logged in user.
     */
    public void welcome(String username) {
        System.out.println("Welcome " + username + "!");
    }
}
