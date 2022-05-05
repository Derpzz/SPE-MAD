package de.spe.control;

import javax.swing.JFrame;

import de.spe.model.Game;

public class Controller {

    private static final Controller INSTANCE = new Controller();
    private GameField frame;
    private Game currentGame;

    private Controller()
    {

    }

    public static Controller getInstance()
    {
        return INSTANCE;
    }

    public void newGame(String name)
    {
        this.currentGame = new Game(game);
    }

    public void setFrame(GameField frame)
    {
        if(this.frame==null)
            this.frame = frame;
    }

    public void saveGame()
    {
        return;
    }

    public Game loadGame()
    {
        return;
    }
}
