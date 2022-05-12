package de.spe.model;

import java.sql.SQLException;

public interface Saveable {
    public void save() throws SQLException;
}
