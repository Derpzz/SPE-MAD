package de.spe.view;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class RedField extends JPanel{

	public RedField() {
		
		this.setBackground(Color.RED);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}