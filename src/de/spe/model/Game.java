package de.spe.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import de.spe.control.Controller;
import de.spe.control.Observable;
import de.spe.control.Observer;

public class Game implements Observable, Observer{		Random random = new Random();

/*****Attribute*****/
	private Player yellowPlayer;
	private Player greenPlayer;
	private Player bluePlayer;
	private Player redPlayer;
	private Player currentPlayer;
	
	private Figure currentFigure;
	private Figure toKickFigure;
	
	
	private Figure[] fieldFigurePosition;
	private ArrayList<Player> players;	
	private ArrayList<Figure> blockedFigure;
	private ArrayList<Integer> dices;
	private ArrayList<Observer> observers;
	
	private int round;
	private int wuerfe;
	private int lastPosition;
	private Area lastArea;
	
	private boolean canMove;
	

/*****GetterAndSetter*****/
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public Figure getCurrentFigure() {
		return currentFigure;
	}
	public Figure getToKickFigure() {
		return toKickFigure;
	}
	public int getLastPosition() {
		return lastPosition;
	}
	public Area getLastArea() {
		return this.lastArea;
	}

/*****Constructor*****/
	public Game(String yellowPlayerName, String greenPlayerName, String redPlayerName, String bluePlayerName) {
		this(yellowPlayerName, false, greenPlayerName, false, redPlayerName, false, bluePlayerName, false);
	}
	
	public Game(String yellowPlayerName, Boolean yellowBot, String greenPlayerName, Boolean greenBot, String redPlayerName, Boolean redBot, String bluePlayerName, Boolean blueBot) {
		super();
		
		this.fieldFigurePosition = new Figure[40];
		this.players = new ArrayList<Player>();	
		this.blockedFigure = new ArrayList<Figure>();
		this.dices = new ArrayList<Integer>();
		this.observers = new ArrayList<Observer>();
		
		this.canMove = false;
		
		//Add Observer
		this.addObserver(Controller.getInstance().getFrame().getMainContent().getBoardPanel());
		DiceAL.getInsance().addObserver(this);
		
		//Create Players
		if((yellowPlayerName != null && !yellowPlayerName.isBlank()) || yellowBot == true) {
			this.yellowPlayer = new Player(yellowPlayerName, Color.yellow, new Color(0xe8e841), yellowBot);
			this.players.add(yellowPlayer);
			System.out.println(yellowPlayerName + ": spielt als Gelber Spieler mit.");
			this.createFigure(yellowPlayer);
		}
		if(greenPlayerName != null && !greenPlayerName.isBlank() || greenBot == true) {
			this.greenPlayer = new Player(greenPlayerName, Color.green, new Color(0x28b528), greenBot);
			this.players.add(greenPlayer);
			System.out.println(greenPlayerName + ": spielt als Grüner Spieler mit.");
			this.createFigure(greenPlayer);
		}
		if(redPlayerName != null && !redPlayerName.isBlank() || redBot == true) {
			this.redPlayer = new Player(redPlayerName, Color.red, new Color(0xc94040), redBot);
			this.players.add(redPlayer);
			System.out.println(redPlayerName + ": spielt als Roter Spieler mit.");
			this.createFigure(redPlayer);
		}
		if(bluePlayerName != null && !bluePlayerName.isBlank() || blueBot == true) {
			this.bluePlayer = new Player(bluePlayerName, Color.blue, new Color(0x1b1ba6), blueBot);
			this.players.add(bluePlayer);
			System.out.println(bluePlayerName + ": spielt als Blauer Spieler mit.");
			this.createFigure(bluePlayer);
		}
		
		System.out.println("Alle Spieler erstellt: " + this.players + "\n");
		round = 0;
		currentPlayer = players.get(0);
		currentPlayer.activateFigures();
	}

/*****Methoden*****/	
	
///createFigures
	private void createFigure(Player player) {
		System.out.println("Ich platziere die Figuren für " + player.getName() + ".\n");
		for(Figure figure : player.getFigures()) {
			this.currentFigure = figure;
			notifyObservers();
		}
	}
	
//order Player
	private void orderPlayers(ArrayList<Player> players, ArrayList<Integer> dices) {
		int maxDicePos = 0;
		for(int i = 0; i < dices.size()-1; i++) {
			if(dices.get(i) < dices.get(i+1)) {
				maxDicePos = i+1;
			}
		}
		this.currentPlayer = players.get(maxDicePos);
	}
	
//next Player
	private void nextPlayer() {
		this.currentPlayer.blockFigure();
		
		if(this.players.indexOf(this.currentPlayer)<this.players.size()-1) {
			this.currentPlayer=this.players.get(this.players.indexOf(this.currentPlayer)+1);
		} else {
			this.currentPlayer=this.players.get(0);
		}
		System.out.println("-------------" + currentPlayer + " ist dran.------------");
		wuerfe = 0;
	}
	
	private void againPlayer() {
		System.out.println("againPlayer");
		this.currentPlayer.blockFigure();
		
		System.out.println(currentPlayer + " ist dran.");
	}
	
///checkMove
	public void checkMove() {
		System.out.println("Ich checke jetzt was " + currentPlayer.getName() + " machen kann.");
		
	/// Alle Figur sind in der Base		
		 if(this.currentPlayer.baseSize() == 4){
			if(DiceAL.getInsance().getLastRoll() != 6) {//gewürfelte Nummer ist nicht 6
				System.out.println("Leider sind alle Figuren in der Base und du hast keine 6");
				if(wuerfe < 3) {
					System.out.println("Du darfst nochmal würfeln. " + wuerfe);
					currentFigure = null;
					this.notifyObservers();
					return;
				}else {
					this.blockedFigure.clear();
					this.nextPlayer();
					this.notifyObservers();
					return;
				}
			}
		 }
	//ckecke alle Figuren
			for(Figure figure : this.currentPlayer.getFigures()) {
				int moveTo = 0;
			//Figure befindet sich auf Spielfeld
				if(Arrays.asList(this.fieldFigurePosition).contains(figure)) {
					System.out.println("Eine Figur befindet sich auf dem Spielfeld");
				//würde ins Ziel gehen
					if(Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll() >  this.currentPlayer.getEndField() && Arrays.asList(this.fieldFigurePosition).indexOf(figure) < this.currentPlayer.getEndField()) {//cP + W > eF && cP < eF
						if(Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll() > this.currentPlayer.getEndField() + 4) this.blockedFigure.add(figure);// cant go to far into home
						
						int lastStepsOnField = this.currentPlayer.getEndField() - Arrays.asList(this.fieldFigurePosition).indexOf(figure);
						if (this.currentPlayer.getInHome()[DiceAL.getInsance().getLastRoll() - lastStepsOnField] != null) this.blockedFigure.add(figure); // cant go to own field into home
					}
					
						
					
				//würde sich auf Spielfeld bewegen
					else{
						moveTo = Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll();
						if(moveTo>39) moveTo = moveTo - 40;
						if(this.fieldFigurePosition[moveTo] != null){
							if(this.fieldFigurePosition[moveTo].getBackground() == this.currentPlayer.getColor()) this.blockedFigure.add(figure);//cant go to own field				
						}
					}
				}
			//Figure befindet sich in Home			
				else if (Arrays.asList(this.currentPlayer.getInHome()).contains(figure)) {
					System.out.println("Eine Figur befindet sich in Home");
					moveTo = Arrays.asList(this.currentPlayer.getInHome()).indexOf(figure) + DiceAL.getInsance().getLastRoll();
					if(moveTo > 3) this.blockedFigure.add(figure);//cant go to far in home
					else if(this.currentPlayer.getInHome()[moveTo] != null) this.blockedFigure.add(figure);//cant go to own field in home
				}	
			//Figure befindet sich in Base			
				else if(Arrays.asList(this.currentPlayer.getInBase()).contains(figure)) {
					if(DiceAL.getInsance().getLastRoll() != 6) this.blockedFigure.add(figure);
					else if(this.fieldFigurePosition[this.currentPlayer.getStartField()] != null 
							&& this.fieldFigurePosition[this.currentPlayer.getStartField()].getColor() == currentFigure.getColor()) blockedFigure.add(figure);
				}
			}	
			
			///nothing can move
			if(this.blockedFigure.size()==4) {
				System.out.println("Keine Figur kann laufen");
				currentFigure = null;
				
				
				this.notifyObservers();
				//NextPlayerOrAgain
				if(DiceAL.getInsance().getLastRoll() != 6) this.nextPlayer();
				this.blockedFigure.clear();
				return;
			} 
			
			System.out.println("");
			
			for(Figure figure : currentPlayer.getFigures()) {
				if(!blockedFigure.contains(figure)) {
					System.out.println("activiere");
					currentPlayer.activateFigures(figure);
				}
			}
		//if currentPlayer=bot -> botMove()
		
	}
	
//move Figure	
	public void moveFigure(Figure currentFigure) {
		if(this.canMove == false) return;
		if(this.currentPlayer.getColor() != currentFigure.getBackground()) return;
		
		this.currentFigure = currentFigure;
		this.toKickFigure = null;
		int moveTo = 0;
		
	///nothing can move
		if(this.blockedFigure.size()==4) {
			currentFigure = null;
		}
	///Figure cant move
		else if(this.blockedFigure.contains(this.currentFigure)) return;
		
		
	/// Figure steht auf dem Spielfeld		
		else if(Arrays.asList(this.fieldFigurePosition).contains(this.currentFigure)) { 
		//Figure geht ins Haus
			if(Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure) + DiceAL.getInsance().getLastRoll() >  this.currentPlayer.getEndField() && Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure) < this.currentPlayer.getEndField()) {//würde ins Ziel gehen
				
				int lastStepsOnField = this.currentPlayer.getEndField() - Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure);
				moveTo = DiceAL.getInsance().getLastRoll() - lastStepsOnField -1;
				
				//speichern das Figure gerückt ist
				this.lastPosition = Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure);
				this.lastArea = currentFigure.getArea();
				this.fieldFigurePosition[Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure)] = null;
				this.currentPlayer.getInHome()[moveTo] = this.currentFigure;
				this.currentFigure.setArea(Area.Home);
				}
			//geht nicht ins Ziel bleibt auf Feld			
			else {
				moveTo = Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure) + DiceAL.getInsance().getLastRoll();
				if(moveTo>39) moveTo = moveTo - 40;
				///Figure befindet sich auf dem zu ziehenden Feld
				if(this.fieldFigurePosition[moveTo] != null){
					this.kickFigure(moveTo); //zu kickende Figur
				}
				//speichern das Figure gerückt ist
				this.lastPosition = Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure);
				this.lastArea = currentFigure.getArea();
				this.fieldFigurePosition[Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure)] = null;
				this.fieldFigurePosition[moveTo] = this.currentFigure;
				this.currentFigure.setArea(Area.Main);
			} 
		}
	/// Figur ist in der Base
		else if(Arrays.asList(this.currentPlayer.getInBase()).contains(this.currentFigure)) {
			moveTo = this.currentPlayer.getStartField();
			
			//befindet sich da eine Figure
			if(this.fieldFigurePosition[this.currentPlayer.getStartField()] != null){
				this.kickFigure(moveTo);
			}

			//speichern das Figure gerückt ist
			this.lastPosition = Arrays.asList(this.currentPlayer.getInBase()).indexOf(this.currentFigure);
			this.lastArea = currentFigure.getArea();
			this.currentPlayer.getInBase()[Arrays.asList(this.currentPlayer.getInBase()).indexOf(this.currentFigure)] = null;
			this.fieldFigurePosition[moveTo] = this.currentFigure;
			this.currentFigure.setArea(Area.Main);
		} 	
	/// Figure steht im Haus/Ziel		
		else if (Arrays.asList(this.currentPlayer.getInHome()).contains(this.currentFigure)) { 			
			moveTo = Arrays.asList(this.currentPlayer.getInHome()).indexOf(this.currentFigure) + DiceAL.getInsance().getLastRoll();
			
			//speichern das Figure gerückt ist
			this.lastPosition = Arrays.asList(this.currentPlayer.getInHome()).indexOf(this.currentFigure);
			this.lastArea = currentFigure.getArea();
			this.currentPlayer.getInHome()[Arrays.asList(this.currentPlayer.getInHome()).indexOf(this.currentFigure)] = null;
			this.currentPlayer.getInHome()[moveTo] = this.currentFigure;
			this.currentFigure.setArea(Area.Home);
		} 
		
		else {
			System.out.println("Fehler Figur ist nirgends gespeichert");
		}
		
		//GewonnenOrNot
		if(currentPlayer.homeSize() == 4) {
			System.out.println(currentPlayer.getInHome());
			this.currentFigure.setPosition(moveTo);
			this.blockedFigure.clear();
			this.notifyObservers();
			
			System.out.println(currentPlayer.getName() + " hat gewonnen!");
		}
		else {
		
		//NextPlayerOrAgain
		if(DiceAL.getInsance().getLastRoll() != 6) {
			this.nextPlayer();
		} else {
			this.againPlayer();
		}
		System.out.println("moveFigure");
		this.currentFigure.setPosition(moveTo);
		this.blockedFigure.clear();
		this.canMove = false;System.out.println("CantMove");
		this.notifyObservers();
		}


	}
	
	private void moveBot() {
		ArrayList<Figure> rdmMoveFigure = new ArrayList<Figure>();
		
		for(Figure figure : currentPlayer.getFigures()) {
			if(!blockedFigure.contains(figure)) {
				int moveScore = 0;
				
			}
		}
	}
	
//Kick Player
	private void kickFigure(int moveTo) {
		int kickTo = 0;
		this.toKickFigure = this.fieldFigurePosition[moveTo];
		System.out.println("Ich kicke Logik");
		
		if(this.toKickFigure.getBackground() == this.yellowPlayer.getColor()) {
			this.yellowPlayer.addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.yellowPlayer.getInBase()).indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getBackground()== this.greenPlayer.getColor()) {
			this.greenPlayer.addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.greenPlayer.getInBase()).indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getBackground()== this.redPlayer.getColor()) {
			this.redPlayer.addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.redPlayer.getInBase()).indexOf(toKickFigure);
		}	
		else if(this.toKickFigure.getBackground()== this.bluePlayer.getColor()) {
			this.bluePlayer.addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.bluePlayer.getInBase()).indexOf(this.toKickFigure);
		}
			

		toKickFigure.setPosition(kickTo);
		toKickFigure.setArea(Area.Base);
	}
	
	public void removeFigure(Figure[] figureList,Figure removeFigure){
		for(int i = 0; i<figureList.length;i++) {
			if(figureList[i]==removeFigure) {
				figureList[i] = null;
			}
		}
	}
	
/*****ObserverAndObseravable*****/
	//Notify from Dice
	@Override
	public void update(Observable observable, Object object) {

		if(object instanceof Integer lastRoll) {
			System.out.println(this.currentPlayer.getName() + " hat eine " + lastRoll + " gewürfelt");
			dices.add(lastRoll);
			wuerfe = wuerfe + 1;
		}
	//Erste Runde
		if(this.round == 0) {
			currentFigure = null;
			if(this.dices.size() == this.players.size()) {
				System.out.println("Alle 4 gewürfelt");
				orderPlayers(this.players, this.dices);
				this.round = this.round + 1;
				this.againPlayer();
				this.notifyObservers(); 
				for(Player player : players) {
					player.blockFigure();
				}
			}else {
				this.nextPlayer();
				this.notifyObservers(); 
				this.currentPlayer.activateFigures();
			}
		
		
		} 
	//Andere Runden	
		else {	
		//checke, was moven kann
			this.canMove = true;System.out.println("CanMove");
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
//		System.out.println("Ich benachrichtige die Oberserver");
		for (Observer observer : observers) {
			observer.update(this, currentFigure);
		}
	}
	
}
