package de.spe.model;

import java.awt.Color;

public enum Colors {
	
	Yellow(new Color(0xffff00)),
	YellowBlock(new Color(0xcccc11)),
	Green(new Color(0x00ff00)),
	GreenBlock(new Color(0x11cc11)),
	Red(new Color(0xff0000)),
	RedBlock(new Color(0xcc1111)),
	Blue(new Color(0x0000ff)),
	BlueBlock(new Color(0x1111cc));
	
	private final Color color;

	private Colors(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
