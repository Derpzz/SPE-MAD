package de.spe.main;

import de.spe.model.Game;
import de.spe.view.MainMadFrame;

public class MADMain {

	public static void main(String[] args) {
		
		System.out.println("Lets GOOO\n");

		new Game("Gent",false,"",false,"Adem",false,"Jannik",false);		        
		System.out.println("\nENDE");
		MainMadFrame mainfr = new MainMadFrame();
	}

}
