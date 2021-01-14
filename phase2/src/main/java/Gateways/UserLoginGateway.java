package main.java.Gateways;

import main.java.UseCases.UserManager;

import java.io.*;

/**
 * <h1>UserLoginInfo</h1>
 * Responsible for reading/writing to database that stores user info
 * @version phase2
 * @author Konstantinos Papaspyridis
 */
public class UserLoginGateway extends DatabaseGateway<UserManager>{

    public UserLoginGateway(){
        super("phase2/src/main/java/DB/UserLoginInfo.ser");
    }

    /**
     * Clear contents of database file and write LoginUserManager to database
     * @param obj The LoginUserManager object to store
     */
    @Override
    public void save(UserManager obj) {
        clearFileContentsUtil("user");
        try {
            OutputStream file = new FileOutputStream(getDbPath());
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(obj);
            output.close();
        } catch (IOException e) {
            System.err.println("Could not save user data to database.");
            e.printStackTrace();
        }
    }

    /**
     * Read user data from database.
     * @return A LoginUserManager object
     */
    @Override
    public UserManager read() {
        UserManager loginUserManager;
        try {
            InputStream file = new FileInputStream(getDbPath());
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            loginUserManager = (UserManager) input.readObject();
            input.close();
        } catch (EOFException e) { //database file is empty
            loginUserManager = new UserManager();
        } catch (ClassNotFoundException | StreamCorruptedException | InvalidClassException e) {   //incorrect class format
            System.err.println("Corrupted file contents in user database. Clearing file...");
            clearFileContentsUtil("user");
            loginUserManager = new UserManager();
        } catch (IOException e) {  //other IO exception
            System.err.println("Unknown error when reading from user database file.");
            e.printStackTrace();
            loginUserManager = new UserManager();
        }
        return loginUserManager;
    }
}