package de.spe.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeepRatioPanel extends JPanel{
	
	private BoardPanel boardPanel;

	public KeepRatioPanel() {
		this.setLayout(new SingleComponentAspectRatioKeeperLayout());
		boardPanel = new BoardPanel();
		this.add(boardPanel);
		this.setBackground(new Color(0xfcfc8d));
	}

	
	/*
	 * Getter and Setter
	 */
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	
}

