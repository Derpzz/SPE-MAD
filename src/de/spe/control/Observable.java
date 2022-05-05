package de.spe.control;

import de.spe.model.Figure;

public interface Observable {
	public abstract void addObserver(Observer observer);
	public abstract void removeObsever(Observer observer);
	public abstract void notifyObservers();
	public abstract Figure getData();
}
