////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class user. It contains information related to a user's state in the MSN.
 * @author Juan Luis
 */
public class User {
    /**
     * User's id.
     */
    private int uid;
    
    /**
     * User's name.
     */
    private String name;
    
    /**
     * User's state.
     */
    private UserState state;
    
    /**
     * Time at last user's update.
     */
    private Date current_time;
            
    /**
     * Users directory.
     */
    private static final String MSNKER = "_msnsys/_kermsn/";
    
    /**
     * User's limit.
     */
    private static final int MAX_USERS = 200;
    
    /**
     * Date Format.
     */
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    /**
     * Input/Output delimiter.
     */
    private static final String IO_LIM = "\0";
    
    /**
     * Default constructor.
     * Creates a disconnected user with an invalid state.
     */
    public User(){
        this.uid = -1;
        this.name = "";
        this.state = UserState.OFF;
        this.current_time = null;
    }
    
    /**
     * Constructor
     * @param name User's name.
     */
    public User(String name){
        this.name = name;
        this.uid = getNewId();
        this.state = UserState.ONLINE;
        this.current_time = new Date();
        write();
    }
    
    // ---------- GETTER METHODS ---------- //
    
    /**
     * Get User's id.
     * @return User's id.
     */
    public int getUid(){
        return uid;
    }
    
    /**
     * Get User's name.
     * @return User's name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Get User's state.
     * @return User's state.
     */
    public UserState getState(){
        return state;
    }
    
    /**
     * Get User's date.
     * @return User's date.
     */
    public Date getDate(){
        return current_time;
    }
    
    /**
     * Get User's directory.
     * @return User's directory.
     */
    public static String getUserDir(){
        return MSNKER;
    }
    
    /**
     * Get User's file.
     */
    public static String getUserFile(int uid){
        return MSNKER + "_usr" + Integer.toString(uid) + ".usr";
    }
    
    // ---------- PUBLIC METHODS ---------- //
    
    /**
     * Find a new user id.
     * @return Integer with the new user id.
     */
    public int getNewId(){
        for(int i = 1; i < MAX_USERS; i++){
            File f = new File(getUserFile(i));
            if(!f.exists()){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Updates user time.
     */
    public void update(){
        current_time = new Date();
        write();
    }
    
    /**
     * Updates user state.
     */
    public void changeState(UserState state){
        this.state = state;
        update();
    }
    
    
    /**
     * Erases the corresponding user file.
     */
    public void erase(){
        File f = new File(getUserFile(uid));
        f.delete();
    }
    
    /**
     * Writes the corresponding user file and updates it.
     */
    public void write(){
        FileWriter fw = null;
        try{
            fw = new FileWriter(getUserFile(uid));
            fw.write(Integer.toString(uid) + IO_LIM);
            fw.write(name + IO_LIM);
            fw.write(state.toString() + IO_LIM);
            fw.write(df.format(current_time) + IO_LIM);
        }
        catch(IOException ex){
        }
        finally{
            try{
                if(fw != null) fw.close();
            }
            catch(IOException ex){}
        }
    }
    
    /**
     * Reads the corresponding user file and updates the user.
     */
    public void read(){
        Scanner scan = null;
        File f = new File(getUserFile(uid));
        
        if(f.exists()){
            try {
                scan = new Scanner(f);
                scan.useDelimiter(IO_LIM);
                uid = Integer.parseInt(scan.next());
                name = scan.next();
                state = UserState.valueOf(scan.next());
                current_time = df.parse(scan.next());
            } catch (FileNotFoundException | ParseException ex) {
             //   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
             //   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }
    }
    
    /**
     * Gets a string with user's info.
     * @return String with user's info.
     */
    @Override
    public String toString(){
        return "User " + Integer.toString(uid) + "\nName: " + name + "\nState: "
                    + state.toString() + "\nLast update: " + df.format(current_time);
    }
}
