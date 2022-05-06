package de.spe.control;

import de.spe.model.Game;
import de.spe.view.MainMadFrame;

public class Controller {

    private static final Controller INSTANCE = new Controller();
    private MainMadFrame frame;
    private Game currentGame;

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
    public void newGame(String yName, boolean yBot, String gName, boolean gBot, String bName, boolean bBot, String rName, boolean rBot)
    {
        this.currentGame = new Game(yName, yBot, gName, gBot, bName, bBot, rName, rBot);
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

    /**
     * Saves the current game
     */
    public void saveGame()
    {
        return;
    }

    /**
     * Loads the specified game and sets it as the current game.
     * @return loaded game
     */
    public Game loadGame()
    {
        this.currentGame = currentGame;
        return currentGame;
    }

	public MainMadFrame getFrame() {
		return frame;
	}

	public Game getCurrentGame() {
		return currentGame;
	}
    
    /**
     * GetterAndSetter
     */
    
}
