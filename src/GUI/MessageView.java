////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package GUI;

import Model.Message;
import java.awt.Color;
import javax.swing.BorderFactory;

/**
 *
 * @author Juan Luis
 */
public class MessageView extends javax.swing.JPanel {

    /**
     * Message.
     */
    private Message messageModel;
    
    /**
     * Selected checker.
     */
    boolean isSelected;
    
    
    private void setBackground(){
        if(isSelected){
            this.setBackground(new Color(0x00FFFF));
        }
        else{
            this.setBackground(new Color(0xF0F0F0));
        }
    }
    
    /**
     * @return true if and only if the user is selected.
     */
    public boolean isSelected(){
        return isSelected;
    }
    
    /**
     * Selects or unselects the view.
     * @param selection Boolean indicating selection or not.
     */
    public void select(boolean selection){
        isSelected = selection;
        setBackground();
        repaint();
    }
    
    /**
     * Creates new form MessageView
     */
    public MessageView() {
        initComponents();
    }

    /**
     * Set the view for a message.
     * @param m Message to set.
     */
    public void setMessage(Message m){
        select(false);
        this.messageModel = m;
        this.labelText.setText(messageModel.toString());
        repaint();
    }
    
    public Message getMessage(){
        return messageModel;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        labelText = new javax.swing.JEditorPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(675, 75));
        setMinimumSize(new java.awt.Dimension(675, 75));
        setPreferredSize(new java.awt.Dimension(675, 75));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        labelText.setEditable(false);
        labelText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        labelText.setMaximumSize(new java.awt.Dimension(675, 2147483647));
        labelText.setMinimumSize(new java.awt.Dimension(675, 14));
        jScrollPane2.setViewportView(labelText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        select(!isSelected);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JEditorPane labelText;
    // End of variables declaration//GEN-END:variables
}
