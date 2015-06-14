////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
    private MessageKind kind;
    
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
    private void set(String sender, String text, MessageKind kind){
        this.sender = sender;
        this.text = text;
        this.kind = kind;
    }
    
    /**
     * Default constructor.
     */
    public Message(){
        set("","",MessageKind.PUBLIC);
    }
    /**
     * Constructor.
     * @param sender
     * @param text
     * @param isPublic 
     */
    public Message(String sender, String text, MessageKind kind){
        set(sender,text,kind);
    }
    
    /**
     * Constructor. By default, a message is public.
     * @param sender
     * @param text 
     */
    public Message(String sender, String text){
        set(sender,text,MessageKind.PUBLIC);
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
    public MessageKind getKind(){
        return kind;
    }
    
    /**
     * Writes the message in a file.
     * @param filename File's name.
     */
    public void write(String filename){
        FileWriter fw = null;
        try{
            fw = new FileWriter(filename);
            fw.write(sender + IO_LIM + text + IO_LIM + kind.toString() + IO_LIM);
        }
        catch(IOException ex){}
        finally{
            try{
                if(fw != null) fw.close();
            }
            catch(IOException ex){}
        }
    }
    
    /**
     * Reads the message from a file.
     * @param filename File's name.
     */
    public void read(String filename){
        Scanner scan = null;
        File f = new File(filename);
        
        if(f.exists()){
            try {
                scan = new Scanner(f);
                scan.useDelimiter(IO_LIM);
                sender = scan.next();
                text = scan.next();
                kind = MessageKind.valueOf(scan.next());
            } catch (FileNotFoundException ex){}           
        }
    }
    
    /**
     * Obtains a string with the message.
     * @return String with the message.
     */
    @Override
    public String toString(){
        String ret = "";
        if(kind == MessageKind.PUBLIC)
            ret = (sender + " dice: " + text);
        else if(kind == MessageKind.PRIVATE)
            ret = ("Mensaje privado de " + sender + ": " + text);
        else if(kind == MessageKind.BEGIN)
            ret = (sender + " ha iniciado sesión.");
        else if(kind == MessageKind.END)
            ret = (sender + " se ha desconectado.");
        return ret;
        //return ((isPublic)?(sender + " dice: "):("Mensaje privado de " + sender + ": ")) + text;
    }
}
