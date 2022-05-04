package de.spe.view;
import javax.swing.JPanel;
import java.awt.Color;

public class MadJPanel extends JPanel 
{
    public MadJPanel(Color color)
    {
        super();
        this.setBackground(color);
    }

    public MadJPanel(boolean visibility)
    {
        super();
        this.setVisible(visibility);
    }

    public MadJPanel(boolean visibility, Color color)
    {
        super();
        this.setVisible(visibility);
        this.setBackground(color);
    }
}
