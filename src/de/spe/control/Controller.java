package de.spe.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import de.spe.model.Game;
import de.spe.view.MainMadFrame;

public class Controller {

    private static final Controller INSTANCE = new Controller();
    private MainMadFrame frame;
    private Game currentGame;
    
    private int botTime = 1500;

    private Controller()
    {
    }

    /**
     * Gets singleton instance of the dice.
     * @return dice object
     */
    public static Controller getInstance()
    {
        return INSTANCE;
    }

    /**
     * Creates and saves a new game instance while setting it as the current game.
     * @param yName name of yellow player
     * @param gName name of green player
     * @param bName name of blue player
     * @param rName name of red player
     */
    public void newGame(String yName, String gName, String bName, String rName)
    {
        try {
			this.currentGame = new Game(yName, gName, bName, rName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    public void newGame(String yName, boolean yBot, String gName, boolean gBot, String bName, boolean bBot, String rName, boolean rBot)
    {
        try {
			this.currentGame = new Game(yName, yBot, gName, gBot, bName, bBot, rName, rBot);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    /**
     * Enables the controller to acces the application frame. 
     * THIS METHOD SHOULD ONLY BE EXCUTED ONCE ON STARTUP.
     * @param frame
     */
    public void setFrame(MainMadFrame frame)
    {
        if(this.frame==null)
            this.frame = frame;
    }

	public MainMadFrame getFrame() {
		return frame;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public int getBotTime() {
		return botTime;
	}

	public void setBotTime(int botTime) {
		this.botTime = botTime;
	}  
	
}
