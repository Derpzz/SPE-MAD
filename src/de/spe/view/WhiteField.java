package de.spe.view;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class WhiteField extends JPanel{

	public WhiteField() {
		
		this.setBackground(Colors.White.getColor());
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}
