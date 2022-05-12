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
		JMenuItem startGameItem, botTimeItem, informationItem, creditItem;
		
		menuBar = this;
		
		menu = new JMenu("Menü");
		menuBar.add(menu);
		
		startGameItem = new JMenuItem("Spiel Starten");
		startGameItem.addActionListener(new MenuAL());
		menu.add(startGameItem);
		
		option = new JMenu("Optionen");
		menuBar.add(option);
		
		botTimeItem = new JMenuItem("Bot Zeit");
		botTimeItem.addActionListener(new MenuAL());
		option.add(botTimeItem);
		
		info = new JMenu("Info");
		menuBar.add(info);
		
		informationItem = new JMenuItem("Spielregeln");
		informationItem.addActionListener(new MenuAL());
		info.add(informationItem);
		
		creditItem = new JMenuItem("Credits");
		creditItem.addActionListener(new MenuAL());
		info.add(creditItem);
		
		frame.setJMenuBar(menuBar);
	}
}
