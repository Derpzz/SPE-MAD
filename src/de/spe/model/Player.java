package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
/*****Attribute*****/
	private String name;
	private Color color;
	private Color blockedColor;
	private boolean bot;
	
	private ArrayList<Figure> figures;
	private Figure[] inBase;
	private Figure[] inHome;
	
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
	public Figure[] getInBase() {
		return inBase;
	}
	public int addInBase(Figure homeFigure) {
		for(int i = 0; i < 4; i++) {
			if (this.inBase[i]==null){
				this.inBase[i] = homeFigure;
				return i;
			}
		}
		return 3;
	}
	public Figure[] getInHome() {
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
		this.inBase = new Figure[4];
		this.inHome = new Figure[4];
		
		//Für Farbe richtige Nummern
		if(color==Color.yellow) {
			startField = GUINumber.yellowStartPoint.getNumber();
			endField = GUINumber.yellowEndPoint.getNumber();
		} 
		else if(color==Color.green) {
			startField = GUINumber.greenStartPoint.getNumber();
			endField = GUINumber.greenEndPoint.getNumber();
		} 
		else if(color==Color.red) {
			startField = GUINumber.redStartPoint.getNumber();
			endField = GUINumber.redEndPoint.getNumber();
		} 
		else if(color==Color.blue) {
			startField = GUINumber.blueStartPoint.getNumber();
			endField = GUINumber.blueEndPoint.getNumber();
		}
		
		//create Figure	
		this.firstFigure = new Figure(color, 0);
		this.figures.add(firstFigure);
		this.addInBase(firstFigure);		
		this.secondFigure = new Figure(color, 1);
		this.figures.add(secondFigure);
		this.addInBase(secondFigure);	
		this.thirdFigure = new Figure(color, 2);
		this.figures.add(thirdFigure);
		this.addInBase(thirdFigure);
		this.fourthFigure = new Figure(color, 3);
		this.figures.add(fourthFigure);
		this.addInBase(fourthFigure);	
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

