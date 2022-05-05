package de.spe.control;

public enum GUINumber {
	/*****Finals*****/
	RESET(999), ///Würfel wieder aktivieren sonst nichts
	yellowStartPoint  (0),
	yellowEndPoint  (39),
	greenStartPoint  (10),
	greenEndPoint  (9),
	blueStartPoint  (20),
	blueEndPoint  (19),
	redStartPoint  (30),
	redEndPoint  (29),
	WÜRFEL (2);
	
	private int number;

	private GUINumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
}
