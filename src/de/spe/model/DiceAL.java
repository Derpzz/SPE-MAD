package de.spe.model;

// TODO PLS change imports for new Observer
import java.util.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceAL extends Observable implements ActionListener{

    private List<Observer> observers = new ArrayList<Observer>();
    
    private static final DiceAL INSTANCE = new DiceAL();
    private final Random rand;
    private int lastRoll;

    private DiceAL() 
    {
        rand = new Random();
        reset();
    }

    private void reset()
    {
        lastRoll = -1;
    }

    public static DiceAL getInsance()
    {
        return INSTANCE;
    }

    public int rollTheDice()
    {
        return lastRoll = rand.nextInt(6) + 1; 
    }

    public int getLastRoll()
    {
        return lastRoll;
    }

    public void deactivateDice(Dice button)
    {
        butt.setEnabled(false);
    }

    @Override
    public void addObserver(Observer observer)
    {
        if(observer != null && !observers.contains(observer))
            observers.add(observer);
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
        
        if(e.getSource() instanceof Dice button)
        {
            //PLS add animation
            butt.setText(Integer.toString(rollTheDice()));
            deactivateDice(butt);

        } 
    }
    
}
