package de.spe.model;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import de.spe.control.Controller;
import de.spe.control.DatabaseConnector;
import de.spe.control.FigureAL;

@SuppressWarnings("serial")
public class Figure extends JButton implements Saveable{
	
/*****Attribute*****/
	private int position;
	private Colors color;
	private Area area;
	private int moveScore;
	private Player player;
	
/*****Constructor*****/
	public Figure(Colors color, int position, Player player) {
		super();
		this.color = color;
		this.position = position;
		this.area = Area.Base;
		this.player = player;
		
		this.setPreferredSize(new Dimension(25,25));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.addActionListener(new FigureAL());
	}
	
/*****GetterAndSetter*****/
	public Colors getColor() {
		return color;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int currentPosition) {
		this.position = currentPosition;
	}

	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public int getMoveScore() {
		return moveScore;
	}
	public void addMoveScore(int moveScore) {
		this.moveScore = this.moveScore + moveScore;
	}
	public void setMoveScore(int moveScore) {
		this.moveScore = moveScore;
	}

	@Override
	public String toString() {
		return "Figure [position=" + position + ", color=" + color + ", area=" + area + ", moveScore=" + moveScore
				+ "]";
	}

	@Override
	public void save() throws SQLException {
		Game currentGame = Controller.getInstance().getCurrentGame();

		int lastID = -1;
		//currentPlayer.save();
		if((lastID = player.getLastID()) == -1)
			throw new SQLException("Failed to retrieve Player");

		boolean movable = !currentGame.getBlockedFigures().contains(this);
		
		DatabaseConnector.execute("INSERT INTO t_figure (position, movable, area, player_id) VALUES(" +  position + ", " + movable + ", '" + area.name().toLowerCase() + "', " + lastID + ")");
		
	}
	
/*****toStrong*****/
	
}
