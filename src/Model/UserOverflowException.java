////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

/**
 * Class UserOverflowException.
 * @author Juan Luis
 */
public class UserOverflowException extends Exception{
    public UserOverflowException(){
        
    }
    
    public UserOverflowException(String message){
        super(message);
    }
}
