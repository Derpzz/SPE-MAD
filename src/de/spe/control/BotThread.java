package de.spe.control;

public class BotThread implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(1500);
			System.out.println("I sleep");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Controller.getInstance().getCurrentGame().botMove();
		
	}

}