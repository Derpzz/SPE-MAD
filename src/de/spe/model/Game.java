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
	public Figure getToKickFigure() {
		return toKickFigure;
	}
	
/*****Constructor*****/
	public Game(String yellowPlayerName, Boolean yellowBot, String greenPlayerName, Boolean greenBot, String bluePlayerName, Boolean blueBot,String redPlayerName, Boolean redBot) {
		super();
		//Create Players
		if((yellowPlayerName != null && !yellowPlayerName.isBlank()) || yellowBot == true) {
			this.yellowPlayer = new Player(yellowPlayerName, Color.yellow, Color.gray, yellowBot);
			this.players.add(yellowPlayer);
			System.out.println("Gelber Spieler erstellt: " + yellowPlayerName);
			this.createFigure(yellowPlayer);
		}
		if(greenPlayerName != null && !greenPlayerName.isBlank() || greenBot == true) {
			this.greenPlayer = new Player(greenPlayerName, Color.green, Color.gray, greenBot);
			this.players.add(greenPlayer);
			System.out.println("Grüner Spieler erstellt: " + greenPlayerName);
			this.createFigure(greenPlayer);
		}
		if(bluePlayerName != null && !bluePlayerName.isBlank() || blueBot == true) {
			this.bluePlayer = new Player(bluePlayerName, Color.blue, Color.gray, blueBot);
			this.players.add(bluePlayer);
			System.out.println("Blauer Spieler erstellt: " + bluePlayerName);
			this.createFigure(bluePlayer);
		}
		if(redPlayerName != null && !redPlayerName.isBlank() || redBot == true) {
			this.redPlayer = new Player(redPlayerName, Color.red, Color.gray, redBot);
			this.players.add(redPlayer);
			System.out.println("Roter Spieler erstellt: " + redPlayerName);
			this.createFigure(redPlayer);
		}
		System.out.println("Alle Spieler erstellt: " + this.players + "\n");
		round = 0;
		currentPlayer = players.get(0);
		this.update(null);///Würfel simulieren
	}

/*****Methoden*****/	
	
///createFigures
	private void createFigure(Player player) {
		System.out.println("Ich erstelle die Figuren für " + player.getName() + ".\n");
		for(Figure figure : player.getFigures()) {
			this.currentFigure = figure;
			notifyObservers();
		}
	}
	
//order Player
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
		System.out.println("Habe Spieler sortiert: " + players +"\n");
		return players;
	}
//next Player
	private void nextPlayer() {
		this.currentPlayer.blockFigure();
		
		if(this.players.indexOf(this.currentPlayer)<this.players.size()-1) {
			this.currentPlayer=this.players.get(this.players.indexOf(this.currentPlayer)+1);
		} else {
			this.currentPlayer=this.players.get(0);
		}
		this.update(null); ///test
	}
	
///checkMove
	public void checkMove() { /// JButton --> Figure
		System.out.println("Ich checke jetzt was " + currentPlayer.getName() + " machen kann.");
		/// Alle Figur  sind in der Base		
		 if(this.currentPlayer.getInBase().size()==4){
			if(GUINumber.WÜRFEL.getNumber() != 6) {//gewürfelte Nummer ist nicht 6
				System.out.println("Leider sind alle Figuren in der Base und du hast keine 6");
				currentFigure = null;
				this.notifyObservers();
				return;
			}
		 }
		 
	//ckecke alle Figuren
		for(Figure figure : this.currentPlayer.getFigures()) {
			int moveTo = 0;
		//Figure befindet sich auf Spielfeld
			if(this.fieldFigurePosition.contains(figure)) {
				System.out.println("Eine Figur befindet sich auf dem Spielfeld");
			//würde ins Ziel gehen
				if(this.currentPlayer.getEndField() - GUINumber.WÜRFEL.getNumber() < this.fieldFigurePosition.indexOf(figure)) {
					if(this.fieldFigurePosition.indexOf(figure) + GUINumber.WÜRFEL.getNumber() > this.currentPlayer.getEndField() + 4) this.blockedFigure.add(figure);// cant go to far into home
					
					int lastStepsOnField = this.currentPlayer.getEndField() - this.fieldFigurePosition.indexOf(figure);
					if (this.currentPlayer.getInHome().get(GUINumber.WÜRFEL.getNumber() - lastStepsOnField) != null) this.blockedFigure.add(figure); // cant go to own field into home
				}
			//würde sich auf Spielfeld bewegen
				else{
					moveTo = this.fieldFigurePosition.indexOf(figure) + GUINumber.WÜRFEL.getNumber();
					if(this.fieldFigurePosition.get(moveTo) != null){
						if(this.fieldFigurePosition.get(moveTo).getBackground() == this.currentPlayer.getColor()) this.blockedFigure.add(figure);//cant go to own field				
					}
				}
			}
		//Figure befindet sich in Home			
			else if (this.currentPlayer.getInHome().contains(figure)) {
				System.out.println("Eine Figur befindet sich in Home");
				moveTo = this.currentPlayer.getInHome().indexOf(figure) + GUINumber.WÜRFEL.getNumber();
				if(moveTo > 3) this.blockedFigure.add(figure);//cant go to far in home
				else if(this.currentPlayer.getInHome().get(moveTo) != null) this.blockedFigure.add(figure);//cant go to own field in home
			}	
		//Figure befindet sich in Base			
			else if(this.currentPlayer.getInBase().contains(figure)) {
				System.out.println("Eine Figure befindet sich in der Base");
				if(GUINumber.WÜRFEL.getNumber() != 6) this.blockedFigure.add(figure);
//				else if(this.fieldFigurePosition.get(this.currentPlayer.getStartField()) != null 
//						&& this.fieldFigurePosition.get(this.currentPlayer.getStartField()).getColor() == currentFigure.getColor()) blockedFigure.add(figure);
			}
		}	
		
		///nothing can move
		if(this.blockedFigure.size()==4) {
			System.out.println("Keine Figur kann laufen");
			currentFigure = null;
			
			//NextPlayerOrAgain
			if(GUINumber.WÜRFEL.getNumber() != 6) this.nextPlayer();
			
			this.blockedFigure.clear();
			this.notifyObservers();
			return;
		} 
		
		System.out.println("");
		for(Figure figure : currentPlayer.getFigures()) {
			if(!blockedFigure.contains(figure)) {
				currentPlayer.activateFigures(figure);
			}
		}
		
		//if currentPlayer=bot -> botMove()
		
	}
	
//move Figure	
	public void moveFigure(Figure currentFigure) {
		if(this.round == 0) return;
		if(this.currentPlayer.getColor() != currentFigure.getBackground()) return;
		
		this.currentFigure = currentFigure;
		int moveTo = 0;
		
	///nothing can move
		if(this.blockedFigure.size()==4) {
			currentFigure = null;
		}
	///Figure cant move
		else if(this.blockedFigure.contains(this.currentFigure)) return;
		
		
	/// Figure steht auf dem Spielfeld		
		else if(this.fieldFigurePosition.contains(this.currentFigure)) { 
		//Figure geht ins Haus
			if(this.currentPlayer.getEndField() - GUINumber.WÜRFEL.getNumber() < this.fieldFigurePosition.indexOf(this.currentFigure)) {//würde ins Ziel gehen
				
				int lastStepsOnField = this.currentPlayer.getEndField() - fieldFigurePosition.indexOf(this.currentFigure);
				moveTo = GUINumber.WÜRFEL.getNumber() - lastStepsOnField -1;
				
				//speichern das Figure gerückt ist
				this.fieldFigurePosition.remove(this.currentFigure);
				this.currentPlayer.getInHome().add(moveTo, this.currentFigure);
				this.currentFigure.setArea(Area.Home);
				}
			//geht nicht ins Ziel	bleibt auf Feld			
			else {
				moveTo = this.fieldFigurePosition.indexOf(this.currentFigure) + GUINumber.WÜRFEL.getNumber();
				
				///Figure befindet sich auf dem zu ziehenden Feld
				if(this.fieldFigurePosition.get(moveTo) != null){
					this.kickFigure(moveTo); //zu kickende Figur
				}
				//speichern das Figure gerückt ist
				this.fieldFigurePosition.remove(this.currentFigure);
				this.fieldFigurePosition.add(moveTo, this.currentFigure);
				this.currentFigure.setArea(Area.Main);
			} 
		}
	/// Figur ist in der Base
		else if(this.currentPlayer.getInBase().contains(this.currentFigure)) {
			moveTo = this.currentPlayer.getStartField();
			
			//befindet sich da eine Figure
			if(this.fieldFigurePosition.get(this.currentPlayer.getStartField()) != null){
				this.kickFigure(moveTo);
			}

			//speichern das Figure gerückt ist
			this.currentPlayer.getInBase().remove(this.currentFigure);
			this.fieldFigurePosition.add(moveTo, this.currentFigure);
			this.currentFigure.setArea(Area.Home);
		} 	
	/// Figure steht im Haus/Ziel		
		else if (this.currentPlayer.getInHome().contains(this.currentFigure)) { 			
			moveTo = this.currentPlayer.getInHome().indexOf(this.currentFigure) + GUINumber.WÜRFEL.getNumber();
			
			//speichern das Figure gerückt ist
			this.currentPlayer.getInHome().remove(this.currentFigure);
			this.currentPlayer.getInHome().add(moveTo, this.currentFigure);
			this.currentFigure.setArea(Area.Home);
		} 
		
		else {
			System.out.println("Fehler Figur ist nirgends gespeichert");
		}
		
		//NextPlayerOrAgain
		if(GUINumber.WÜRFEL.getNumber() != 6) this.nextPlayer();
		
		this.currentFigure.setPosition(moveTo);
		this.blockedFigure.clear();
		this.notifyObservers();
	}
	
//Kick Player
	private void kickFigure(int moveTo) {
		int kickTo = 0;
		this.toKickFigure = this.fieldFigurePosition.get(moveTo);
		
		if(this.toKickFigure.getBackground() == this.yellowPlayer.getColor()) {
			this.yellowPlayer.addInBase(this.toKickFigure);
			kickTo = this.yellowPlayer.getInBase().indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getBackground()== this.greenPlayer.getColor()) {
			this.greenPlayer.addInBase(this.toKickFigure);
			kickTo = this.greenPlayer.getInBase().indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getBackground()== this.bluePlayer.getColor()) {
			this.bluePlayer.addInBase(this.toKickFigure);
			kickTo = this.bluePlayer.getInBase().indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getBackground()== this.redPlayer.getColor()) {
			this.redPlayer.addInBase(this.toKickFigure);
			kickTo = this.redPlayer.getInBase().indexOf(toKickFigure);
		}
		toKickFigure.setPosition(kickTo);
		toKickFigure.setArea(Area.Base);
	}
	
/*****ObserverAndObseravable*****/
	//Notify from Dice
	@Override
	public void update(Observable observable) {
//		dices.add(observable.getData.getNumber);
///////////Würfel simulieren
		int gewürfelt = random.nextInt(6)+1;
		dices.add(gewürfelt);
		System.out.println(this.currentPlayer.getName() + " hat eine " + gewürfelt + " gewürfelt");
		test++;
//////////
		//Erste Runde
		if(this.round == 0) {
			currentFigure = null;
			if(this.dices.size() == this.players.size()) {
				System.out.println("\nIch sortiere Spieler:");
				orderPlayers(this.players, this.dices);
				this.round =+ 1;
				this.currentPlayer = this.players.get(players.size()-1);
			}

		this.nextPlayer();
		this.notifyObservers();
		} 
	//Andere Runden	
		else {		
		//checke, was moven kann
			this.checkMove();	
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
	public Figure getData() {
		return currentFigure;
	}
	
}
