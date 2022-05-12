package de.spe.control;

public class BotThread implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(Controller.getInstance().getBotTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Controller.getInstance().getCurrentGame().botMove();
		
	}

}
