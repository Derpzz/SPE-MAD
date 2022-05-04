package de.spe.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	
	public MainPanel() {
		
		
		this.setLayout(new GridLayout(11,11,5,5));
		
		
		JPanel[] fieldPanelPosition = new JPanel[40];
		
		JPanel[] baseY = new JPanel[4];
		JPanel[] baseG = new JPanel[4];
		JPanel[] baseB = new JPanel[4];
		JPanel[] baseR = new JPanel[4];
		
		JPanel[] homeY = new JPanel[4];
		JPanel[] homeG = new JPanel[4];
		JPanel[] homeB = new JPanel[4];
		JPanel[] homeR = new JPanel[4];
		
		
		for(int i = 0; i < 121; i++) {
			InvisiblePanel x = new InvisiblePanel();
			this.add(x);
			
			if(i==1) {
				
			}
		}
		
	
		
	}
}
