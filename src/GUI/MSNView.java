////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package GUI;

import Model.Message;
import Model.MessageKind;
import Model.User;
import Model.UserOverflowException;
import Model.UserState;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Juan Luis
 */
public class MSNView extends javax.swing.JFrame {

    /**
     * MSN Controller.
     */
    private MSNController msn_ctrl;
   
    ActionListener taskReader;
    
    ActionListener taskPrivateReader;
    
    ActionListener taskUserUpdater;
    
    ActionListener taskListUserUpdater;
    
    Timer timerReader;
    
    Timer timerPrivateReader;
    
    Timer timerUserUpdater;
    
    Timer timerListUserUpdater;
    
    /**
     * Checks if text is valid to be sent.
     */
    boolean validText;
    
    /**
     * Set valid text and enables components if necessary.
     * @param valid Boolean to set valid text.
     */
    private void setValidText(boolean valid){
        this.validText = valid;
        BtSend.setEnabled(validText);
    }
    
    /**
     * Action of sending a message.
     */
    private void performSend(){
        if(BtPrivate.isSelected()){
            ArrayList <User> dest = getSelectedUsers();
            Message msg = new Message(msn_ctrl.getUser().getName(),TextMessage.getText(),MessageKind.PRIVATE);
            for(User u : dest){
                msn_ctrl.send(msg,u);
                pushMessage(new Message("","Enviaste a " + u.getName()+ ": " + TextMessage.getText(),MessageKind.JUSTTEXT));
            }
            
        }
        else{
            Message msg = new Message(msn_ctrl.getUser().getName(),TextMessage.getText(),MessageKind.PUBLIC);
            msn_ctrl.send(msg);
        }
        TextMessage.setText("");
        setValidText(false);
    }
    /**
     * Creates new form MSNView
     */
    public MSNView() {
        this.taskReader = (ActionEvent evt) -> {
            msn_ctrl.reader();
        };
        
        this.taskPrivateReader = (ActionEvent evt) -> {
            msn_ctrl.privateReader();
        };
        
        this.taskUserUpdater = (ActionEvent evt) -> {
            try {
                msn_ctrl.updateUser();
            } catch (UserOverflowException ex) {
                 showUserOverflowMsg(ex);
                 msn_ctrl.stop();
            }
        };
        
        this.taskListUserUpdater = (ActionEvent evt) -> {
            //fillUserPanel(msn_ctrl.getUserList());
            updateUserPanel(msn_ctrl.getUserList());
        };
        
        initComponents();
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                msn_ctrl.stop();
                System.exit(0);
            }
        });
                
        timerReader = new Timer(100, taskReader);
        timerPrivateReader = new Timer(100,taskPrivateReader);
        timerUserUpdater = new Timer(200000,taskUserUpdater);
        timerListUserUpdater = new Timer(3000,taskListUserUpdater);
        
    }

    public void showView(){
        this.setVisible(true);
        TextMessage.grabFocus();
        TextMessage.requestFocusInWindow();
        TextMessage.requestFocus();
    }
    
    public void setMSN(MSNController msn){
        this.msn_ctrl = msn;
        MyUserPanel.setUser(msn_ctrl.getUser());
        MyUserPanel.repaint();
        fillUserPanel(msn_ctrl.getUserList());
        repaint();
        timerReader.start();
        timerPrivateReader.start();
        timerUserUpdater.start();
        timerListUserUpdater.start();
    }
    
    public void enableMSNComponents(boolean enabled){
        this.BtPrivate.setEnabled(enabled);
        setValidText(enabled && !TextMessage.getText().trim().isEmpty());
        //this.ComboUserState.setEnabled(enabled);
        this.MessagePanel.setEnabled(enabled);
        this.MyUserPanel.setEnabled(enabled);
        this.TextMessage.setEnabled(enabled);
        this.UsersPanel.setEnabled(enabled);
    }
    
    public void pushMessage(Message msg){
        MessageView msgview = new MessageView();
        msgview.setMessage(msg);
        msgview.setVisible(true);
        MessagePanel.add(msgview);
        MessagePanel.repaint();
        MessagePanel.revalidate();
        MessageScroll.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });
    }
    
    public ArrayList<User> getSelectedUsers(){
       UserView uv;
       ArrayList<User> users = new ArrayList();
       for(Component c : UsersPanel.getComponents()){
           uv = (UserView) c;
           if(uv.isSelected())
               users.add(uv.getUser());
       }
       return users;
    }
    
    public void fillUserPanel(User[] user_list){
        UsersPanel.removeAll();
        for(User u: user_list){
            UserView uv = new UserView();
            uv.setUser(u);
            UsersPanel.add(uv);
        //    UsersPanel.add(Box.createRigidArea(new Dimension(5,0)));
        }
        UsersPanel.repaint();
        UsersPanel.revalidate();
    }
    
    public void updateUserPanel(User[] user_list){
        boolean selection_now;
        //UserView view;
        for(int i = 1; i < User.getMaxUsers(); i++){
            selection_now = ((UserView)UsersPanel.getComponent(i)).isSelected();
            ((UserView)UsersPanel.getComponent(i)).setUser(user_list[i]);
            if(user_list[i].validState())
                ((UserView)UsersPanel.getComponent(i)).select(selection_now);
        //    view = ((UserView)UsersPanel.getComponent(i));
        //    selection_now = view.isSelected();
        //    view.setUser(user_list[i]);
        //   view.select(selection_now);   
        }
        UsersPanel.repaint();
    }
    
    public static void showUserOverflowMsg(UserOverflowException ex){
        JOptionPane.showMessageDialog(null, "Dropbox MSN está desbordado en estos instantes. Inténtelo más tarde." +
            "\n[Error: " + ex.getMessage() + "]\n",
            "User Overflow",JOptionPane.ERROR_MESSAGE);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        MyUserPanel = new GUI.UserView();
        MessageScroll = new javax.swing.JScrollPane();
        MessagePanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        UserScroll = new javax.swing.JScrollPane();
        UsersPanel = new javax.swing.JPanel();
        ComboUserState = new javax.swing.JComboBox();
        BtSend = new javax.swing.JButton();
        BtPrivate = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        BtExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextMessage = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dropbox MSN");
        setMaximumSize(new java.awt.Dimension(945, 591));
        setMinimumSize(new java.awt.Dimension(945, 591));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("USUARIOS CONECTADOS:");

        MyUserPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        MessageScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MessageScroll.setPreferredSize(new java.awt.Dimension(711, 1000000));

        MessagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MessagePanel.setLayout(new javax.swing.BoxLayout(MessagePanel, javax.swing.BoxLayout.PAGE_AXIS));

        jToolBar1.setRollover(true);
        MessagePanel.add(jToolBar1);

        MessageScroll.setViewportView(MessagePanel);

        UserScroll.setMaximumSize(new java.awt.Dimension(204, 32767));
        UserScroll.setPreferredSize(new java.awt.Dimension(204, 1000000));

        UsersPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        UsersPanel.setNextFocusableComponent(TextMessage);
        UsersPanel.setLayout(new javax.swing.BoxLayout(UsersPanel, javax.swing.BoxLayout.PAGE_AXIS));
        UsersPanel.setLayout(new javax.swing.BoxLayout(UsersPanel, javax.swing.BoxLayout.PAGE_AXIS));
        UserScroll.setViewportView(UsersPanel);

        ComboUserState.setModel(new DefaultComboBoxModel<>(UserState.values()));
        ComboUserState.setNextFocusableComponent(TextMessage);
        ComboUserState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboUserStateActionPerformed(evt);
            }
        });

        BtSend.setBackground(new java.awt.Color(0, 204, 51));
        BtSend.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        BtSend.setForeground(new java.awt.Color(255, 255, 255));
        BtSend.setText("SEND");
        BtSend.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtSend.setNextFocusableComponent(TextMessage);
        BtSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSendActionPerformed(evt);
            }
        });

        BtPrivate.setBackground(new java.awt.Color(204, 0, 204));
        BtPrivate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtPrivate.setForeground(new java.awt.Color(255, 255, 255));
        BtPrivate.setText("Private mode");
        BtPrivate.setToolTipText("Active este botón para iniciar la mensajería privada.");
        BtPrivate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtPrivate.setContentAreaFilled(false);
        BtPrivate.setNextFocusableComponent(TextMessage);
        BtPrivate.setOpaque(true);
        BtPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPrivateActionPerformed(evt);
            }
        });

        jLabel2.setText("Seleccione un estado:");

        BtExit.setBackground(new java.awt.Color(255, 0, 0));
        BtExit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        BtExit.setForeground(new java.awt.Color(255, 255, 255));
        BtExit.setText("EXIT");
        BtExit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExitActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TextMessage.setColumns(20);
        TextMessage.setLineWrap(true);
        TextMessage.setRows(5);
        TextMessage.setNextFocusableComponent(TextMessage);
        TextMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextMessageKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TextMessage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboUserState, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(MessageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BtExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtPrivate))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(484, 484, 484)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboUserState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MessageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(UserScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtPrivate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(BtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        MessageScroll.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ComboUserStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboUserStateActionPerformed
        try {
            msn_ctrl.setState((UserState) ComboUserState.getSelectedItem());
            MyUserPanel.setUser(msn_ctrl.getUser());
        } catch (UserOverflowException ex) {
            showUserOverflowMsg(ex);
        }
    }//GEN-LAST:event_ComboUserStateActionPerformed

    private void BtSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSendActionPerformed
        if(validText) performSend();
    }//GEN-LAST:event_BtSendActionPerformed

    private void BtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExitActionPerformed
        msn_ctrl.stop();
        System.exit(0);
    }//GEN-LAST:event_BtExitActionPerformed

    private void BtPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPrivateActionPerformed
        if(BtPrivate.isSelected()){
            BtPrivate.setToolTipText("El modo privado está activado. Seleccione a los usuarios de la lista "+
                 "con los que desea hablar");
            BtPrivate.setBackground(new Color(0xFFC000));
        }else{
            BtPrivate.setToolTipText("Active este botón para iniciar la mensajería privada.");
            BtPrivate.setBackground(new Color(0xCC00CC));
        }       
    }//GEN-LAST:event_BtPrivateActionPerformed

    private void TextMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMessageKeyReleased
        setValidText(!TextMessage.getText().trim().isEmpty());
    }//GEN-LAST:event_TextMessageKeyReleased

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtExit;
    private javax.swing.JToggleButton BtPrivate;
    private javax.swing.JButton BtSend;
    private javax.swing.JComboBox ComboUserState;
    private javax.swing.JPanel MessagePanel;
    private javax.swing.JScrollPane MessageScroll;
    private GUI.UserView MyUserPanel;
    private javax.swing.JTextArea TextMessage;
    private javax.swing.JScrollPane UserScroll;
    private javax.swing.JPanel UsersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
