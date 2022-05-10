package de.spe.main;

import de.spe.control.Controller;
import de.spe.view.MainMadFrame;

public class MADMain {

	public static void main(String[] args) {
		
		System.out.println("Lets GOOO\n");
		
		Controller.getInstance().setFrame(new MainMadFrame());
	}

}
