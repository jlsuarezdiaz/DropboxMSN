////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package dropboxmsn;

import GUI.MSNController;
import GUI.MSNIntro;
import GUI.MSNView;
import Model.MSN;
import Model.User;

/**
 *
 * @author Juan Luis
 */
public class Main {
    public static void main(String[] args){
        MSNView dropbox_msn_view = new MSNView();
        MSNIntro intro = new MSNIntro(dropbox_msn_view,true);
        
        User myUser = intro.getUser();
        
        //MSN dropbox_msn = new MSN(myUser);
        MSNController msn_ctrl = new MSNController(myUser, dropbox_msn_view);
        dropbox_msn_view.setMSN(msn_ctrl);
        
        dropbox_msn_view.showView();
        msn_ctrl.run();
    }
}
