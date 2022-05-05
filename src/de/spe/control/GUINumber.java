package de.spe.control;

public enum GUINumber {
	/*****Finals*****/
	RESET(999), ///Würfel wieder aktivieren sonst nichts
	yellowNumber  (0),
	yellowStartPoint  (0),
	yellowEndPoint  (39),
	greenNumber  (10),
	greenStartPoint  (10),
	greenEndPoint  (9),
	blueNumber  (20),
	blueStartPoint  (20),
	blueEndPoint  (19),
	redNumber  (30),
	redStartPoint  (30),
	redEndPoint  (29),
	fieldNumber  (100),
	baseNumber  (200),
	homeNumber  (300),
	WÜRFEL (3);
	
	private int number;

	private GUINumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
}
