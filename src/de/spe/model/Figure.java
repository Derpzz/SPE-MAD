package de.spe.model;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import de.spe.control.FigureAL;

@SuppressWarnings("serial")
public class Figure extends JButton{
	
/*****Attribute*****/
	private int position;
	private Colors color;
	private Area area;
	private int moveScore;
	
/*****Constructor*****/
	public Figure(Colors color, int position) {
		super();
		this.color = color;
		this.position = position;
		this.area = Area.Base;
		
		this.setPreferredSize(new Dimension(25,25));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.addActionListener(new FigureAL());
	}
	
/*****GetterAndSetter*****/
	public Colors getColor() {
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
