import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
    JPanel[] baseY;
    JPanel[] baseG;
    JPanel[] baseB;
    JPanel[] baseR;

    JPanel[] homeY;
    JPanel[] homeG;
    JPanel[] homeB;
    JPanel[] homeR;
    
    private static final Color[] colors = {Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED};

    public MainFrame()
    {     	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,30,900,900);
        
        JPanel main = new JPanel();
        
        main.setLayout(new GridLayout(11,11,5,5));

        baseY = new JPanel[4];
        baseG = new JPanel[4];
        baseB = new JPanel[4];
        baseR = new JPanel[4];
        homeY = new JPanel[4];
        homeG = new JPanel[4];
        homeB = new JPanel[4];
        homeR = new JPanel[4];
        

        for(Color color : colors)
        {
            for(int i = 2; i > 0; i--)
            {
                for(int j = 4; j > 0; j--)
                {
                    
                }
            }
        }
        MadJPanel invisble =  new MadJPanel(Color.black);

        JPanel[] playField = new JPanel[60];

        int j = 4;
        for(int i = 0; i < 121; i++)
        {
            JPanel panel = new JPanel();
            panel.setBackground(Color.RED);
            if(i==j)
            {
                panel.setBackground(Color.WHITE);
                j = j + 11;
            }

            if(i == 60)
                panel.setBackground(Color.ORANGE);
            main.add(panel);
            //main.add(new JPanel());
        }
        //System.out.println(panel);

        this.add(main);
        
        this.setTitle("Java Quiz Master 2022");
        this.setVisible(true);
    }

    
    
    
}
