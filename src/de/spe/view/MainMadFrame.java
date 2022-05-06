package de.spe.view;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMadFrame extends JFrame{

	public MainMadFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100,30,800,800);
		
		this.setResizable(true);
		
		
		JPanel mainContent = new KeepRatioPanel();
		this.setContentPane(mainContent);
		
		
		
		
		ImageIcon icon = new ImageIcon("ressources/index.png");
		this.setIconImage(icon.getImage());
		
		new MenuBar(this);
		
		
		this.setTitle("SPE Mensch Ärgere Dich Nicht");
		this.setVisible(true);
		
		
		
	}

	
	
	
}
