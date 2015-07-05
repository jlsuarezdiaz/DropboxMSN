////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package GUI;

import Model.User;
import Model.UserOverflowException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Luis
 */
public class MSNIntro extends javax.swing.JDialog {

    /**
     * User's name.
     */
    User user;
    
    /**
     * Checks if the name is ok.
     */
    boolean validName;
    
    /**
     * Creates new form MSNIntro
     */
    public MSNIntro(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setValidName(false);
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelWelcome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelAuthor = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelEdition = new javax.swing.JLabel();
        labelCopyright = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        labelAskName = new javax.swing.JLabel();
        btStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dropbox MSN");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(350, 500));
        setMinimumSize(new java.awt.Dimension(350, 500));

        labelWelcome.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        labelWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelWelcome.setText("BIENVENIDO A DROPBOX MSN");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/msn_ultimate1.png"))); // NOI18N
        jLabel1.setToolTipText("");

        labelAuthor.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelAuthor.setForeground(new java.awt.Color(0, 0, 255));
        labelAuthor.setText("Un programa de: Juan Luis Suárez Díaz");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("v1.3");

        labelEdition.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelEdition.setForeground(new java.awt.Color(0, 0, 255));
        labelEdition.setText("Drobox MSN Ultimate Java Edition");

        labelCopyright.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelCopyright.setForeground(new java.awt.Color(0, 0, 255));
        labelCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCopyright.setText("© 2015");

        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUserNameKeyReleased(evt);
            }
        });

        labelAskName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAskName.setText("Introduzca un nombre para iniciar sesión:");

        btStart.setBackground(new java.awt.Color(0, 0, 255));
        btStart.setForeground(new java.awt.Color(255, 255, 255));
        btStart.setText("Iniciar sesión");
        btStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelEdition)
                                        .addComponent(labelAuthor))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelCopyright, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(labelAskName)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(btStart)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAuthor)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEdition)
                    .addComponent(labelCopyright))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelAskName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btStart)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void setValidName(boolean valid){
        validName = valid;
        btStart.setEnabled(validName);
    }
    
    private void performReadName(){
        try {
            user = new User(this.txtUserName.getText());
            this.dispose();
        } catch (UserOverflowException ex) {
            MSNView.showUserOverflowMsg(ex);
            System.exit(0);
        }
    }
    private void btStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartActionPerformed
        if(validName) performReadName();
    }//GEN-LAST:event_btStartActionPerformed

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        if(validName) performReadName();
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void txtUserNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyReleased
        setValidName(!txtUserName.getText().trim().isEmpty());
    }//GEN-LAST:event_txtUserNameKeyReleased

    public User getUser(){
        this.setVisible(true);
        return user;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelAskName;
    private javax.swing.JLabel labelAuthor;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JLabel labelEdition;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
