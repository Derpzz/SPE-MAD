package de.spe.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.spe.control.MenuAL;

public class MenuBar extends JMenuBar{

	public MenuBar(JFrame frame) {
		JMenuBar menuBar;
		JMenu menu, option, info;
		JMenuItem startGameItem, saveGameItem, loadGameItem, informationItem;
		
		menuBar = this;
		
		menu = new JMenu("Menü");
		menuBar.add(menu);
		
		startGameItem = new JMenuItem("Spiel Starten");
		startGameItem.addActionListener(new MenuAL());
		menu.add(startGameItem);
		
		option = new JMenu("Optionen");
		menuBar.add(option);
		
		info = new JMenu("Info");
		menuBar.add(info);
		
		informationItem = new JMenuItem("Spielregeln");
		info.add(informationItem);
		
		frame.setJMenuBar(menuBar);
	}
}
