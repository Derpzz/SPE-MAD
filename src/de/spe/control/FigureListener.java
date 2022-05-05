package de.spe.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FigureListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller.currentGame.moveFigure(e);
		
	}

}
