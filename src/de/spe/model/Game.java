package de.spe.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import de.spe.control.Controller;
import de.spe.control.DiceAL;
import de.spe.control.Observable;
import de.spe.control.Observer;
import de.spe.view.BoardPanel;

public class Game implements Observable, Observer{

/*****Attribute*****/
	private Player[] players;	
	private Player currentPlayer;
	
	private Figure currentFigure;
	private Figure toKickFigure;
	
	
	private Figure[] fieldFigurePosition;
	private ArrayList<Figure> blockedFigure;
	private ArrayList<Integer> dices;
	private ArrayList<Observer> observers;
	
	private int round;
	private int wuerfe;
	private int lastPosition;
	private Area lastArea;
	
	private boolean canMove;
	Random random = new Random();
	

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
		this.players = new Player[4];	
		this.blockedFigure = new ArrayList<Figure>();
		this.dices = new ArrayList<Integer>();
		this.observers = new ArrayList<Observer>();
		
		this.canMove = false;
		
		//Add Observer
		BoardPanel boardPanel = Controller.getInstance().getFrame().getMainContent().getBoardPanel();
		this.addObserver(boardPanel);
		DiceAL.getInsance().addObserver(this);
		boardPanel.resetBoard();
		
		
		//Create Players
		if((yellowPlayerName != null && !yellowPlayerName.isBlank()) || yellowBot == true) {
			this.players[0] = new Player(yellowPlayerName, Colors.Yellow, Colors.YellowBlock, yellowBot);
			System.out.println(yellowPlayerName + ": spielt als Gelber Spieler mit.");
			this.createFigure(players[0]);
		}
		if(greenPlayerName != null && !greenPlayerName.isBlank() || greenBot == true) {
			this.players[1] = new Player(greenPlayerName, Colors.Green, Colors.GreenBlock, greenBot);;
			System.out.println(greenPlayerName + ": spielt als Grüner Spieler mit.");
			this.createFigure(players[1]);
		}
		if(redPlayerName != null && !redPlayerName.isBlank() || redBot == true) {
			this.players[2] = new Player(redPlayerName, Colors.Red, Colors.RedBlock, redBot);
			System.out.println(redPlayerName + ": spielt als Roter Spieler mit.");
			this.createFigure(players[2]);
		}
		if(bluePlayerName != null && !bluePlayerName.isBlank() || blueBot == true) {
			this.players[3] = new Player(bluePlayerName, Colors.Blue, Colors.BlueBlock, blueBot);
			System.out.println(bluePlayerName + ": spielt als Blauer Spieler mit.");
			this.createFigure(players[3]);
		}
		
		System.out.println("Alle Spieler erstellt: " + this.players + "\n");
		round = 0;
		currentPlayer = players[0];
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
	private void orderPlayers(Player[] players, ArrayList<Integer> dices) {
		int maxDicePos = 0;
		for(int i = 0; i < dices.size()-1; i++) {
			if(dices.get(i) < dices.get(i+1)) {
				maxDicePos = i+1;
			}
		}
		this.currentPlayer = players[maxDicePos];
		wuerfe = 0;
	}
	
//next Player
	private void nextPlayer() {
		this.currentPlayer.blockFigure();
		
		if(Arrays.asList(this.players).indexOf(this.currentPlayer)<this.players.length -1) {
			this.currentPlayer=this.players[Arrays.asList(this.players).indexOf(this.currentPlayer) +1];
		} else {
			System.out.println(players);
			this.currentPlayer=this.players[0];
		}
		System.out.println("-------------" + currentPlayer.getName() + " ist dran.------------");
		wuerfe = 0;
	}
	
	private void againPlayer() {
		this.currentPlayer.blockFigure();
		
		System.out.println(currentPlayer.getName() + " ist dran.");
	}
	
///checkMove
	public void checkMove() {
		System.out.println("Ich checke jetzt was " + currentPlayer.getName() + " machen kann.");
		System.out.println("Geblockte Figuren vor Schleife: " + this.blockedFigure);
		System.out.println("Figuren in Base: " + this.currentPlayer.getInBase());
	/// Alle Figur sind in der Base		
		 if(this.currentPlayer.baseSize() == 4){
			if(DiceAL.getInsance().getLastRoll() != 6) {//gewürfelte Nummer ist nicht 6
				System.out.println("Leider sind alle Figuren in der Base und du hast keine 6");
				if(wuerfe < 3) {
					System.out.println("Du darfst nochmal würfeln. " + wuerfe);
					currentFigure = null;
					this.notifyObservers();
					
					if(this.currentPlayer.isBot()) {// Bot würfelt selber
						this.botDice();
					}
				}else {					
					this.blockedFigure.clear();
					this.nextPlayer();
					this.notifyObservers();
					
					if(this.currentPlayer.isBot()) {// Bot würfelt selber
						this.botDice();
					}
				}
			}
			else {
				for(Figure figure : this.currentPlayer.getFigures()) {
					figure.setMoveScore(1);
				}
				this.againPlayer();
				for(Figure figure : currentPlayer.getFigures()) {
					if(!blockedFigure.contains(figure)) {
						currentPlayer.activateFigures(figure);
					}
				}
				if(currentPlayer.isBot() == true) {
					this.botMove();
				}
			} 
		 } 
		 else {
			 System.out.println("Test");
	//ckecke alle Figuren
			for(Figure figure : this.currentPlayer.getFigures()) {
				int moveTo = 0;
				figure.setMoveScore(1);
			//Figure befindet sich auf Spielfeld
				if(Arrays.asList(this.fieldFigurePosition).contains(figure)) {
					System.out.println("Eine Figur befindet sich auf dem Spielfeld");
				//würde ins Ziel gehen
					if(Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll() >  this.currentPlayer.getEndField() && Arrays.asList(this.fieldFigurePosition).indexOf(figure) < this.currentPlayer.getEndField()) {//cP + W > eF && cP < eF
						int lastStepsOnField = this.currentPlayer.getEndField() - Arrays.asList(this.fieldFigurePosition).indexOf(figure);
						
						if(Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll() > this.currentPlayer.getEndField() + 4) {
							this.blockedFigure.add(figure);// cant go to far into home
							continue;
						}
						else if (this.currentPlayer.getInHome()[DiceAL.getInsance().getLastRoll() - lastStepsOnField -1] != null) {
							this.blockedFigure.add(figure);// cant go to own field into home
							continue;
						}
						
						figure.addMoveScore(3);//into home
						
						switch(DiceAL.getInsance().getLastRoll() - lastStepsOnField) {
							case 1:
								figure.addMoveScore(2);
							break;
							case 2:
								figure.addMoveScore(3);
							break;
							case 3:
								figure.addMoveScore(4);
							break;
							case 4:
								figure.addMoveScore(5);
							break;
						}
					}
					
						
					
				//würde sich auf Spielfeld bewegen
					else{
						moveTo = Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll();
						if(moveTo>39) moveTo = moveTo - 40;
						if(this.fieldFigurePosition[moveTo] != null){//Figure on the Field
							System.out.println("+++++++++++++DA IST EINE FIGURE++++++++++++++++++++");
							System.out.println(currentPlayer.getColor() + " kickt " + this.fieldFigurePosition[moveTo].getColor());
							if(this.fieldFigurePosition[moveTo].getColor() == this.currentPlayer.getColor()) {
								System.out.println("IST SOGAR VON DIR");
								this.blockedFigure.add(figure);//cant go to own field	
								continue;
							}
							else {
								figure.addMoveScore(4);//kick Figure
							}
						}
						
						if(figure.getPosition() == GUINumber.yellowStartPoint.getNumber() || figure.getPosition() == GUINumber.greenStartPoint.getNumber() || figure.getPosition() == GUINumber.redStartPoint.getNumber() || figure.getPosition() == GUINumber.blueStartPoint.getNumber()) {
							figure.addMoveScore(2);//out of StartField
						}
						if(moveTo + 10 > 39) {
							if(moveTo + 10 - 40 > currentPlayer.getEndField()) {
								
							}
							figure.addMoveScore(4);//eF - 10
						}
						
						
					}
				}
			//Figure befindet sich in Home			
				else if (Arrays.asList(this.currentPlayer.getInHome()).contains(figure)) {
					System.out.println("Eine Figur befindet sich in Home");
					moveTo = Arrays.asList(this.currentPlayer.getInHome()).indexOf(figure) + DiceAL.getInsance().getLastRoll();
					
					System.out.println("Current Position: "+ Arrays.asList(this.currentPlayer.getInHome()).indexOf(figure));
					System.out.println("Würfel " + DiceAL.getInsance().getLastRoll());
					System.out.println("MoveTo in Check:" + moveTo);
					
					if(moveTo > 3) {
						this.blockedFigure.add(figure);//cant go to far in home
						continue;
					}
					else if(this.currentPlayer.getInHome()[moveTo] != null) {
						this.blockedFigure.add(figure);//cant go to own field in home
						continue;
					}
					else {
						switch(moveTo) {
						case 1:
							figure.addMoveScore(3);
						break;
						case 2:
							figure.addMoveScore(4);
						break;
						case 3:
							figure.addMoveScore(5);
						break;
					}
					}
					
				}	
			//Figure befindet sich in Base			
				else if(Arrays.asList(this.currentPlayer.getInBase()).contains(figure)) {
					System.out.println("Eine Figure befindet sich in der Base");
					if(DiceAL.getInsance().getLastRoll() != 6) {
						this.blockedFigure.add(figure);
						continue;
					}
					if(this.fieldFigurePosition[this.currentPlayer.getStartField()] != null) {
						if(this.fieldFigurePosition[this.currentPlayer.getStartField()].getColor() == currentPlayer.getColor()) {
							this.blockedFigure.add(figure);
							continue;
						}
						else {
							figure.addMoveScore(4);//kick Figure
						}
					} 
					figure.addMoveScore(3);//out of Base
				}
			}	
			
			///nothing can move
			if(this.blockedFigure.size()==4) {
				System.out.println("Keine Figur kann laufen");
				currentFigure = null;
				
				//NextPlayerOrAgain
				if(DiceAL.getInsance().getLastRoll() == 6){
					this.againPlayer();
				}
				else if(this.currentPlayer.getInHome().length > 0 && this.wuerfe < 3) {
					int finishedFigures = 0;
					for(int i = 3; i==0 ; i--) {
						if(currentPlayer.getInHome()[i] != null) {
							finishedFigures = finishedFigures + 1;
							break;
						}
					}
					if(finishedFigures + currentPlayer.getInBase().length == 4) {
						this.againPlayer();
					}
				}
				else {
					this.nextPlayer();
				}
				this.blockedFigure.clear();
				this.canMove = false;
				this.notifyObservers();
				
				if(this.currentPlayer.isBot()) {// Bot würfelt selber
					this.botDice();
				}
			} else {
			
				System.out.println("");
				System.out.println("Geblockte Figuren nach Schleife: " + this.blockedFigure);
				
				for(Figure figure : currentPlayer.getFigures()) {
					if(!blockedFigure.contains(figure)) {
						currentPlayer.activateFigures(figure);
					}
				}
				
				if(currentPlayer.isBot() == true) {
					this.botMove();
				}
			}
		}
	}
//move Figure	
	public void moveFigure(Figure currentFigure) {
		if(this.canMove == false) return;
		if(this.currentPlayer.getColor() != currentFigure.getColor()) return;
		
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
		else if(Arrays.asList(this.fieldFigurePosition).contains(this.currentFigure)) { System.out.println("Figure ist auf dem Feld");
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
		else if(Arrays.asList(this.currentPlayer.getInBase()).contains(this.currentFigure)) {System.out.println("Figure ist aquf dem Base");
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
			
			System.out.println(Arrays.asList(this.currentPlayer.getInHome()).indexOf(this.currentFigure));
			System.out.println(DiceAL.getInsance().getLastRoll());
			System.out.println(moveTo);
			
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
			this.currentFigure.setPosition(moveTo);
			this.blockedFigure.clear();
			this.currentPlayer.blockFigure();
			this.notifyObservers();
			DiceAL.getInsance().deactivateDice(Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice());
			
			System.out.println(currentPlayer.getName() + " hat gewonnen!");
			return;
		}
		else {
		
		//NextPlayerOrAgain
		if(DiceAL.getInsance().getLastRoll() != 6) {
			this.nextPlayer();
		} else {
			this.againPlayer();
		}
		
		this.currentFigure.setPosition(moveTo);
		this.blockedFigure.clear();
		this.canMove = false;
		this.notifyObservers();
		
		if(this.currentPlayer.isBot()) {// Bot würfelt selber
			this.botDice();
		}
		}


	}
	
	private void botMove() {
		ArrayList<Figure> rdmMoveFigure = new ArrayList<Figure>();
		
		for(Figure figure : currentPlayer.getFigures()) {
			if(!blockedFigure.contains(figure)) {
				for(int i = 1; i <= figure.getMoveScore(); i++) {
					rdmMoveFigure.add(figure);
				}
				
			}
		}
		System.out.println("rdmMoveFigure Größe: "+ rdmMoveFigure.size());
		this.moveFigure(rdmMoveFigure.get(random.nextInt(rdmMoveFigure.size())));
		
	}
	
	private void botDice() {
		DiceAL.getInsance().rollTheDice();
		Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice().setText(Integer.toString(DiceAL.getInsance().getLastRoll()));
		DiceAL.getInsance().deactivateDice(Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice());
		DiceAL.getInsance().notifyObservers();	
	}
	
//Kick Player
	private void kickFigure(int moveTo) {
		System.out.println("Kicke Figure");
		int kickTo = 0;
		this.toKickFigure = this.fieldFigurePosition[moveTo];
		
		if(this.toKickFigure.getColor() == this.players[0].getColor()) { //Yellow
			this.players[0].addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.players[0].getInBase()).indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getColor() == this.players[1].getColor()) {//Green
			this.players[1].addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.players[1].getInBase()).indexOf(this.toKickFigure);
		}
		else if(this.toKickFigure.getColor() == this.players[2].getColor()) {//Red
			this.players[2].addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.players[2].getInBase()).indexOf(toKickFigure);
		}	
		else if(this.toKickFigure.getColor() == this.players[3].getColor()) {//Blue
			this.players[3].addInBase(this.toKickFigure);
			kickTo = Arrays.asList(this.players[3].getInBase()).indexOf(this.toKickFigure);
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
			if(this.dices.size() == this.players.length) {
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
				
				if(this.currentPlayer.isBot()) {// Bot würfelt selber
					this.botDice();
				}
			}
		
		
		} 
	//Andere Runden	
		else {	
		//checke, was moven kann
			this.canMove = true;
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
			observer.update(this, currentFigure);
		}
	}
	
}
