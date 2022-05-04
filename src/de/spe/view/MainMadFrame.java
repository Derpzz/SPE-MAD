package de.spe.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMadFrame extends JFrame{

	public MainMadFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100,30,900,900);
		
		
		JPanel mainContent = new MainPanel();
		this.setContentPane(mainContent);
		
		
		ImageIcon icon = new ImageIcon("ressources/index.png");
		this.setIconImage(icon.getImage());
		
		new MenuBar(this);
		
		
		this.setTitle("SPE Mensch Ärgere Dich Nicht");
		this.setVisible(true);
		
	}
	
}
