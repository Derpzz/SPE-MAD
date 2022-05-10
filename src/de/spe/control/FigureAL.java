package de.spe.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.spe.model.Figure;

public class FigureAL implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof Figure figure){
			Controller.getInstance().getCurrentGame().moveFigure(figure);
		}
		
		
	}

}
