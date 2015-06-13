////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Test;

import Model.Message;

/**
 *
 * @author Juan Luis
 */
public class test2 {
    
    public static void main(String[] args){
        Message a = new Message("Pepe","Holaaaaaaa xd",true);
        System.out.println(a.toString());
        Message b = new Message("Pepa","Holas xddd",false);
        System.out.println(b.toString());
        a.write("a.msg");
        b.write("b.msg");
        a.read("b.msg");
        b.read("a.msg");
        System.out.println(a.toString());
        System.out.println(b.toString());
        
    }
}
    

