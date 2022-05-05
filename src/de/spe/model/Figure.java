package de.spe.model;

import java.awt.Color;

import javax.swing.JButton;

public class Figure extends JButton{
/*****Attribute*****/
	int currentPosition;
	Color color;
	
/*****Constructor*****/
	public Figure(Color color, int currentPosition) {
		super();
		this.color = color;
		this.currentPosition = currentPosition;
		
		this.setBackground(color);
		this.setBorderPainted(true);
		this.setEnabled(false);
	}
}
