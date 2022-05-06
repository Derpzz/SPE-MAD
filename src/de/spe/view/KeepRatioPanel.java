package de.spe.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeepRatioPanel extends JPanel{

	public KeepRatioPanel() {
		this.setLayout(new SingleComponentAspectRatioKeeperLayout());
		this.add(new BoardPanel());
		this.setBackground(new Color(0xfcfc8d));

		
		
		
		
		
	}
}
