package de.spe.view;

import javax.swing.JPanel;

import de.spe.model.Colors;

public class KeepRatioPanel extends JPanel{
	
	private BoardPanel boardPanel;

	public KeepRatioPanel() {
		this.setLayout(new SingleComponentAspectRatioKeeperLayout());
		boardPanel = new BoardPanel();
		this.add(boardPanel);
		this.setBackground(Colors.BackBackground.getColor());
	}

	
	/*
	 * Getter and Setter
	 */
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	
}

