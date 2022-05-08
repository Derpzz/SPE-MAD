package de.spe.model;

import java.awt.Color;

public enum Colors {
	
	Yellow(new Color(0xffff00)),
	Green(new Color(0x00ff00)),
	Red(new Color(0xff0000)),
	Blue(new Color(0x0000ff))
	;
	
	private final Color color;

	private Colors(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
