package de.spe.view;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class WhiteField extends JPanel{

	public WhiteField() {
		
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}
