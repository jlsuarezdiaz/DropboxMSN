////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package GUI;

import Model.MSN;
import Model.Message;
import Model.MessageKind;
import Model.User;
import Model.UserOverflowException;
import Model.UserState;

/**
 * Class MSNController.
 * A structure based on a MSN class which manages reading, sending, updating and GUI synchronizing proccesses.
 * @author Juan Luis
 */
public class MSNController {
    /**
     * View associated to controller.
     */
    private MSNView view;
    
    /**
     * MSN associated to controller.
     */
    private MSN msn;
    
    /**
     * Indicates if messenger is running.
     */
    private boolean running;
    
    /**
     * Buffers with last received messages.
     */
    private Message[] read_buffers;
    
    //private Message[] private_buffers;
    
    /**
     * Inits the buffers with the existing old messages.
     */
    private void initBuffers(){
        for(int i = 0; i < MSN.getMaxChannels(); i++){
            read_buffers[i] = msn.get(i);
        }
    }
    
    /**
     * Constructor
     * @param u MSN user
     * @param view View to associate controller.
     */
    public MSNController(User u,MSNView view){
        msn = new MSN(u);
        this.view = view;
        running = false;
        view.enableMSNComponents(running);
        this.read_buffers = new Message[MSN.getMaxChannels()];
        //this.private_buffers = new Message[MSN.getMaxChannels()];
    }
    
    /**
     * Starts the messenger.
     */
    public void run(){
        initBuffers();
        running = true;
        view.enableMSNComponents(running);
        msn.send(new Message(msn.getUser().getName(),"",MessageKind.BEGIN));
    }
    
    /**
     * Stops the messenger.
     */
    public void stop(){
        running = false;
        view.enableMSNComponents(running);
        msn.send(new Message(msn.getUser().getName(),"",MessageKind.END));
        getUser().erase();
    }
    
    /**
     * Sends a message.
     * @param msg Message to send.
     */
    public void send(Message msg){
        msn.send(msg);
    }
    
    /**
     * Sends a message to a specific user.
     * @param msg Message to send.
     * @param u User to be send message.
     */
    public void send(Message msg,User u){
        msn.send(msg,u);
    }
    
    /**
     * Performs a complete reading of existing messages, and updates new messages in the view.
     */
    public void reader(){
        for(int i = 0; i < MSN.getMaxChannels() && running; i++){
            Message msg = msn.get(i);
            if(msg != null && !msg.isEmpty() && !msg.equals(read_buffers[i])){
                read_buffers[i] = msg;
                view.pushMessage(msg);
                view.messageSound();
            }
        }
    }
    
    /**
     * Performs a complete reading of existing private messages, and updates new messages in the view.
     */
    public void privateReader(){
        for(int i = 0; i < MSN.getMaxChannels() && running; i++){
            Message msg = msn.getPrivate(i);
            if(msg != null && !msg.isEmpty()){
                view.pushMessage(msg);
                view.messageSound();
            }
        }
    }
    
    /**
     * Sets the user state.
     * @param state New user state.
     * @throws UserOverflowException if messenger has no space for new users.
     */
    public void setState(UserState state) throws UserOverflowException{
        if(state == UserState.OFF && msn.getUser().getState() != UserState.OFF
         ||state != UserState.OFF && msn.getUser().getState() == UserState.OFF){
            if(state == UserState.OFF) stop();
            else run();
        }
        msn.setState(state);    
    }
    
    /**
     * Gets MSN user.
     * @return MSN user.
     */
    public User getUser(){
        return msn.getUser();
    }
    
    /**
     * Gets list of users in MSN at the moment.
     * @return Users list.
     */
    public User[] getUserList(){
        msn.checkUsers();
        return msn.getUserList();
    }
    
    /**
     * Updates MSN user.
     * @throws UserOverflowException if there is no space for the user.
     */
    public void updateUser() throws UserOverflowException{
        msn.getUser().update();
    }
    
    /**
     * Updates MSN user.
     * @param newid If it is true, a new user id will be found for the user.
     * @throws UserOverflowException if there is no space for the user.
     */
    public void updateUser(boolean newid) throws UserOverflowException{
        msn.getUser().update(newid);
    }
}
