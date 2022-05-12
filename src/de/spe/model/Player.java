package de.spe.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import de.spe.control.Controller;
import de.spe.control.DatabaseConnector;

public class Player implements Saveable {
/*****Attribute*****/
	private String name;
	private Colors color;
	private Colors blockedColor;
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

	int lastID;
	
	
/*****GetterAndSetter*****/
	public String getName() {
		return name;
	}
	public Colors getColor() {
		return color;
	}
	public boolean isBot() {
		return this.bot;
	}
	public ArrayList<Figure> getFigures(){ 
		return figures;
	}
	public Figure[] getInBase() {
		return inBase;
	}
	public int addInBase(Figure homeFigure) {
		int i;
		for(i = 0; i < 4; i++) {
			if (this.inBase[i] == null){
				this.inBase[i] = homeFigure;
				break;
			}
		}
		return i;
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
	public int getLastID()
	{
		return lastID;
	}
	
/*****Constructor*****/	
	public Player(String name, Colors color, Colors blockedColor, Boolean bot) {
		super();
		this.name = name;
		this.color = color;
		this.blockedColor = blockedColor;
		this.bot = bot;
		this.figures = new ArrayList<Figure>();
		this.inBase = new Figure[4];
		this.inHome = new Figure[4];
		
		//F�r Farbe richtige Nummern
		if(color==Colors.Yellow) {
			startField = GUINumber.yellowStartPoint.getNumber();
			endField = GUINumber.yellowEndPoint.getNumber();
		} 
		else if(color==Colors.Green) {
			startField = GUINumber.greenStartPoint.getNumber();
			endField = GUINumber.greenEndPoint.getNumber();
		} 
		else if(color==Colors.Red) {
			startField = GUINumber.redStartPoint.getNumber();
			endField = GUINumber.redEndPoint.getNumber();
		} 
		else if(color==Colors.Blue) {
			startField = GUINumber.blueStartPoint.getNumber();
			endField = GUINumber.blueEndPoint.getNumber();
		}
		
		//create Figure	
		this.firstFigure = new Figure(color, 0, this);
		this.figures.add(firstFigure);
		this.addInBase(firstFigure);		
		this.secondFigure = new Figure(color, 1, this);
		this.figures.add(secondFigure);
		this.addInBase(secondFigure);	
		this.thirdFigure = new Figure(color, 2, this);
		this.figures.add(thirdFigure);
		this.addInBase(thirdFigure);
		this.fourthFigure = new Figure(color, 3, this);
		this.figures.add(fourthFigure);
		this.addInBase(fourthFigure);	
		
		this.blockFigure();

		this.lastID = -1;
	}
	
/*****Methoden*****/	
	
	//aktivieren deaktivieren Farbe
	public void activateFigures() {
		for(Figure figure : this.figures) {
			figure.setBorder(BorderFactory.createRaisedBevelBorder());
			figure.setBackground(this.color.getColor());
		}
	}
	public void activateFigures(Figure figure) {
		figure.setBorder(BorderFactory.createRaisedBevelBorder());
		figure.setBackground(this.color.getColor());
	}
	
	public void blockFigure() {
		for(Figure figure : this.figures) {
			figure.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			figure.setBackground(this.blockedColor.getColor());
		}
	}
	public void blockFigure(Figure figure) {
		figure.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		figure.setBackground(this.blockedColor.getColor());
	}
	
	//Gr��e der Arrays
	public int baseSize() {
		int count = 0;
		for(Figure figure : this.inBase) {
			if(figure != null) {
				count = count +1;
			}
		}
		return count;
	}
	public int homeSize() {
		int count = 0;
		for(Figure figure : this.inHome) {
			if(figure != null) {
				count = count +1;
			}
		}
		return count;
	}
	
/*****Override*****/	
	@Override
	public String toString() {
		return "Player:" + this.name + ", isBot: " + this.bot +"|";
	}
	
	@Override
public void save() throws SQLException{
		Game currentGame = Controller.getInstance().getCurrentGame();
		int lastID = -1;
		//currentGame.save();
		if((lastID = currentGame.getLastID()) == -1)
			throw new SQLException("Failed to retrieve GameID");

		if(DatabaseConnector.executeIsQueryEmpty("SELECT * FROM t_person WHERE name ='" + name + "';"))
			DatabaseConnector.execute("INSERT INTO t_person VALUES('" + name +"')");

		
		this.lastID = DatabaseConnector.executeGetID("INSERT INTO t_player (name, color, game_id) VALUES(" + "'" + name + "', " + "'" + color.name().toLowerCase() + "', " + lastID + ")");
	}

	
}

