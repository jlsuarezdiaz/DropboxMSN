////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package GUI;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Luis
 */
public class SettingsView extends javax.swing.JDialog {
    /**
     * MSNView to set.
     */
    private MSNView view;
    
    private void setView(MSNView mv){
        this.view = mv;
        this.ChkEnterSend.setSelected(view.getEnterSendOption());
        this.ChkSound.setSelected(view.getSound());
    }
    
    
    /**
     * Creates new form SettingsView
     */
    public SettingsView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                ((MSNView)parent).enableSettingsButton(true);
                dispose();
            }
        });
    }

    public void showView(MSNView mv){
        this.setView(mv);
        this.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        ChkEnterSend = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtClear = new javax.swing.JButton();
        ChkSound = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        txtCheat = new javax.swing.JTextField();
        BtCheat = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        BtUpdateUser = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        BtSaveAll = new javax.swing.JButton();
        BtSaveSelected = new javax.swing.JButton();
        BtAbout = new javax.swing.JButton();
        BtVersions = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dropbox MSN (Settings)");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/settings_icon_xs.png")));
        setResizable(false);

        ChkEnterSend.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkEnterSend.setText("Enviar al pulsar Intro.");
        ChkEnterSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkEnterSendActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel1.setText("<html> (Si la casilla está activada, los mensajes se enviarán al pulsar la tecla Intro). <html>");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Borrar panel de mensajes.");

        BtClear.setText("Borrar");
        BtClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtClearActionPerformed(evt);
            }
        });

        ChkSound.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkSound.setText("Sonido");
        ChkSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkSoundActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Cheats: ");

        BtCheat.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtCheat.setText("OK");
        BtCheat.setMinimumSize(new java.awt.Dimension(43, 22));
        BtCheat.setPreferredSize(new java.awt.Dimension(43, 22));
        BtCheat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCheatActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("<html>  Pulsa el siguiente botón si observas problemas a la hora de mostrar tu usuario a los demás: <html>");

        BtUpdateUser.setText("Forzar actualización");
        BtUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtUpdateUserActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Guardar historial de conversaciones:");

        BtSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/save_icon.png"))); // NOI18N
        BtSaveAll.setToolTipText("Guardar todos los mensajes.");
        BtSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSaveAllActionPerformed(evt);
            }
        });

        BtSaveSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/save_icon_selected.png"))); // NOI18N
        BtSaveSelected.setToolTipText("Guardar solo mensajes seleccionados.");
        BtSaveSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSaveSelectedActionPerformed(evt);
            }
        });

        BtAbout.setText("Acerca de Dropbox MSN");
        BtAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAboutActionPerformed(evt);
            }
        });

        BtVersions.setText("Versiones");
        BtVersions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtVersionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(BtAbout)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtVersions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(BtClear, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(ChkEnterSend)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCheat, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BtCheat, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(ChkSound)
                                .addComponent(BtUpdateUser)
                                .addComponent(jLabel5)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(BtSaveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(BtSaveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChkEnterSend)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtClear)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ChkSound)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCheat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(BtCheat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtUpdateUser)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtSaveAll)
                    .addComponent(BtSaveSelected))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtAbout)
                    .addComponent(BtVersions))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ChkEnterSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkEnterSendActionPerformed
        view.setEnterSendOption(ChkEnterSend.isSelected());
    }//GEN-LAST:event_ChkEnterSendActionPerformed

    private void BtClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtClearActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres borrar los mensajes? No podrás recuperarlos.",
                "Borrar mensajes", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            view.clearMessages();
    }//GEN-LAST:event_BtClearActionPerformed

    private void ChkSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkSoundActionPerformed
        view.setSound(ChkSound.isSelected());
    }//GEN-LAST:event_ChkSoundActionPerformed

    private void BtCheatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCheatActionPerformed
        txtCheat.setText("");
    }//GEN-LAST:event_BtCheatActionPerformed

    private void BtUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtUpdateUserActionPerformed
        view.updateUserForced();
    }//GEN-LAST:event_BtUpdateUserActionPerformed

    private void BtSaveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSaveAllActionPerformed
        JFileChooser fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            view.saveMessage(fc.getSelectedFile().getAbsolutePath(), null);
        }
    }//GEN-LAST:event_BtSaveAllActionPerformed

    private void BtSaveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSaveSelectedActionPerformed
        JFileChooser fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            view.saveMessage(fc.getSelectedFile().getAbsolutePath(), view.getSelectedMessages());
        }
    }//GEN-LAST:event_BtSaveSelectedActionPerformed

    private void BtAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAboutActionPerformed
        JOptionPane.showMessageDialog(this,Data.Txt.EDITION + "\n\n" + Data.Txt.PROGRAM_INFO + "\n\nAutor: " +
                Data.Txt.AUTHOR + "\nDiseño: " + Data.Txt.AUTHOR + "\nSonido: " + Data.Txt.AUTHOR +"\nProgramación: " +
                Data.Txt.AUTHOR + "\n\n" +  Data.Txt.COPYRIGHT + "        " + Data.Txt.VERSION,
                "Acerca de Dropbox MSN", JOptionPane.INFORMATION_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Media/msn_ultimate1.png")));         
    }//GEN-LAST:event_BtAboutActionPerformed

    
    private void BtVersionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtVersionsActionPerformed
        JOptionPane.showMessageDialog(this,
                "VERSIÓN ACTUAL:\n" + Data.Txt.LAST_VERSION_INFO + "VERSIONES ANTERIORES:\n" + Data.Txt.OLD_VERSIONS_INFO,
                "Versiones",JOptionPane.INFORMATION_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Media/msn_ultimate1.png")));
    }//GEN-LAST:event_BtVersionsActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAbout;
    private javax.swing.JButton BtCheat;
    private javax.swing.JButton BtClear;
    private javax.swing.JButton BtSaveAll;
    private javax.swing.JButton BtSaveSelected;
    private javax.swing.JButton BtUpdateUser;
    private javax.swing.JButton BtVersions;
    private javax.swing.JCheckBox ChkEnterSend;
    private javax.swing.JCheckBox ChkSound;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtCheat;
    // End of variables declaration//GEN-END:variables
}
