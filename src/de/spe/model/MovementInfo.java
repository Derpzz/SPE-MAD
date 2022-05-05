package de.spe.model;

import java.awt.Color;
import java.util.Objects;

public class MovementInfo {

    private Figure figure;
    private int position;
    private Color color;
    private Area area;

    /**
     * Constructs a new information object with the given parameters.
     * Origin and destination areas are being set to Main per default.
     * 
     * @param figure current figure that will be moved
     * @param distance distance the figure should move forward
     * @param player color of the player whose turn is the current turn
     */
    public MovementInfo(Figure figure, int position, Color color, Area area)
    {
        this.figure = figure;
        this.position = position;
        this.color = color;
        this.area = area;
    }

/*****GetterAndSetter*****/
    public Figure getFigure() {
        return this.figure;
    }

    public int getDistance() {
        return this.position;
    }

    public Color getPlayer() {
        return this.color;
    }

    public Area getDestinationArea() {
        return this.area;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MovementInfo)) {
            return false;
        }
        MovementInfo movementInfo = (MovementInfo) o;
        return Objects.equals(figure, movementInfo.figure) && position == movementInfo.position && Objects.equals(color, movementInfo.color) && Objects.equals(area, movementInfo.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figure, position, color, area);
    }

}
