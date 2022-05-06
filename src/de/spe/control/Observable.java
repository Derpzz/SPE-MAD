package de.spe.control;

public interface Observable {
	public abstract void addObserver(Observer observer);
	public abstract void removeObsever(Observer observer);
	public abstract void notifyObservers();
}
