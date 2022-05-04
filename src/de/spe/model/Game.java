package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import de.spe.control.DiceAL;
import de.spe.control.Observable;
import de.spe.control.Observer;
import de.spe.view.Figure;

public class Game implements Observable, Observer{		int test =0;
														Random random = new Random();
/*****Attribute*****/
	private Player yellowPlayer;
	private Player greenPlayer;
	private Player bluePlayer;
	private Player redPlayer;
	private ArrayList<Player> players = new ArrayList<Player>();	
	private Player currentPlayer;
	
	private Figure currentFigure;
	private ArrayList<Figure> fieldFigurePosition = new ArrayList<Figure>();
	
	private int moveTo;
	private int kickTo;
	private Figure toKickFigure;
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private int round;
	private ArrayList<Integer> dices = new ArrayList<Integer>();
	
/*****GetterAndSetter*****/
//	public ArrayList<Figure> getFieldFigurePosition() {
//		return fieldFigurePosition;
//	}
//	public void setFieldFigurePosition(ArrayList<Figure> fieldFigurePosition) {
//		this.fieldFigurePosition = fieldFigurePosition;
//	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public Figure getCurrentFigure() {
		return currentFigure;
	}
	public int getMoveTo() {
		return moveTo;
	}
	public int getKickTo() {
		return kickTo;
	}
	public Figure getToKickFigure() {
		return toKickFigure;
	}
	
/*****Constructor*****/
	public Game(String yellowPlayerName, String greenPlayerName, String bluePlayerName, String redPlayerName) {
		super();
		//Create Players
		if(yellowPlayerName != null && !yellowPlayerName.isBlank()) {
			yellowPlayer = new Player(yellowPlayerName,Color.yellow);
			players.add(yellowPlayer);
			System.out.println("Gelber Spieler erstellt");
		}
		if(greenPlayerName != null && !greenPlayerName.isBlank()) {
			greenPlayer = new Player(greenPlayerName,Color.green);
			players.add(greenPlayer);
			System.out.println("Grüner Spieler erstellt");
		}
		if(bluePlayerName != null && !bluePlayerName.isBlank()) {
			bluePlayer = new Player(bluePlayerName,Color.blue);
			players.add(bluePlayer);
			System.out.println("Blauer Spieler erstellt");
		}
		if(redPlayerName != null && !redPlayerName.isBlank()) {
			redPlayer = new Player(redPlayerName,Color.red);
			players.add(redPlayer);
			System.out.println("Roter Spieler erstellt");
		}
		round = 0;
		currentPlayer = players.get(0);
		this.update(null);///Würfel simulieren
	}

/*****Methoden*****/		
	//first Player
	private ArrayList<Player> orderPlayers(ArrayList<Player> players, ArrayList<Integer> dices) {
		int tempInt;
		Player tempPlayer;
		for(int i = 0; i < players.size()-1; i++) {
			for(int j = 0; j <players.size()-1; j++) {
				if (dices.get(j) < dices.get(j+1)) {
					tempInt = dices.get(j);
					tempPlayer=players.get(j);
					dices.set(j, dices.get(j+1));
					players.set(j, players.get(j+1));
					dices.set(j+1,tempInt);
					players.set(j+1, tempPlayer);
				}
			}
		}
		System.out.println(players);
		return players;
	}
	//next Player
	private void nextPlayer() {
		if(players.indexOf(currentPlayer)<players.size()-1) {
			currentPlayer=players.get(players.indexOf(currentPlayer)+1);
		} else {
			currentPlayer=players.get(0);
		}		
		
		this.update(null); ///test
	}
	
	//checkMove
	public void checkMove(Figure currentFigure) { /// JButton --> Figure
		if(currentPlayer.getColor() != currentFigure.getBackground()) return;
		moveTo=0;
		
		if(fieldFigurePosition.contains(currentFigure)) { /// Figure steht auf dem Spielfeld
			moveTo = fieldFigurePosition.indexOf(currentFigure) + (random.nextInt(6)+1); //gewürfelte Nummer
			if(fieldFigurePosition.get(moveTo)!=null){///Figure befindet sich auf dem zu ziehenden Feld
				if(fieldFigurePosition.get(moveTo).getBackground()==currentPlayer.getColor()) return;
				else kickFigure(moveTo); //zu kickende Figur
			}
			fieldFigurePosition.remove(fieldFigurePosition.indexOf(currentFigure));
			fieldFigurePosition.add(moveTo, currentFigure);
			moveTo=+100;//speichern das Figur gerückt ist
			this.currentFigure=currentFigure;
		} 
		
		else if (currentPlayer.getInHome().contains(currentFigure)) { /// Figure steht im Haus/Ziel
			
		} 
		
		else if(currentPlayer.getInBase().size()>0){ /// Figur ist in der Base
			
		}
		
		notifyObservers();
	}
	
	private void kickFigure(int moveTo) {
		toKickFigure = fieldFigurePosition.get(moveTo);
		if(toKickFigure.getBackground()==yellowPlayer.getColor()) {
			yellowPlayer.addInBase(toKickFigure);
			kickTo = yellowPlayer.getInBase().indexOf(toKickFigure);
		}
		
	}

	
/*****ObserverAndObseravable*****/
	//Notify from Dice
	@Override
	public void update(Observable observable) {
//		dices.add(observable.getData.getNumber);
///////////Würfel simulieren
		int gewürfelt = random.nextInt(7-1);
		dices.add(gewürfelt);
		System.out.println(this.currentPlayer.getName() + " hat eine " + gewürfelt + " gewürfelt");
		test++;
//////////
		if(round == 0) {
			moveTo = 000;
			if(dices.size() == players.size()) {
				System.out.println(players);
				System.out.println("Spieler sortieren");
				orderPlayers(players, dices);
				round =+ 1;
				currentPlayer=players.get(players.size()-1);
			}
		notifyObservers();
		nextPlayer();
		} else {
			
		}
		
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}
	@Override
	public void removeObsever(Observer observer) {
		this.observers.remove(observer);
	}
	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
	@Override
	public int getData() {
		return 0;
	}
	
}
