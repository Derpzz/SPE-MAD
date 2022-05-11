package de.spe.view;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class GreenField extends JPanel{

	public GreenField() {
		
		this.setBackground(Colors.GreenField.getColor());
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}
	
	
}
