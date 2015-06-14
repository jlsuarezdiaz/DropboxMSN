////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Test;

import Model.MSN;
import Model.Message;
import Model.MessageKind;
import Model.User;
import Model.UserOverflowException;

/**
 *
 * @author Juan Luis
 */
public class test3 {
    public static void main(String[] args) throws UserOverflowException{
        User u1, u2;
        MSN msn1, msn2;
        u1 = new User("Pepe");
        u2 = new User("Pepa");
        msn1 = new MSN(u1);
        msn2 = new MSN(u2);
        Message m;
        
        msn1.send(new Message(u1.getName(),"Holaaaaaa xdxd",MessageKind.PUBLIC));
        m = msn2.get(0);
        System.out.println(m.toString());
        
        msn2.send(new Message(u2.getName(),"Holis xd",MessageKind.PRIVATE),u1);
        m = msn1.getPrivate(0);
        System.out.println(m.toString());
    }
}
