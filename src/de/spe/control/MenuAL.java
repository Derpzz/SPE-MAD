package de.spe.control;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
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
	                	JOptionPane.showOptionDialog(
	            	 			Controller.getInstance().getFrame(),
	                         	"Gib die Spieler an, welche mitspielen.\n  Gelb                       Grün                        Rot                         Blau", 
	                         	"Player",            
	                         	JOptionPane.OK_CANCEL_OPTION,
	                         	JOptionPane.PLAIN_MESSAGE,
	                         	null,//new ImageIcon("ressources/index.png")         
	                         	options, 
	                         	options[0]
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
	                break;
	                case "Bot Zeit":
	                	Object[] optionsBotTime = {"0,01 Sekunden", "0,1 Sekunden", "1 Sekunde",  "1,5 Sekunde", "2 Sekunden"};
	                	String botTimeString = (String)JOptionPane.showInputDialog(
	            	 			Controller.getInstance().getFrame(),
        	                    "Zeit die ein Bot braucht um einen Zug auszuführen:\n",
        	                    "Bot Zeit",
        	                    JOptionPane.PLAIN_MESSAGE,
        	                    null,
        	                    optionsBotTime,
        	                    optionsBotTime[0]);

	                	System.out.println(botTimeString);
	                	if(botTimeString != null) {
	                		switch(botTimeString) {
		                	case "0,01 Sekunden":
		                		Controller.getInstance().setBotTime(10);
		                	break;
		                	case "0,1 Sekunden":
		                		Controller.getInstance().setBotTime(100);
		                	break;
		                	case "1 Sekunde":
		                		Controller.getInstance().setBotTime(1000);
		                	break;
		                	case "1,5 Sekunden":
		                		Controller.getInstance().setBotTime(1500);
		                	break;
		                	case "2 Sekunden":
		                		Controller.getInstance().setBotTime(2000);
		                	break;
	                	}
	                }
	                break;	
	                case "Spielregeln":
	                	Object[] objectRule = {"Verstanden"};
	                	String spielregeln = 
	                			"Das Spiel kann von zwei bis vier Spielern gespielt werden. Jeder Spieler besitzt vier Figuren aus einer Farbe, die sich zu Beginn des Spiels in ihrem Startfeld befinden.\n"
	                			+ "Ziel ist es, alle seine vier Figuren einmal, um das ganze Spielbrett zu bewegen und anschließend die Zielfelder zu besetzen. \r\n"
	                			+ "\r\n"
	                			+ "Beginn: \r\n"
	                			+ "\r\n"
	                			+ "Jeder Spieler darf am Anfang einmal würfeln. Der Spieler, der die größte Zahl gewürfelt hat, beginnt. \n"
	                			+ "Er hat die Chance innerhalb von drei Würfen eine sechs zu würfeln. Danach geht es im Uhrzeigersinn reihum. Alle Spieler wechseln sich mit dem Würfeln so lange ab, bis eine sechs fällt. \n"
	                			+ "Bei einer gewürfelten sechs darf der jeweilige Spieler eine Figur aus seinem Startfeld herausnehmen und auf das Anfangsfeld seiner zugehörigen Farbe stellen, danach darf dieser erneut würfeln.  \r\n"
	                			+ "\r"
	                			+ "\r\n"
	                			+ "Spielverlauf: \r\n"
	                			+ "\r\n"
	                			+ "Befindet sich bereits eine Figur des Spielers auf dem Spielfeld, darf nur noch einmal gewürfelt werden und der Spieler muss eine seiner Figuren um die gewürfelte Augenzahl auf dem Spielbrett weiterbewegen. \n"
	                			+ "Bei einer sechs darf dieser erneut würfeln. \r\n"
	                			+ "\r\n"
	                			+ "Befindet sich beim Vorrücken der jeweiligen Augenzahl auf dem letzten Feld eine gegnerische Figur, so wird diese „geschlagen“ und somit zurück in ihr Startfeld geschickt. \n"
	                			+ "Es ist nicht möglich auf ein Feld mit seiner eigenen Figur zu gehen. Auf dem Weg liegende Figuren werden übersprungen. Nach jedem Wurf ist der nächste Spieler dran. \r\n"
	                			+ "\r\n"
	                			+ "Endphase: \r\n"
	                			+ "\r\n"
	                			+ "Nach einer Umrundung des gesamten Feldes, können die Figuren auf die markierten Zielfelder der gleichen Farbe gesetzt werden. \n"
	                			+ "Es darf keine Figur einer anderen Farbe auf ein Zielfeld des Gegners. \n"
	                			+ "Wenn ein Spieler es schafft, alle seine vier Figuren in die Zielfelder zu rücken, hat dieser das Spiel gewonnen. ";
	                	
	                	JOptionPane.showOptionDialog(
	            	 			Controller.getInstance().getFrame(),
	                         	spielregeln, 
	                         	"Spielregeln",            
	                         	JOptionPane.OK_CANCEL_OPTION,
	                         	JOptionPane.PLAIN_MESSAGE,
	                         	null,//new ImageIcon("ressources/index.png")         
	                         	objectRule, 
	                         	objectRule[0]
	            			 );
	                break;
	                case "Credits":
	                	Object[] objectCredit = {"Spenden", "Fresh"};
	                	
	                	int click = JOptionPane.showOptionDialog(
	            	 			Controller.getInstance().getFrame(),
	                         	"Mensch ärgere Dich nicht! by Gent, Adem, Jannik", 
	                         	"Credits",            
	                         	JOptionPane.OK_CANCEL_OPTION,
	                         	JOptionPane.PLAIN_MESSAGE,
	        					new ImageIcon("ressources/MADLogoSmall.png"),        
	                         	objectCredit, 
	                         	objectCredit[0]
	            			 );
	                	
	                	if(click == 0) {
	                		URL url = null;
	                		try {
	                			url = new URL("http://paypal.me/jannikohme");
	                		} catch (MalformedURLException me) {
	                			me.printStackTrace();
	                		}
	                		if(Desktop.isDesktopSupported()){
	                			try {
	                				Desktop.getDesktop().browse(url.toURI());
	                			} catch (IOException ioe) {
	                				ioe.printStackTrace();
	                			} catch (URISyntaxException se) {
	                				se.printStackTrace();
	                			}
	                		}
	                	}
		            break;
	            }
	        }
	}

}
