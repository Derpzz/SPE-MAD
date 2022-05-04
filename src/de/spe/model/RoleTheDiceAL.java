package de.spe.model;

import java.util.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoleTheDiceAL extends Observable implements ActionListener{

    private List<Observer> observers = new ArrayList<Observer>();
    
    private static final RoleTheDiceAL INSTANCE = new RoleTheDiceAL();
    private final Random rand;
    private int lastRoll;

    private RoleTheDiceAL() 
    {
        rand = new Random();
        reset();
    }

    private void reset()
    {
        lastRoll = -1;
    }

    public RoleTheDiceAL getInsance()
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

    public void deactivateDice(Dice butt)
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
        
        if(e.getSource() instanceof Dice butt)
        {
            //PLS add animation
            butt.setText(Integer.toString(rollTheDice()));
            deactivateDice(butt);

        } else if(e.getSource() instanceof JButton)
        {
            System.out.println("HEY, DU! Bitte eine eigene Button Klasse namens Dice anlegen");
        }
        
    }
    
}
