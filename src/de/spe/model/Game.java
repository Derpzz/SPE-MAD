package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import de.spe.control.Observable;
import de.spe.control.Observer;
import de.spe.control.GUINumber;

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
	private ArrayList<Figure> blockedFigure = new ArrayList<Figure>();
	
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
		//Figuren platzieren
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
	
///checkMove
	public void checkMove(Figure currentFigure) { /// JButton --> Figure
		if(round == 0) return;
		if(currentPlayer.getColor() != currentFigure.getBackground()) return;
		moveTo=0;
		
	///nothing can move
		if(blockedFigure.size()==4) {
			moveTo = GUINumber.RESET.getNumber();
		}
	///Figure cant move
		else if(blockedFigure.contains(currentFigure)) return;
		
		
	/// Figure steht auf dem Spielfeld		
		else if(fieldFigurePosition.contains(currentFigure)) { 
		//Figure geht ins Haus
			if(currentPlayer.getColor()==Color.yellow) {
				if(currentPlayer.getEndField() - GUINumber.WÜRFEL.getNumber() < fieldFigurePosition.indexOf(currentFigure)) {//würde ins Ziel gehen
					if(fieldFigurePosition.indexOf(currentFigure) + GUINumber.WÜRFEL.getNumber() > currentPlayer.getEndField() + 4) {blockedFigure.add(currentFigure); return;}// cant go to far
					
					int lastStepsOnField = currentPlayer.getEndField() - fieldFigurePosition.indexOf(currentFigure);
					if (currentPlayer.getInHome().get(GUINumber.WÜRFEL.getNumber() - lastStepsOnField) != null) {blockedFigure.add(currentFigure); return;}; // cant go to own field
					
					moveTo = GUINumber.WÜRFEL.getNumber() - lastStepsOnField -1;
					
					//speichern das Figure gerückt ist
					fieldFigurePosition.remove(currentFigure);
					currentPlayer.getInHome().add(moveTo, currentFigure);
					
					//Befehl für GUI
					moveTo =+ currentPlayer.getColorNumber();
					moveTo =+ GUINumber.homeNumber.getNumber();
				}
			}
			
			
			
			moveTo = fieldFigurePosition.indexOf(currentFigure) + GUINumber.WÜRFEL.getNumber(); //gewürfelte Nummer
			
			///Figure befindet sich auf dem zu ziehenden Feld
			if(fieldFigurePosition.get(moveTo) != null){
				if(fieldFigurePosition.get(moveTo).getBackground()==currentPlayer.getColor()) {blockedFigure.add(currentFigure); return;};//cant go to own field
				kickFigure(moveTo); //zu kickende Figur
			}
			//speichern das Figure gerückt ist
			fieldFigurePosition.remove(currentFigure);
			fieldFigurePosition.add(moveTo, currentFigure);
			
			//Befehl für GUI
			moveTo =+ GUINumber.fieldNumber.getNumber();
			
			this.currentFigure=currentFigure;
		} 
		
	/// Figure steht im Haus/Ziel		
		else if (currentPlayer.getInHome().contains(currentFigure)) { 
			if(currentPlayer.getInHome().indexOf(currentFigure) + GUINumber.WÜRFEL.getNumber() > 3) {blockedFigure.add(currentFigure); return;};//cant go to far
			
			moveTo = currentPlayer.getInHome().indexOf(currentFigure) + GUINumber.WÜRFEL.getNumber();
			if(currentPlayer.getInHome().get(moveTo) != null) {blockedFigure.add(currentFigure); return;};//cant go to own field
			
			//speichern das Figure gerückt ist
			currentPlayer.getInHome().remove(currentFigure);
			currentPlayer.getInHome().add(moveTo, currentFigure);
			
			//Befehl für GUI
			moveTo =+ currentPlayer.getColorNumber();
			moveTo =+ GUINumber.homeNumber.getNumber();
			
			this.currentFigure=currentFigure;
		} 
		
	/// Alle Figur  sind in der Base				
		else if(currentPlayer.getInBase().size()==4){
			if(GUINumber.WÜRFEL.getNumber() != 6) {//gewürfelte Nummer ist nicht 6
				moveTo = GUINumber.RESET.getNumber();
				notifyObservers();
				return;
			}
/*************VVVVV gucken ob da keiner STEHT VVVVVV*******/	
			moveTo=currentPlayer.getStartField();
			
			//speichern das Figure gerückt ist
			currentPlayer.getInBase().remove(currentFigure);
			fieldFigurePosition.add(moveTo, currentFigure);
			
			//Befehl für GUI
			moveTo =+ GUINumber.fieldNumber.getNumber();
		}
		
	/// Figur ist in der Base
		else if(currentPlayer.getInBase().contains(currentFigure)) {
			if(GUINumber.WÜRFEL.getNumber() != 6) {
				blockedFigure.add(currentFigure);
				return;
			}
			moveTo=currentPlayer.getStartField();
			
			//speichern das Figure gerückt ist
			currentPlayer.getInBase().remove(currentFigure);
			fieldFigurePosition.add(moveTo, currentFigure);
			
			//Befehl für GUI
			moveTo =+ GUINumber.fieldNumber.getNumber();
		}
		
		else {
			if(GUINumber.WÜRFEL.getNumber() != 6) {//Würfel ist nicht 6
				blockedFigure.add(currentFigure);
				return;
			}
			moveTo=currentPlayer.getStartField();
			
			//speichern das Figure gerückt ist
			fieldFigurePosition.add(moveTo, currentFigure);
			
			//Befehl für GUI
			moveTo =+  GUINumber.fieldNumber.getNumber();
		}
		
		//NextPlayerOrAgain
		if(GUINumber.WÜRFEL.getNumber() != 6) nextPlayer();
		
		blockedFigure.clear();
		notifyObservers();
	}
	
	private void kickFigure(int moveTo) {
		kickTo = 0;
		toKickFigure = fieldFigurePosition.get(moveTo);
		if(toKickFigure.getBackground()==yellowPlayer.getColor()) {
			yellowPlayer.addInBase(toKickFigure);
			kickTo = yellowPlayer.getInBase().indexOf(toKickFigure);
			kickTo =+ yellowPlayer.getColorNumber();
		}
		else if(toKickFigure.getBackground()== greenPlayer.getColor()) {
			greenPlayer.addInBase(toKickFigure);
			kickTo = greenPlayer.getInBase().indexOf(toKickFigure);
			kickTo =+ greenPlayer.getColorNumber();
		}
		else if(toKickFigure.getBackground()== bluePlayer.getColor()) {
			bluePlayer.addInBase(toKickFigure);
			kickTo = bluePlayer.getInBase().indexOf(toKickFigure);
			kickTo =+ bluePlayer.getColorNumber();
		}
		else if(toKickFigure.getBackground()== redPlayer.getColor()) {
			redPlayer.addInBase(toKickFigure);
			kickTo = redPlayer.getInBase().indexOf(toKickFigure);
			kickTo =+ redPlayer.getColorNumber();
		}
		kickTo =+ GUINumber.baseNumber.getNumber();
		
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
		//Erste Runde
		if(round == 0) {
			moveTo = GUINumber.RESET.getNumber();
			if(dices.size() == players.size()) {
				System.out.println(players);
				System.out.println("Spieler sortieren");
				orderPlayers(players, dices);
				round =+ 1;
				currentPlayer=players.get(players.size()-1);
			}
		notifyObservers();
		nextPlayer();
		} 
		//Andere Runden
		else {
			//checken, was moven kann
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
