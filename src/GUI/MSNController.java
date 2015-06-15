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
 *
 * @author Juan Luis
 */
public class MSNController {
    
    private MSNView view;
    
    private MSN msn;
    
    private boolean running;
    
    private Message[] read_buffers;
    
    //private Message[] private_buffers;
    
    private void initBuffers(){
        for(int i = 0; i < MSN.getMaxChannels(); i++){
            read_buffers[i] = msn.get(i);
        }
    }
    
    public MSNController(User u,MSNView view){
        msn = new MSN(u);
        this.view = view;
        running = false;
        view.enableMSNComponents(running);
        this.read_buffers = new Message[MSN.getMaxChannels()];
        //this.private_buffers = new Message[MSN.getMaxChannels()];
    }
    
    
    
    public void run(){
        initBuffers();
        running = true;
        view.enableMSNComponents(running);
        msn.send(new Message(msn.getUser().getName(),"",MessageKind.BEGIN));
    }
    
    public void stop(){
        running = false;
        view.enableMSNComponents(running);
        msn.send(new Message(msn.getUser().getName(),"",MessageKind.END));
        getUser().erase();
    }
    
    public void send(Message msg){
        msn.send(msg);
    }
    
    public void send(Message msg,User u){
        msn.send(msg,u);
    }
    
    public void reader(){
        for(int i = 0; i < MSN.getMaxChannels() && running; i++){
            Message msg = msn.get(i);
            if(msg != null && !msg.isEmpty() && !msg.equals(read_buffers[i])){
                read_buffers[i] = msg;
                view.pushMessage(msg);
            }
        }
    }
    
    public void privateReader(){
        for(int i = 0; i < MSN.getMaxChannels() && running; i++){
            Message msg = msn.getPrivate(i);
            if(msg != null && !msg.isEmpty()){
                view.pushMessage(msg);
            }
        }
    }
    
    public void setState(UserState state) throws UserOverflowException{
        if(state == UserState.OFF && msn.getUser().getState() != UserState.OFF
         ||state != UserState.OFF && msn.getUser().getState() == UserState.OFF){
            if(state == UserState.OFF) stop();
            else run();
        }
        msn.setState(state);
        
    }
    
    public User getUser(){
        return msn.getUser();
    }
    
    public User[] getUserList(){
        msn.checkUsers();
        return msn.getUserList();
    }
    
    public void updateUser() throws UserOverflowException{
        msn.getUser().update();
    }
}
