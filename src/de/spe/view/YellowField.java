package de.spe.view;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class YellowField extends JPanel{

	public YellowField() {
		
		this.setBackground(Colors.YellowField.getColor());
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}