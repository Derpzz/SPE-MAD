package de.spe.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainMadFrame extends JFrame{
	
	private KeepRatioPanel mainContent;

	public MainMadFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100,30,600,600);
		
		this.setResizable(true);
		
//		mainContent = new BoardPanel();
		mainContent = new KeepRatioPanel();
		this.setContentPane(mainContent);
		
		
		
		
		ImageIcon icon = new ImageIcon("ressources/index.png");
		this.setIconImage(icon.getImage());
		
		new MenuBar(this);
		
		
		this.setTitle("SPE Mensch Ärgere Dich Nicht");
		this.setVisible(true);
		
	}

	
	/*
	 * Getter and Setter
	 */

	public KeepRatioPanel getMainContent() {
		return mainContent;
	}
	
	
	
}
