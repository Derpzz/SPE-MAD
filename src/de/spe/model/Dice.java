package de.spe.model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import de.spe.control.DiceAL;

public class Dice extends JButton{
	
	public Dice() {
		this.setText("6");
		this.setBackground(Colors.White.getColor());
		this.setForeground(Color.black);
		this.setPreferredSize(new Dimension(10,10));
		
		this.addActionListener(DiceAL.getInsance());
	}
}
