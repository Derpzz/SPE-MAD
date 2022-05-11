package de.spe.model;

import java.awt.Color;

public enum Colors {
	
	Yellow(new Color(0xffcc00)),
	YellowBlock(new Color(0xffcc00)),
	YellowField(new Color(0xffd700)),
	
	Green(new Color(0x5e752e)),
	GreenBlock(new Color(0x5e752e)),
	GreenField(new Color(0x6b8e23)),
	
	Red(new Color(0x962323)),
	RedBlock(new Color(0x962323)),
	RedField(new Color(0xb22222)),
	
	Blue(new Color(0x3a6b94)),
	BlueBlock(new Color(0x3a6b94)),
	BlueField(new Color(0x4682B4)),
	
	Background(new Color(0xe0c073)),
	BackBackground(new Color(0xccaf68)),
	
	White(new Color(0xf5f5f5));
	
	private final Color color;

	private Colors(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
