////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

/**
 * Class UserOverflowException.
 * Exception to throw when user's space is full.
 * @author Juan Luis
 */
public class UserOverflowException extends Exception{
    /**
     * Default constructor.
     */
    public UserOverflowException(){
        
    }
    
    /**
     * Constructor by string.
     * @param message String with the exception message. 
     */
    public UserOverflowException(String message){
        super(message);
    }
}
