package de.spe.model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import de.spe.control.FigureListener;

@SuppressWarnings("serial")
public class Figure extends JButton{
	
/*****Attribute*****/
	private int position;
	private Color color;
	private Area area;
	private int moveScore;
	
/*****Constructor*****/
	public Figure(Color color, int position) {
		super();
		this.color = color;
		this.position = position;
		this.area = Area.Base;
		
		this.setPreferredSize(new Dimension(25,25));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.addActionListener(new FigureListener());
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
	public int getMoveScore() {
		return moveScore;
	}
	public void addMoveScore(int moveScore) {
		this.moveScore = this.moveScore + moveScore;
	}
	public void setMoveScore(int moveScore) {
		this.moveScore = moveScore;
	}

	@Override
	public String toString() {
		return "Figure [position=" + position + ", color=" + color + ", area=" + area + ", moveScore=" + moveScore
				+ "]";
	}
	
/*****toStrong*****/
	
}
