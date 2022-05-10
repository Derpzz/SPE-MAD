package de.spe.control;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class EmptyFL implements FocusListener{

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField textField) {
			if(textField.getText().isBlank() || textField.getText().isEmpty()) {
				textField.setText("");
	        }
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}

}
