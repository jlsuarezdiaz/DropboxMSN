////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * Class MSN.
 * Class which represents a Messenger structure, with its basic features (send, read, user management).
 * @author Juan Luis
 */
public class MSN {
    /**
     * Max users allowed.
     */
    private static final int MAX_USERS = User.getMaxUsers();
    
    /**
     * Max transmission channels allowed.
     */
    private static final int MAX_CHANNELS = 128;
    
    /**
     * Message cloud directory.
     */
    private static final String MSGDIR = "./_msnsys/_msgcloud/";
    
    /**
     * Private message cloud directory.
     */
    private static final String PRIVDIR = "./_msnsys/_pmcloud/";
    
    /**
     * Maximum period of inactivity available before removing a user.
     */
    private static final int MAX_INACTIVE_PERIOD = 500;
    /**
     * Messenger user.
     */
    private User my_user;
    
    /**
     * Users list in the messenger.
     */
    private User[] user_list;
    
    /**
     * Current transmission channel.
     */
    int current_channel;
    
    // ---------- PRIVATE METHODS ---------- //
    /**
     * Gets a name for the file which will contain next message.
     * @param uid User id to send. If it is 0, it will a global output.
     * @param channel Channel to send.
     * @return File name for the message.
     */
    private static String getMsgFile(int uid, int channel){
        if(uid == 0){
            return MSGDIR + "mensaje" + Integer.toString(channel) + ".dbmsg";
        }
        else{
            return PRIVDIR + Integer.toString(uid) + "_" + Integer.toString(channel) + ".dpmsg";
        }
    }
    
    /**
     * Changes current channel.
     */
    private void nextChannel(){
        current_channel = (current_channel + 1) % MAX_CHANNELS;
    }
    
    /**
     * Gets the time difference between two dates
     * @param d1 First date.
     * @param d2 Second date.
     * @return d1 - d2 in seconds.
     */
    private static long getTimeDifference(Date d1, Date d2){
        long diff = d1.getTime() - d2.getTime();
        return diff/1000;
    }
    
    // ---------- CONSTRUCTOR ---------- //
    /**
     * Constructor.
     * @param user User of the messenger.
     */
    public MSN(User user){
        this.my_user = user;
        user_list = new User[MAX_USERS];
        for(int i = 0; i < MAX_USERS; i++){
            user_list[i] = new User(i);
        }
        Random r = new Random();
        current_channel = r.nextInt(MAX_CHANNELS);
    }
    
    // ---------- GET METHODS ----------//
    
    /**
     * Get user.
     * @return Messenger user.
     */
    public User getUser(){
        return my_user;
    }
    
    /**
     * Get available users.
     * @return list of users in messenger. 
     */
    public User[] getUserList(){
        return user_list;
    }
    
    /**
     * Get size of channel list.
     * @return Number of channels.
     */
    public static int getMaxChannels(){
        return MAX_CHANNELS;
    }
    
    
    // --------- PUBLIC METHODS ---------//
    
    /**
     * Senda a message
     * @param message Message to send.
     */
    public void send(Message message){
        message.write(getMsgFile(0,current_channel));
        nextChannel();
    }
    
    /**
     * Send a message to a specific user.
     * @param message Message to send.
     * @param user User to be send message.
     */
    public void send(Message message,User user){
        message.write(getMsgFile(user.getUid(),current_channel));
        nextChannel();
    }
    
    /**
     * Reads a message from the common message cloud.
     * @param channel Channel where the message will be read.
     * @return Message contents.
     */
    public Message get(int channel){
        Message m = new Message();
        m.read(getMsgFile(0,channel));
        return m;
    }
    
    /**
     * Reads a private message.
     * @param channel Channel where the message will be read.
     * @return Message contents.
     */
    public Message getPrivate(int channel){
        Message m = new Message();
        m.read(getMsgFile(my_user.getUid(),channel));
        File f =new File(getMsgFile(my_user.getUid(),channel));
        f.delete();
        return m;
    }
    
    /**
     * Updates the users list and erases inactive users.
     */
    public void checkUsers(){
        for(User u : user_list){
            u.read();
            if(u.getDate() != null && (getTimeDifference(my_user.getDate(),u.getDate()) > MAX_INACTIVE_PERIOD 
                    || u.getState() == UserState.OFF)){
                u.erase();
            }
        }
    }
    
    /**
     * Sets the state of the messenger user.
     * @param state New state.
     */
    public void setState(UserState state) throws UserOverflowException{
        my_user.changeState(state);
    }
}
