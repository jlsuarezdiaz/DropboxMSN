////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package dropboxmsn;

import GUI.MSNController;
import GUI.MSNIntro;
import GUI.MSNView;
import Model.User;

/**
 * Main program
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

/**
 * VERSIONS INFO:
 * 
 * Version 1.0 (15/6/2015): First program. Messenger basic functions (send, write, users, user states, private messaging)
 * and graphic interface.
 * 
 * Version 1.1 (17/6/2015):
 * - [!!!] Added sounds when receiving a message.
 * - Solved scroll problem.
 * - Solved own-user update missing.
 * - Improved user's name adjust design.
 * 
 * Version 1.2 (24/6/2015):
 * - Improved users management
 * - Allowed message selection (no further use at the moment).
 * - Added message dates.
 * 
 * Version 1.3 (5/7/2015):
 * - Added colors to user state menu in main view.
 * - Added sending after enter pressed mode.
 * 
 * Version 1.4 (6/7/2015):
 * - Added selected messages management (copy, paste, erase).
 * - Now copied messaages are added to system clipboard.
 * - Added settings menu.
 * 
 * Version 2.0 (9/7/2015):
 * - Improved sending at pressing enter, and the enabling of settings button.
 * - Added save messages option.
 * - Added menus about program info.
 */