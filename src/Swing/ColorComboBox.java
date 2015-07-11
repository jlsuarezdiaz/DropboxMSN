////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

/**
 * Class ColorComboBox.
 * Renderer for User State ComboBox.
 * @author Juan Luis
 */
public class ColorComboBox extends JPanel implements ListCellRenderer
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1L;
    
    /**
     * Array with the colors.
     */
    private Color[] colors;
    
    /**
     * Array with the strings.
     */
    private String[] strings;
    
    /**
     * Text panel.
     */
    JPanel textPanel;
    
    /**
     * Text.
     */
    JLabel text;

    /**
     * Constructor.
     * @param combo ComboBox. 
     */
    public ColorComboBox(JComboBox combo) {

        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

    /**
     * Set colors array.
     * @param col Array with the colors.
     */
    public void setColors(Color[] col)
    {
        colors = col;
    }

    /**
     * Set strings array.
     * @param str Array with the strings.
     */
    public void setStrings(String[] str)
    {
        strings = str;
    }

    /**
     * Get colors array.
     * @return Colors array. 
     */
    public Color[] getColors()
    {
        return colors;
    }

    /**
     * Get strings array.
     * @return Strings array.
     */
    public String[] getStrings()
    {
        return strings;
    }

    /**
     * Get List Cell Renderer Component.
     * @param list List.
     * @param value Component value.
     * @param index Component index.
     * @param isSelected Indicates if component is selected.
     * @param cellHasFocus Indicates if component is focused.
     * @return Component.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

    /*    if (isSelected)
        {
            setBackground(list.getSelectionBackground());
        }
        else
        {
            setBackground(Color.WHITE);
        }*/

        if (colors.length != strings.length)
        {
           // System.out.println("colors.length does not equal strings.length");
            return this;
        }
        else if (colors == null)
        {
            //System.out.println("use setColors first.");
            return this;
        }
        else if (strings == null)
        {
           // System.out.println("use setStrings first.");
            return this;
        }

        text.setBackground(getBackground());

        text.setText(value.toString());
        if (index>-1) {
            text.setForeground(colors[index]);
        }
        return text;
    }
}