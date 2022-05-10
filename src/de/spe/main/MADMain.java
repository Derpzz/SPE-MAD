package de.spe.main;

import de.spe.control.Controller;
import de.spe.view.MainMadFrame;

public class MADMain {

	public static void main(String[] args) {
		
		System.out.println("Lets GOOO\n");
		
		Controller.getInstance().setFrame(new MainMadFrame());
		
		Controller.getInstance().newGame("Adem",true, "Jannik", true,"Bot", true, "Gent", true);
		
		
        
		System.out.println("\nENDE");
		
		for(int i = 0; i<10; i++) {
			System.out.println(i);
			continue;
		}
	}

}
