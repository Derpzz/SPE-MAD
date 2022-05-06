package de.spe.model;

public enum GUINumber {
	/*****Finals*****/
	yellowStartPoint  (0),
	yellowEndPoint  (39),
	greenStartPoint  (10),
	greenEndPoint  (9),
	redStartPoint  (20),
	redEndPoint  (19),
	blueStartPoint  (30),
	blueEndPoint  (29),
	WÜRFEL (6);
	
	private int number;

	private GUINumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
}
