package de.spe.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.spe.model.Dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceAL  implements ActionListener, Observable{

    private List<Observer> observers = new ArrayList<Observer>();
    
    private static final DiceAL INSTANCE = new DiceAL();
    private final Random rand;
    private Integer lastRoll;

    private DiceAL() 
    {
        rand = new Random();
        reset();
    }

    /**
     * Resets the last rolled number to become unvaild.
     * In this case -1.
     */
    private void reset()
    {
        lastRoll = -1;
    }

    /**
     * Gets singleton instance of the dice.
     * @return dice object
     */
    public static DiceAL getInsance()
    {
        return INSTANCE;
    }

    /**
     * Rolls the dice
     * @return number between 1 and 6 (inclusive)
     */
    public Integer rollTheDice()
    {
        return lastRoll = rand.nextInt(6) + 1; 
    }

    public Integer getLastRoll()
    {
        return lastRoll;
    }

    /**
     * Disables the dice button, so that it becomes unclickable.
     * @param button button that is to be disabled.
     */
    public void deactivateDice(Dice button)
    {
    	button.setEnabled(false);
    }

    @Override
    public void addObserver(Observer observer)
    {
        if(observer != null && !observers.contains(observer))
            observers.add(observer);
    }
    
	@Override
	public void removeObsever(Observer observer) {
		observers.remove(observer);
		
	}
    
    @Override
    public void notifyObservers()
    {
        for (Observer observer : observers)
        {
            observer.update(this, lastRoll);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() instanceof Dice diceBtn)
        {
            //PLS add animation PLLLLSSSSS
        	rollTheDice();
        	diceBtn.setText(Integer.toString(lastRoll));
            if(Controller.getInstance().getCurrentGame() != null){
            	this.deactivateDice(diceBtn);
            }
            
            notifyObservers();
        } 
    }    
}
