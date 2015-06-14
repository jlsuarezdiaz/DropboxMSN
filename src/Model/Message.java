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
import java.text.ParseException;
import java.util.NoSuchElementException;
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
            // For invalid message reading.
            catch(NoSuchElementException ex){ scan.close(); f.delete();}
            finally{
                scan.close();
            }
            System.out.println("Leido OK " + filename);
        }
    }
    
    /**
     * Checks if a message is empty.
     * @return 
     */
    public boolean isEmpty(){
        return ("".equals(this.sender) || " ".equals(this.sender)) && ("".equals(this.text) || " ".equals(this.text));
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
        else if(kind == MessageKind.JUSTTEXT)
            ret = text;
        
        return ret;
        //return ((isPublic)?(sender + " dice: "):("Mensaje privado de " + sender + ": ")) + text;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(!(obj.getClass().getSimpleName().equals("Message")))
            return false;
        
        Message msg = (Message) obj;
        return this.sender.equals(msg.getSender()) && this.text.equals(msg.getText()) && this.kind.equals(msg.getKind());
    }
}
