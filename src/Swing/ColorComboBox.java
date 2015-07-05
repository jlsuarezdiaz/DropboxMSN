////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;


public class ColorComboBox extends JPanel implements ListCellRenderer
{

    private static final long serialVersionUID = -1L;
    private Color[] colors;
    private String[] strings;

    JPanel textPanel;
    JLabel text;

    public ColorComboBox(JComboBox combo) {

        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

    public void setColors(Color[] col)
    {
        colors = col;
    }

    public void setStrings(String[] str)
    {
        strings = str;
    }

    public Color[] getColors()
    {
        return colors;
    }

    public String[] getStrings()
    {
        return strings;
    }

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