package de.spe.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{

	public MenuBar(JFrame frame) {
		JMenuBar menuBar;
		JMenu menu, option, info;
		JMenuItem startGameItem, saveGameItem, loadGameItem, informationItem;
		
		menuBar = this;
		
		menu = new JMenu("Menü");
		menuBar.add(menu);
		
		startGameItem = new JMenuItem("Spiel Starten");
		menu.add(startGameItem);
		
	    saveGameItem = new JMenuItem("Spiel Speichern");
		menu.add(saveGameItem);
		
		loadGameItem = new JMenuItem("Spiel Laden");
		menu.add(loadGameItem);
		
		option = new JMenu("Optionen");
		menuBar.add(option);
		
		info = new JMenu("Optionen");
		menuBar.add(info);
		
		informationItem = new JMenuItem("Spielregeln");
		info.add(informationItem);
		
		frame.setJMenuBar(menuBar);
	}
}
