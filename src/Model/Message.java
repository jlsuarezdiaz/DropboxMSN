////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

/**
 * Class message. Contains all the information relative to a message.
 * @author Juan Luis
 */
public class Message {
    /**
     * Sender's name
     */
    private String sender;
    
    /**
     * Message contents.
     */
    private String text;
    
    /**
     * Indicates whether message is public.
     */
    private boolean isPublic;
    
    /**
     * Input/Output delimiter.
     */
    private static final String IO_LIM = "\0";
    
    /**
     * Private method to set message attributes.
     * @param sender
     * @param text
     * @param isPublic 
     */
    private void set(String sender, String text, boolean isPublic){
        this.sender = sender;
        this.text = text;
        this.isPublic = isPublic;
    }
    
    /**
     * Default constructor.
     */
    public Message(){
        set("","",true);
    }
    /**
     * Constructor.
     * @param sender
     * @param text
     * @param isPublic 
     */
    public Message(String sender, String text, boolean isPublic){
        set(sender,text,isPublic);
    }
    
    /**
     * Constructor. By default, a message is public.
     * @param sender
     * @param text 
     */
    public Message(String sender, String text){
        set(sender,text,true);
    }
    
    /**
     * 
     * @return sender 
     */
    public String getSender(){
        return sender;
    }
    
    /**
     * 
     * @return text. 
     */
    public String getText(){
        return text;
    }
    
    /**
     * 
     * @return isPublic
     */
    public boolean isPublic(){
        return isPublic;
    }
    
    /**
     * Writes the message in a file.
     * @param filename 
     */
    public void write(String filename){
        
    }
    /**
     * Obtains a string with the message.
     * @return String with the message.
     */
    public String toString(){
        return ((isPublic)?(sender + " dice: "):("Mensaje privado de " + sender + ": ")) + text;
    }
}
