package de.spe.control;

import java.sql.*;
import com.mysql.jdbc.Driver;

public class DatabaseConnector {

    private static final String conString = "jdbc:mysql://localhost:3306/";

    public static void execute(String sqlString) throws SQLException
    {
        try (Connection con = DriverManager.getConnection(conString, "root", "")) {
            Statement stmt= con.createStatement();
            stmt.execute("USE mad");  
            stmt= con.createStatement();
            stmt.execute(sqlString);  
        } catch (Exception e) {
            throw e;
        }
    }

    public static int executeGetID(String sqlString) throws SQLException
    {
        int key = -1;
        try (Connection con = DriverManager.getConnection(conString, "root", "")) {
            Statement stmt= con.createStatement();
            stmt.execute("USE mad");  
            stmt= con.createStatement();
            stmt.executeUpdate(sqlString, Statement.RETURN_GENERATED_KEYS);  

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                key=rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return key;
    }
    
    public static ResultSet executeQuery(String sqlString) throws SQLException
    {
        try (Connection con = DriverManager.getConnection(conString, "root", "")) {
            Statement stmt= con.createStatement();
            stmt.execute("USE mad");  
            stmt= con.createStatement();
            return stmt.executeQuery(sqlString);  
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean executeIsQueryEmpty(String sqlString) throws SQLException
    {
        try (Connection con = DriverManager.getConnection(conString, "root", "")) {
            Statement stmt= con.createStatement();
            stmt.execute("USE mad");  
            stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString); 
            if(rs.next())
                return false;
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
