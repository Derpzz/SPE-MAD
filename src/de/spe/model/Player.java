package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;

import de.spe.view.Figure;

public class Player {
/*****Attribute*****/
	private String name;
	private Color color;
	
	private ArrayList<Figure> inBase;
	private ArrayList<Figure> inHome;
	
/*****GetterAndSetter*****/
	public ArrayList<Figure> getInBase() {
		return inBase;
	}
	public void setInBase(ArrayList<Figure> inBase) {
		this.inBase = inBase;
	}
	public void addInBase(Figure homeFigure) {
		this.inBase.add(homeFigure);
	}
	public ArrayList getInHome() {
		return inHome;
	}
	public void setInHome(ArrayList inHome) {
		this.inHome = inHome;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	
/*****Constructor*****/	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
		this.inBase = new ArrayList<Figure>();
	}
@Override
public String toString() {
	return "Player [name=" + name + ", color=" + color + "]";
}
	

	
}

