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
     * Indicates de message modality.
     */
    private MessageKind kind;
    
    /**
     * Message date.
     */
    private Date date;
    
    /**
     * Date Format.
     */
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
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
        this.sender = "".equals(sender)?" ":sender;
        this.text = "".equals(text)?" ":text;
        this.kind = kind;
        this.date = new Date();
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
     * 
     * @return date 
     */
    public Date getDate(){
        return date;
    }
    
    /**
     * 
     * @return Date format. 
     */
    public static DateFormat getDateFormat(){
        return df;
    }
    
    /**
     * Writes the message in a file.
     * @param filename File's name.
     */
    public void write(String filename){
        //FileWriter fw = null;
        OutputStreamWriter fw = null;
        try{
            //fw = new FileWriter(filename);
            fw = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");
            fw.write(sender + IO_LIM + text + IO_LIM + kind.toString() + IO_LIM + df.format(date) + IO_LIM);
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
                scan = new Scanner(f,"UTF-8");
                scan.useDelimiter(IO_LIM);
                sender = scan.next();
                text = scan.next();
                kind = MessageKind.valueOf(scan.next());
                date = df.parse(scan.next());
            } catch (FileNotFoundException ex){}
            // For invalid message reading.
            catch(NoSuchElementException | ParseException ex){ scan.close(); f.delete();}
            finally{
                if(scan != null) scan.close();
            }
            //System.out.println("Leido OK " + filename);
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
    
    /**
     * Obtains a string with all the message information.
     * @return String with message info.
     */
    public String toStringXL(){
        return "[" + getDateFormat().format(getDate()) + "] " + toString();
    }
    
    /**
     * Message comparer.
     * @param obj Object to compare
     * @return true if and only if messages are equal.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(!(obj.getClass().getSimpleName().equals("Message")))
            return false;
        
        Message msg = (Message) obj;
        return this.sender.equals(msg.getSender()) && this.text.equals(msg.getText()) 
                && this.kind.equals(msg.getKind()) && this.date.equals(msg.getDate());
    }
}
