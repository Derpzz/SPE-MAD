package de.spe.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.spe.control.BotThread;
import de.spe.control.Controller;
import de.spe.control.DiceAL;
import de.spe.control.EmptyFL;
import de.spe.control.Observable;
import de.spe.control.Observer;
import de.spe.view.BoardPanel;

public class Game implements Observable, Observer{

/*****Attribute*****/
	private Player currentPlayer;
	private ArrayList<Player> players;	
	
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
	Random random;
	BotThread botRun;
	

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

/*****Constructor
 * @throws Exception *****/
	public Game(String yellowPlayerName, String greenPlayerName, String redPlayerName, String bluePlayerName) throws Exception {
		this(yellowPlayerName, false, greenPlayerName, false, redPlayerName, false, bluePlayerName, false);
	}
	
	public Game(String yellowPlayerName, Boolean yellowBot, String greenPlayerName, Boolean greenBot, String redPlayerName, Boolean redBot, String bluePlayerName, Boolean blueBot) throws Exception {
		super();
		
		this.fieldFigurePosition = new Figure[40];
		this.players = new ArrayList<Player>();	
		this.blockedFigure = new ArrayList<Figure>();
		this.dices = new ArrayList<Integer>();
		this.observers = new ArrayList<Observer>();
		
		this.canMove = false;
		random = new Random();
		botRun = new BotThread();
		
		
		//Add Observer
		BoardPanel boardPanel = Controller.getInstance().getFrame().getMainContent().getBoardPanel();
		this.addObserver(boardPanel);
		DiceAL.getInsance().addObserver(this);
		boardPanel.resetBoard();
		
		
		//Create Players
		if((!yellowPlayerName.isBlank()) || yellowBot == true) {
			if(yellowBot == true) {
				yellowPlayerName="yellowBot";
			}
			Player yellowPlayer = new Player(yellowPlayerName, Colors.Yellow, Colors.YellowBlock, yellowBot);
			this.players.add(yellowPlayer);
			System.out.println(yellowPlayerName + ": spielt als Gelber Spieler mit.");
			this.createFigure(players.get(players.indexOf(yellowPlayer)));
		}
		if(!greenPlayerName.isBlank() || greenBot == true) {
			if(greenBot == true) {
				greenPlayerName="greenBot";
			}
			Player greenPlayer = new Player(greenPlayerName, Colors.Green, Colors.GreenBlock, greenBot);
			this.players.add(greenPlayer);
			System.out.println(greenPlayerName + ": spielt als Grüner Spieler mit.");
			this.createFigure(players.get(players.indexOf(greenPlayer)));
		}
		if(!redPlayerName.isBlank() || redBot == true) {
			if(redBot == true) {
				redPlayerName="redBot";
			}
			Player redPlayer = new Player(redPlayerName, Colors.Red, Colors.RedBlock, redBot);
			this.players.add(redPlayer);
			System.out.println(redPlayerName + ": spielt als Roter Spieler mit.");
			this.createFigure(players.get(players.indexOf(redPlayer)));
		}
		if(!bluePlayerName.isBlank() || blueBot == true) {
			if(blueBot == true) {
				bluePlayerName="blueBot";
			}
			Player bluePlayer = new Player(bluePlayerName, Colors.Blue, Colors.BlueBlock, blueBot);
			this.players.add(bluePlayer);
			System.out.println(bluePlayerName + ": spielt als Blauer Spieler mit.");
			this.createFigure(players.get(players.indexOf(bluePlayer)));
		}
		for(int i = 0; i<players.size()-1;i++) {
			for(int j = i+1; j <players.size(); j++) {
				if(players.get(i).getName().equals(players.get(j).getName())) {
					Controller.getInstance().getFrame().getMainContent().getBoardPanel().resetBoard();
					throw new Exception("Spiel kann nicht gestartet werden. Zwei Spieler sind gleich.");
				}
			}
		}
		
		System.out.println("Alle Spieler erstellt: " + this.players + "\n");
		round = 0;
		currentPlayer = players.get(0);
		currentPlayer.activateFigures();
		Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice().setBackground(currentPlayer.getColor().getColor());
		
		if(currentPlayer.isBot() == true) {
			this.botDice();
		}
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
		wuerfe = 0;
	}
	
//next Player
	public void nextPlayer() {
		this.currentPlayer.blockFigure();
		
		if(this.players.indexOf(this.currentPlayer) < this.players.size() -1) {
			this.currentPlayer=this.players.get(this.players.indexOf(this.currentPlayer) +1);
		} else {
			this.currentPlayer=this.players.get(0);
		}
		System.out.println("-------------" + currentPlayer.getName() + " ist dran.------------");
		wuerfe = 0;
	}
	
	private void againPlayer() {
		this.currentPlayer.blockFigure();
	}
	
///checkMove
	public void checkMove() {
		System.out.println("Ich checke jetzt was " + currentPlayer.getName() + " machen kann.");
	/// Alle Figur sind in der Base		
		 if(this.currentPlayer.baseSize() == 4){
			if(DiceAL.getInsance().getLastRoll() != 6) {//gewürfelte Nummer ist nicht 6
				if(wuerfe < 3) {
					currentFigure = null;
					this.notifyObservers();
					
					if(this.currentPlayer.isBot()) {// Bot würfelt selber
						this.botDice();
					}
				}else {					
					this.blockedFigure.clear();
					this.nextPlayer();
					this.notifyObservers();
					
					if(currentPlayer.isBot()) {
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
				if(currentPlayer.isBot()==true) {
					Thread botThread = new Thread(botRun);
					botThread.start();
				}
			} 
		 } 
		 else {
	//ckecke alle Figuren
			for(Figure figure : this.currentPlayer.getFigures()) {
				int moveTo = 0;
				figure.setMoveScore(1);
			//Figure befindet sich auf Spielfeld
				if(Arrays.asList(this.fieldFigurePosition).contains(figure)) {
				//würde ins Ziel gehen
					if(Arrays.asList(this.fieldFigurePosition).indexOf(figure) + DiceAL.getInsance().getLastRoll() >  this.currentPlayer.getEndField() && Arrays.asList(this.fieldFigurePosition).indexOf(figure) <= this.currentPlayer.getEndField()) {//cP + W > eF && cP <= eF
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
							System.out.println(currentPlayer.getColor() + " kickt " + this.fieldFigurePosition[moveTo].getColor());
							if(this.fieldFigurePosition[moveTo].getColor() == this.currentPlayer.getColor()) {
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
					moveTo = Arrays.asList(this.currentPlayer.getInHome()).indexOf(figure) + DiceAL.getInsance().getLastRoll();
					
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
				for(Figure figure : currentPlayer.getFigures()) {
					if(!blockedFigure.contains(figure)) {
						currentPlayer.activateFigures(figure);
					}
				}
				if(currentPlayer.isBot()==true) {
					Thread botThread = new Thread(botRun);
					botThread.start();
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
		else if(Arrays.asList(this.fieldFigurePosition).contains(this.currentFigure)) {
		//Figure geht ins Haus
			if(Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure) + DiceAL.getInsance().getLastRoll() >  this.currentPlayer.getEndField() && Arrays.asList(this.fieldFigurePosition).indexOf(this.currentFigure) <= this.currentPlayer.getEndField()) {//cP + W > eF && cP <= eF
				
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
		
	//Gewonnen
		if(currentPlayer.homeSize() == 4) {
			this.currentFigure.setPosition(moveTo);
			this.currentPlayer.blockFigure();
			this.blockedFigure.clear();
			this.notifyObservers();
			DiceAL.getInsance().deactivateDice(Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice());
			canMove = false;
			
			String winText = currentPlayer.getName() + " hat das Spiel gewonnen.";
			
			Object[] optionsWin = {"Neues Spiel", "OK COOL"};
			
			int btnPressed = JOptionPane.showOptionDialog(
					Controller.getInstance().getFrame() ,
					winText,
					"Glückwunsch",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon("ressources/MADLogoSmall.png"),
					optionsWin, 
					optionsWin[0]);
			
			if(btnPressed == 0){
				JTextField nameOne = new JTextField("             ");
            	nameOne.addFocusListener(new EmptyFL());
            	JTextField nameTwo = new JTextField("             ");
            	nameTwo.addFocusListener(new EmptyFL());
            	JTextField nameThree = new JTextField("             ");
            	nameThree.addFocusListener(new EmptyFL());
            	JTextField nameFour = new JTextField("             ");
            	nameFour.addFocusListener(new EmptyFL());
            	
            	JCheckBox checkOne = new JCheckBox("Bot");
            	JCheckBox checkTwo = new JCheckBox("Bot");
            	JCheckBox checkThree = new JCheckBox("Bot");
            	JCheckBox checkFour = new JCheckBox("Bot");
            	Object[] optionsNew = {nameOne, checkOne, nameTwo, checkTwo, nameThree, checkThree, nameFour, checkFour, "Start Spiel"};
            	JOptionPane.showOptionDialog(
        	 			Controller.getInstance().getFrame(),
                     	"Gib die Spieler an, welche mitspielen.\n  Gelb                       Grün                        Rot                         Blau", 
                     	"Player",            
                     	JOptionPane.OK_CANCEL_OPTION,
                     	JOptionPane.PLAIN_MESSAGE,
                     	null,//new ImageIcon("ressources/index.png")         
                     	optionsNew, 
                     	optionsNew[0]
        			 );
            	
            	int players = 4;
            	if(nameOne.getText().isBlank() && !checkOne.isSelected()) {
            		players--;
            	}
            	if(nameTwo.getText().isBlank() && !checkTwo.isSelected()) {
            		players--;
            	}
            	if(nameThree.getText().isBlank() && !checkThree.isSelected()) {
            		players--;
            	}
            	if(nameFour.getText().isBlank() && !checkFour.isSelected()) {
            		players--;
            	}
            	if(players < 2) {
            		System.out.println("Zu wenig Spieler");
            	}
            	else {
            	 	DiceAL.getInsance().removeObsever((Observer)Controller.getInstance().getCurrentGame());
                    Controller.getInstance().newGame(nameOne.getText(), checkOne.isSelected(), nameTwo.getText(), checkTwo.isSelected(),nameThree.getText(),checkThree.isSelected(), nameFour.getText(),checkFour.isSelected());
            	} 
			}
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
	
	public void botMove() {
		ArrayList<Figure> rdmMoveFigure = new ArrayList<Figure>();
		
		for(Figure figure : currentPlayer.getFigures()) {
			if(!blockedFigure.contains(figure)) {
				for(int i = 1; i <= figure.getMoveScore(); i++) {
					rdmMoveFigure.add(figure);
				}
			}
		}
		this.moveFigure(rdmMoveFigure.get(random.nextInt(rdmMoveFigure.size())));
	}
	
	private void botDice(){
		DiceAL.getInsance().rollTheDice();
		Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice().setText(Integer.toString(DiceAL.getInsance().getLastRoll()));
		DiceAL.getInsance().deactivateDice(Controller.getInstance().getFrame().getMainContent().getBoardPanel().getDice());
		Controller.getInstance().getFrame().getMainContent().getBoardPanel().revalidate();
		Controller.getInstance().getFrame().getMainContent().getBoardPanel().repaint();	
		DiceAL.getInsance().notifyObservers();	
	}
	
//Kick Player
	private void kickFigure(int moveTo) {
		int kickTo = 0;
		this.toKickFigure = this.fieldFigurePosition[moveTo];
		
		for(Player player : players) {
			if(player.getColor() == this.toKickFigure.getColor()) {
				player.addInBase(this.toKickFigure);
				kickTo = Arrays.asList(player.getInBase()).indexOf(this.toKickFigure);
			}
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
			dices.add(lastRoll);
			wuerfe = wuerfe + 1;
		}
	//Erste Runde
		if(this.round == 0) {
			currentFigure = null;
			if(this.dices.size() == this.players.size()) {
				this.orderPlayers(this.players, this.dices);
				this.round = this.round + 1;
				this.againPlayer();
				this.notifyObservers(); 
				for(Player player : players) {
					player.blockFigure();
				}
				if(currentPlayer.isBot() == true) {
					this.botDice();
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
