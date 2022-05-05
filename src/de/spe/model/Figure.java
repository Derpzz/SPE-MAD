package de.spe.model;

import java.awt.Color;

import javax.swing.JButton;

import de.spe.control.FigureListener;

@SuppressWarnings("serial")
public class Figure extends JButton{
	
/*****Attribute*****/
	private int position;
	private Color color;
	private Area area;

	
/*****Constructor*****/
	public Figure(Color color, int position) {
		super();
		this.color = color;
		this.position = position;
		this.area = Area.Base;
		
		this.addActionListener(new FigureListener());
		this.setBorderPainted(true);
	}
	
/*****GetterAndSetter*****/
	public Color getColor() {
		return color;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int currentPosition) {
		this.position = currentPosition;
	}

	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	
}
