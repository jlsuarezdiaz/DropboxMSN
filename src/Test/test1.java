////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Test;

import Model.User;
import Model.UserOverflowException;
import Model.UserState;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;

/**
 *
 * @author Juan Luis
 */
public class test1 {
    public static void main(String[] args) throws InterruptedException, UserOverflowException{
      //  FileWriter f = new FileWriter("Hola.txt");
      //  f.write("XDDDD");
        User a = new User("pepe");
        System.out.println(a.toString());
        sleep(10000);
        a.read();
        System.out.println(a.toString());
        a.changeState(UserState.BUSY);
        System.out.println(a.toString());
        a.read();
        System.out.println(a.toString());
        a.write();
    }
}
