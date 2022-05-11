package de.spe.view;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class RedField extends JPanel{

	public RedField() {
		
		this.setBackground(Colors.RedField.getColor());
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}