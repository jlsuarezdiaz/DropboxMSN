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
import Swing.ColorComboBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * Class MSNView.
 * A messenger GUI.
 * @author Juan Luis
 */
public class MSNView extends javax.swing.JFrame {

    /**
     * MSN Controller.
     */
    private MSNController msn_ctrl;
   
    /**
     * Reader handler.
     */
    private final ActionListener taskReader;
    
    /**
     * Private reader handler.
     */
    private final ActionListener taskPrivateReader;
    
    /**
     * User updater handler.
     */
    private final ActionListener taskUserUpdater;
    
    /**
     * User list updater handler.
     */
    private final ActionListener taskListUserUpdater;
    
    /**
     * Reader timer.
     */
    private final Timer timerReader;
    
    /**
     * Private reader timer.
     */
    private final Timer timerPrivateReader;
    
    /**
     * User updater timer.
     */
    private final Timer timerUserUpdater;
    
    /**
     * User list updater timer.
     */
    private final Timer timerListUserUpdater;
    
    /**
     * State colors array.
     */
    private static final Color[] state_colors = {Color.GREEN,Color.ORANGE,Color.RED,Color.BLACK};
    
    /**
     * State names array.
     */
    private static final String[] state_names = {"ONLINE","ABSENT","BUSY","OFF"};
    
    
    /**
     * Checks if text is valid to be sent.
     */
    boolean validText;
    
    // ---------- SETTINGS ATTRIBUTES ---------- //
    /**
     * Allows sending after pressing Enter.
     */
    private boolean enterSendOption;
    
    /**
     * Text copied in clipboard.
     */
    private String clipboard;
    
    /**
     * Allows sound when receiving a message.
     */
    private boolean sound;
    
    
    
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
        TextMessage.setCaretPosition(0);
        setValidText(false);
    }
    
    /**
     * Updates the color of the user state combo box
     */
    private void updateStateBoxColor(){
            ComboUserState.setForeground(state_colors[ComboUserState.getSelectedIndex()]);
            Object child = ComboUserState.getAccessibleContext().getAccessibleChild(0);
            BasicComboPopup popup = (BasicComboPopup)child;
            JList list = popup.getList();
            list.setSelectionForeground(state_colors[ComboUserState.getSelectedIndex()]);
            list.setSelectionBackground(Color.WHITE);
    }
    
    /**
     * Enables or disables copying, pasting and removing buttons.
     */
    private void enableCopyButtons(){
        if(getSelectedMessages().isEmpty()){
            BtCopy.setEnabled(false);
            BtRemove.setEnabled(false);
        }
        else{
            BtCopy.setEnabled(true);
            BtRemove.setEnabled(true);
        }
        BtPaste.setEnabled(clipboard != null);
        repaint();
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
                MyUserPanel.setUser(msn_ctrl.getUser());
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
        

        ColorComboBox ColorBoxUserState = new ColorComboBox(ComboUserState);
        ColorBoxUserState.setColors(state_colors);
        ColorBoxUserState.setStrings(state_names);
        ComboUserState.setRenderer(ColorBoxUserState);
        updateStateBoxColor();
        
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
        
        //--- SETTINGS INITIALIZE ---//
        enterSendOption = true;
        
        sound = true;
        
        clipboard = null;
        
        enableCopyButtons();
    }

    /**
     * Shows MSNView.
     */
    public void showView(){
        this.setVisible(true);
        TextMessage.grabFocus();
        TextMessage.requestFocusInWindow();
        TextMessage.requestFocus();
    }
    
    /**
     * Set the view of MSN
     * @param msn msn_controller that will set the view.
     */
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
    
    /**
     * Enables or disables essential MSNView components.
     * @param enabled Determines enabling or disabling.
     */
    public void enableMSNComponents(boolean enabled){
        this.BtPrivate.setEnabled(enabled);
        setValidText(enabled && !TextMessage.getText().trim().isEmpty());
        //this.ComboUserState.setEnabled(enabled);
        this.MessagePanel.setEnabled(enabled);
        this.MyUserPanel.setEnabled(enabled);
        this.TextMessage.setEnabled(enabled);
        this.UsersPanel.setEnabled(enabled);
    }
    
    /**
     * Adds a new message to message panel.
     * @param msg Message to add.
     */
    public void pushMessage(Message msg){
        boolean isOnBottom = MessageScroll.getVerticalScrollBar().getValue() == 
                MessageScroll.getVerticalScrollBar().getMaximum()-MessageScroll.getVerticalScrollBar().getVisibleAmount();
        MessageView msgview = new MessageView();
        msgview.setMessage(msg);
        msgview.setVisible(true);
        msgview.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessagePanelMouseClicked(evt);
            }
        });
        
        MessagePanel.add(msgview);
        MessagePanel.repaint();
        MessagePanel.revalidate();
        
        MessageScroll.validate();
        
        if(isOnBottom){
            JScrollBar vertical = MessageScroll.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        }
        
        //MessageScroll.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
        //    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        //});
    }
    
    /**
     * Gets users on user list which are selected by the MSN user.
     * @return Array with selected users.
     */
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
    
    /**
     * Gets messages on message panel which are selected by the MSN user.
     * @return Array with selected messages.
     */
    public ArrayList<Message> getSelectedMessages(){
       MessageView mv;
       ArrayList<Message> msgs = new ArrayList();
       for(Component c : MessagePanel.getComponents()){
           try{
           mv = (MessageView) c;
           if(mv.isSelected())
               msgs.add(mv.getMessage());
           }
           catch(ClassCastException ex){
               msgs.remove(c);
           }
       }
       return msgs;
    }
    
    /**
     * Removes all messages from message panel.
     */
    public void clearMessages(){
        MessagePanel.removeAll();
        MessagePanel.repaint();
        MessagePanel.revalidate();
    }
    
    /**
     * Fills the user list.
     * @param user_list Array of users that will fill the panel.
     */
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
    
    /**
     * Updates user panel.
     * @param user_list Array of users that will update user panel.
     */
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
    
    /**
     * Forces the MSN user update, independently of the state of user updater handler.
     */
    public void updateUserForced(){
        try {
            msn_ctrl.updateUser(true);
            MyUserPanel.setUser(msn_ctrl.getUser());
        } catch (UserOverflowException ex) {
            MSNView.showUserOverflowMsg(ex);
        }
    }
    
    /**
     * Shows User Overflow exception message dialog.
     * @param ex User Overflow exception.
     */
    public static void showUserOverflowMsg(UserOverflowException ex){
        JOptionPane.showMessageDialog(null, "Dropbox MSN está desbordado en estos instantes. Inténtelo más tarde." +
            "\n[Error: " + ex.getMessage() + "]\n",
            "User Overflow",JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Performs message received sound, if sound setting allows it.
     */
    public void messageSound(){
        if(sound){
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            (getClass().getResource("/Media/MSNsound.wav")));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                //    System.err.println(e.getMessage());
                }
            }).start();
        }
    }
    
    /**
     * Enables or disables settings button.
     * @param b Determines if enabling of disabling.
     */
    public void enableSettingsButton(boolean b){
        BtSettings.setEnabled(b);
    }
    
    /**
     * Saves the given messages in a file.
     * @param address String with file's path.
     * @param msgs Array with messages to save. If null, it will contain the whole message panel.
     */
    public void saveMessage(String address, ArrayList<Message> msgs){
        FileWriter fw = null;
        
        if(msgs == null){
            msgs = new ArrayList();
            for(Component c: MessagePanel.getComponents()){
                try{
                    msgs.add(((MessageView)c).getMessage());
                }
                catch(ClassCastException ex){
                    MessagePanel.remove(c);
                }
            }
        }
        
        try{
            fw = new FileWriter(address);
            for(Message msg : msgs){
                fw.write(msg.toStringXL() + "\n");
            }
            
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo." +
            "\n[Error: " + ex.getMessage() + "]\n",
            "Save error",JOptionPane.ERROR_MESSAGE);
        }
        finally{
            try{
                if(fw != null) fw.close();
            }
            catch(IOException ex){}
        }
    }

    // ---------- SETTINGS ACCESSORS ---------- //
    /**
     * Gets the value of sending when pressing enter option.
     */
    public boolean getEnterSendOption(){
        return enterSendOption;
    }
    
    /**
     * Sets the value of sending when pressing enter option.
     */
    public void setEnterSendOption(boolean b){
        enterSendOption = b;
    }
    
    /**
     * Gets the value of sounding option.
     */
    public boolean getSound(){
        return sound;
    }
    
    /**
     * Sets the value of sounding option.
     */
    public void setSound(boolean b){
        sound = b;
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
        BtSettings = new javax.swing.JButton();
        BtCopy = new javax.swing.JButton();
        BtPaste = new javax.swing.JButton();
        BtRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dropbox MSN");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/msn_ultimate1.png")));
        setMaximumSize(new java.awt.Dimension(945, 591));
        setMinimumSize(new java.awt.Dimension(945, 591));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("USUARIOS CONECTADOS:");

        MyUserPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        MessageScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MessageScroll.setPreferredSize(new java.awt.Dimension(711, 1000000));

        MessagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MessagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessagePanelMouseClicked(evt);
            }
        });
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextMessageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextMessageKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TextMessage);

        BtSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/settings_icon_xs.png"))); // NOI18N
        BtSettings.setToolTipText("Configuración (Settings)");
        BtSettings.setNextFocusableComponent(TextMessage);
        BtSettings.setPreferredSize(new java.awt.Dimension(28, 28));
        BtSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSettingsActionPerformed(evt);
            }
        });

        BtCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/copy_icon.png"))); // NOI18N
        BtCopy.setToolTipText("Copiar (Copy)");
        BtCopy.setNextFocusableComponent(TextMessage);
        BtCopy.setPreferredSize(new java.awt.Dimension(28, 28));
        BtCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCopyActionPerformed(evt);
            }
        });

        BtPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/paste_icon.png"))); // NOI18N
        BtPaste.setToolTipText("Pegar (Paste)");
        BtPaste.setNextFocusableComponent(TextMessage);
        BtPaste.setPreferredSize(new java.awt.Dimension(28, 28));
        BtPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPasteActionPerformed(evt);
            }
        });

        BtRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/delete_icon.png"))); // NOI18N
        BtRemove.setToolTipText("Borrar (Remove)");
        BtRemove.setNextFocusableComponent(TextMessage);
        BtRemove.setPreferredSize(new java.awt.Dimension(28, 28));
        BtRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboUserState, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtPaste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(MessageScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(BtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtPrivate)))
                    .addComponent(UserScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(MessageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UserScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(BtPrivate)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(BtSend, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtPaste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboUserState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        MessageScroll.getAccessibleContext().setAccessibleName("");
        BtSettings.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * User state changed event.
     * @param evt 
     */
    private void ComboUserStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboUserStateActionPerformed
        try {
            updateStateBoxColor();
            /////////////////////////////////////////////
            
            msn_ctrl.setState((UserState) ComboUserState.getSelectedItem());
            MyUserPanel.setUser(msn_ctrl.getUser());
        } catch (UserOverflowException ex) {
            showUserOverflowMsg(ex);
        }
    }//GEN-LAST:event_ComboUserStateActionPerformed
    
    /**
     * Button sending event.
     * @param evt 
     */
    private void BtSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSendActionPerformed
        if(validText) performSend();
    }//GEN-LAST:event_BtSendActionPerformed

    /**
     * Button exit event.
     * @param evt 
     */
    private void BtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExitActionPerformed
        msn_ctrl.stop();
        System.exit(0);
    }//GEN-LAST:event_BtExitActionPerformed

    /**
     * Button private mode event.
     * @param evt 
     */
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

    /**
     * Key released event on message text area. Enables sending button or empties text area if needed.
     * @param evt 
     */
    private void TextMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMessageKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && enterSendOption){
            TextMessage.setText("");
        }
        setValidText(!TextMessage.getText().trim().isEmpty());
    }//GEN-LAST:event_TextMessageKeyReleased

    /**
     * Key pressed event on message text area. Sends the message if needed and allowed.
     * @param evt 
     */
    private void TextMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMessageKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && enterSendOption && validText){
            performSend();
        }
    }//GEN-LAST:event_TextMessageKeyPressed

    /**
     * Copy button event.
     * @param evt 
     */
    private void BtCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCopyActionPerformed
        ArrayList <Message> msgs = getSelectedMessages();
        clipboard = msgs.size()==0?null:new String();
        if(msgs.size() == 1 && 
                msgs.get(0).getKind() == MessageKind.PUBLIC || msgs.get(0).getKind() == MessageKind.PRIVATE){
            clipboard = msgs.get(0).getText();
        }
        else{
            for(Message m : msgs){
                clipboard += (m.toStringXL() + "\n");
            }
        }
        enableCopyButtons();
        
        //Copy to system clipboard.
        StringSelection stringSelection = new StringSelection (clipboard);
        Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        clpbrd.setContents (stringSelection, null);
    }//GEN-LAST:event_BtCopyActionPerformed

    /**
     * Paste button event.
     * @param evt 
     */
    private void BtPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPasteActionPerformed
        TextMessage.setText(TextMessage.getText() + clipboard);
        enableCopyButtons();
        setValidText(!TextMessage.getText().trim().isEmpty());
        
    }//GEN-LAST:event_BtPasteActionPerformed

    /**
     * Remove button event.
     * @param evt 
     */
    private void BtRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRemoveActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres borrar los mensajes? No podrás recuperarlos.",
                "Borrar mensajes", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(opt == JOptionPane.YES_OPTION){
            for(Component mv : MessagePanel.getComponents()){
                try{
                    if(((MessageView)mv).isSelected()){
                        MessagePanel.remove(mv);
                        mv.repaint();
                    }
                }
                catch(ClassCastException ex){
                    MessagePanel.remove(mv);
                }
            }
            MessagePanel.repaint();
            MessagePanel.revalidate();
            enableCopyButtons();
        }
    }//GEN-LAST:event_BtRemoveActionPerformed

    /**
     * Message panel clicked event. Allows message managing buttons if needed.
     * @param evt 
     */
    private void MessagePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessagePanelMouseClicked
        enableCopyButtons();
    }//GEN-LAST:event_MessagePanelMouseClicked

    /**
     * Settings button event. Shows settings view.
     * @param evt 
     */
    private void BtSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSettingsActionPerformed
        SettingsView sv = new SettingsView(this,false);
        enableSettingsButton(false);
        sv.showView(this);
    }//GEN-LAST:event_BtSettingsActionPerformed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCopy;
    private javax.swing.JButton BtExit;
    private javax.swing.JButton BtPaste;
    private javax.swing.JToggleButton BtPrivate;
    private javax.swing.JButton BtRemove;
    private javax.swing.JButton BtSend;
    private javax.swing.JButton BtSettings;
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
