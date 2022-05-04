import java.awt.Color;

public class MadJPanelBuilder {
    
    private MadJPanel panel;

    public MadJPanelBuilder()
    {
        panel = MadJPanel();
    }

    public MadJPanelBuilder setVisibility(boolean visibility)
    {
        panel.setVisible(visibility);
        return this;
    }

    public MadJPanelBuilder setColor(Color color)
    {
        panel.setBackground(color);
        return this;
    }

    public MadJPanelBuilder dies()
    {
        return this;
    }

    public MadJPanel build()
    {
        return panel;
    }
}
