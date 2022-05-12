package de.spe.main;

import de.spe.control.Controller;
import de.spe.control.DatabaseConnector;
import de.spe.view.MainMadFrame;

public class MADMain {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Lets GOOO\n");
		
		//int wasDeinID = DatabaseConnector.executeGetID("INSERT INTO t_game VALUES()");
		//System.out.println(wasDeinID);
		Controller.getInstance().setFrame(new MainMadFrame());
	}

}
