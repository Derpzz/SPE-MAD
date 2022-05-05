package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;

import de.spe.control.GUINumber;

public class Player {
/*****Attribute*****/
	private String name;
	private Color color;
	private Color blockedColor;
	private boolean bot;
	
	private ArrayList<Figure> figures;
	private ArrayList<Figure> inBase;
	private ArrayList<Figure> inHome;
	
	private int startField;
	private int endField;
	
	private Figure firstFigure;
	private Figure secondFigure;
	private Figure thirdFigure;
	private Figure fourthFigure;
	
	
/*****GetterAndSetter*****/
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	public boolean getBot() {
		return this.bot;
	}
	public ArrayList<Figure> getFigures(){ 
		return figures;
	}
	public ArrayList<Figure> getInBase() {
		return inBase;
	}
	public void addInBase(Figure homeFigure) {
		this.inBase.add(homeFigure);
	}
	public ArrayList<Figure> getInHome() {
		return inHome;
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
	public Player(String name, Color color, Color blockedColor, Boolean bot) {
		super();
		this.name = name;
		this.color = color;
		this.blockedColor = blockedColor;
		this.bot = bot;
		this.figures = new ArrayList<Figure>();
		this.inHome = new ArrayList<Figure>();
		
		//Für Farbe richtige Nummern
		if(color==Color.yellow) {
			startField = GUINumber.yellowStartPoint.getNumber();
			endField = GUINumber.yellowEndPoint.getNumber();
		} else if(color==Color.green) {
			startField = GUINumber.greenStartPoint.getNumber();
			endField = GUINumber.greenEndPoint.getNumber();
		} else if(color==Color.blue) {
			startField = GUINumber.blueStartPoint.getNumber();
			endField = GUINumber.blueEndPoint.getNumber();
		} else if(color==Color.red) {
			startField = GUINumber.redStartPoint.getNumber();
			endField = GUINumber.redEndPoint.getNumber();
		}
		
		//create Figure
		this.inBase = new ArrayList<Figure>();		
		this.firstFigure = new Figure(color, 0);
		this.figures.add(firstFigure);
		this.inBase.add(firstFigure);		
		this.secondFigure = new Figure(color, 1);
		this.figures.add(secondFigure);
		this.inBase.add(secondFigure);	
		this.thirdFigure = new Figure(color, 2);
		this.figures.add(thirdFigure);
		this.inBase.add(thirdFigure);
		this.fourthFigure = new Figure(color, 3);
		this.figures.add(fourthFigure);
		this.inBase.add(fourthFigure);	
		
		this.blockFigure();
	}
	
/*****Methoden*****/	
	
	public void activateFigures() {
		System.out.println("Alle Figuren von " + this.name + " wurden aktiviert und können laufen.");
		for(Figure figure : this.figures) {
			figure.setBackground(this.color);
		}
	}
	public void activateFigures(Figure figure) {
		System.out.println("Figure auf: " + figure.getArea() + ": " + figure.getPosition() + " wurde aktiviert und kann laufen.");
		figure.setBackground(this.color);
	}
	
	public void blockFigure() {
		System.out.println("Alle Figuren von " + this.name + " wurden deaktiviert.");
		for(Figure figure : this.figures) {
			figure.setBackground(this.blockedColor);
		}
	}
	public void blockFigure(Figure figure) {
		System.out.println("Figure auf: " + figure.getArea() + ": " + figure.getPosition() + " wurde deaktiviert.");
		figure.setBackground(this.blockedColor);
	}
	
/*****Override*****/	
	@Override
	public String toString() {
		return "Player:" + this.name + ", isBot: " + this.bot +"|";
	}
	

	
}

