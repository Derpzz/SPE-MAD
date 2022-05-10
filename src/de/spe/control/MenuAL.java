package de.spe.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MenuAL implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource() instanceof JMenuItem button)
	        {
	            switch(e.getActionCommand())
	            {
	                case "Spiel Starten":
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
	                	Object[] options = {nameOne, checkOne, nameTwo, checkTwo, nameThree, checkThree, nameFour, checkFour, "Start Spiel"};
//	                	JButton addPlayer = new JButton("Add Player");
//	                	addPlayer.addActionListener(null);
//	                	Object[] options = {addPlayer, "Start Spiel"};
	                	JOptionPane.showOptionDialog(
	            	 			Controller.getInstance().getFrame(),
	                         	"Gib die Spieler an, welche mitspielen.", 
	                         	"Player",            
	                         	JOptionPane.OK_CANCEL_OPTION,
	                         	JOptionPane.PLAIN_MESSAGE,
	                         	null,//new ImageIcon("ressources/index.png")         
	                         	options, 
	                         	options[0]
	            			 );
	                	
	                	int players = 4;
	                	if(nameOne.getText().isBlank() || nameOne.getText().isEmpty()) {
	                		players = players -1;
	                	}
	                	if(nameTwo.getText().isBlank() || nameTwo.getText().isEmpty()) {
	                		players = players -1;
	                	}
	                	if(nameThree.getText().isBlank() || nameThree.getText().isEmpty()) {
	                		players = players -1;
	                	}
	                	if(nameFour.getText().isBlank() || nameFour.getText().isEmpty()) {
	                		players = players -1;
	                	}
	                	if(players < 2) {
	                		System.out.println("Zu wenig Spieler");
	                	}
	                	else {
	                	 	DiceAL.getInsance().removeObsever((Observer)Controller.getInstance().getCurrentGame());
		                    Controller.getInstance().newGame(nameOne.getText(), checkOne.isSelected(), nameTwo.getText(), checkTwo.isSelected(),nameThree.getText(),checkThree.isSelected(), nameFour.getText(),checkFour.isSelected());
	                	}               
	            }
	        }
	}

}
