package de.spe.view;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class BlueField extends JPanel{

	public BlueField() {
		
		this.setBackground(Colors.BlueField.getColor());
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}