package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;

import de.spe.control.GUINumber;

public class Player {
/*****Attribute*****/
	private String name;
	private Color color;
	
	private ArrayList<Figure> inBase;
	private ArrayList<Figure> inHome;
	
	private int colorNumber;
	private int startField;
	private int endField;
	
	private Figure firstFigure;
	private Figure secondFigure;
	private Figure thirdFigure;
	private Figure fourthFigure;
	
	
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
	public ArrayList<Figure> getInHome() {
		return inHome;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}	
	public int getColorNumber() {
		return colorNumber;
	}
	
	public int getStartField() {
		return startField;
	}
	public int getEndField() {
		return endField;
	}
	
	public Figure getFirstFigure() {
		return firstFigure;
	}
	public Figure getSecondFigure() {
		return secondFigure;
	}
	public Figure getThirdFigure() {
		return thirdFigure;
	}
	public Figure getFourthFigure() {
		return fourthFigure;
	}
/*****Constructor*****/	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
		
		//Für Farbe richtige Nummern
		if(color==Color.yellow) {
			colorNumber = GUINumber.yellowNumber.getNumber();
			startField = GUINumber.yellowStartPoint.getNumber();
			endField = GUINumber.yellowEndPoint.getNumber();
		} else if(color==Color.green) {
			colorNumber = GUINumber.greenNumber.getNumber();
			startField = GUINumber.greenStartPoint.getNumber();
			endField = GUINumber.greenEndPoint.getNumber();
		} else if(color==Color.blue) {
			colorNumber = GUINumber.blueNumber.getNumber();
			startField = GUINumber.blueStartPoint.getNumber();
			endField = GUINumber.blueEndPoint.getNumber();
		} else if(color==Color.red) {
			colorNumber = GUINumber.redNumber.getNumber();
			startField = GUINumber.redStartPoint.getNumber();
			endField = GUINumber.redEndPoint.getNumber();
		}
		
		//create Figure
		this.inBase = new ArrayList<Figure>();		
		this.firstFigure = new Figure(color, 201 + this.colorNumber);
		this.inBase.add(firstFigure);		
		this.secondFigure = new Figure(color, 201 + this.colorNumber);
		this.inBase.add(secondFigure);	
		this.thirdFigure = new Figure(color, 201 + this.colorNumber);
		this.inBase.add(thirdFigure);
		this.fourthFigure = new Figure(color, 201 + this.colorNumber);
		this.inBase.add(fourthFigure);
		
		
		
		
	}
@Override
public String toString() {
	return "Player [name=" + name + ", color=" + color + "]";
}
	

	
}

