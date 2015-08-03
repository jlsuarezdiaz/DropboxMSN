////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

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
     * Randomizer.
     */
    private static final Random rand = new Random();
    
    
    /**
     * Default constructor.
     * Creates a disconnected user with an invalid state, just the id is set.
     * @param uid User's id.
     */
    public User(int uid){
        this.uid = uid;
        this.name = "";
        this.state = UserState.OFF;
        this.current_time = null;
    }
    
    /**
     * Constructor
     * @param name User's name.
     */
    public User(String name) throws UserOverflowException{
        this.name = name;
        this.uid = getNewId();
        this.state = UserState.ONLINE;
        this.current_time = new Date();
        if(uid != -1) write();
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
     * Gets date format used in user's output
     * @return Date format.
     */
    public static DateFormat getDateFormat(){
        return df;
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
    
    /**
     * 
     * @return Max number of users.
     */
    public static int getMaxUsers(){
        return MAX_USERS;
    }
    
    // ---------- PUBLIC METHODS ---------- //
    
    /**
     * Find a new user id.
     * @return Integer with the new user id.
     * @throws Model.UserOverflowException
     */
    public int getNewId() throws UserOverflowException{
        int rnd = rand.nextInt(MAX_USERS);
        int fnum;
        for(int i = 0; i < MAX_USERS; i++){
            fnum = (i+rnd)%MAX_USERS;
            File f = new File(getUserFile(fnum));
            if(!f.exists() && fnum != 0){
                return fnum;
            }
        }
        throw new UserOverflowException("No users space available.");
    }
    
    /**
     * Updates user.
     * @throws Model.UserOverflowException
     */
    public void update() throws UserOverflowException{
        update(false);
    }
    
    /**
     * Updates user.
     * @param newid If it is true, user id is also updated.
     * @throws UserOverflowException 
     */
    public void update(boolean newid) throws UserOverflowException{
        erase();
        if(state != UserState.OFF){
            if(newid) uid = getNewId();
            current_time = new Date();
        }
        write();
    }
    
    /**
     * Updates user state.
     * @throws Model.UserOverflowException
     */
    public void changeState(UserState state) throws UserOverflowException{
        this.state = state;
        update();
    }
    
    /**
     * Checks if user state is valid.
     */
    public boolean validState(){
        return state != UserState.OFF;
    }
    
    /**
     * Erases the corresponding user file.
     */
    public void erase(){
        File f = new File("./"+getUserFile(uid));
        f.setWritable(true);
        f.delete();
    }
    
    /**
     * Writes the corresponding user file and updates it.
     */
    public void write(){
        write(getUserFile(uid));
    }
    
    /**
     * Writes to any file.
     * @param file 
     */
    public void write(String file){
        //FileWriter fw = null;
        OutputStreamWriter fw = null;
        try{
            //fw = new FileWriter(file);
            fw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
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
        read(getUserFile(uid));
    }
    
    /**
     * Reads an user from any file.
     * @param file 
     */
    public void read(String file){
        Scanner scan = null;
        File f = new File(file);
        
        if(f.exists()){
            try {
                scan = new Scanner(f,"UTF-8");
                scan.useDelimiter(IO_LIM);
                uid = Integer.parseInt(scan.next());
                name = scan.next();
                state = UserState.valueOf(scan.next());
                current_time = df.parse(scan.next());
            } catch (FileNotFoundException | ParseException | NoSuchElementException ex) {
                name = "";
                state = UserState.OFF;        
             //   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
             //   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                if(scan != null) scan.close();
            }
        }
        else{
            name = "";
            state = UserState.OFF;
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
