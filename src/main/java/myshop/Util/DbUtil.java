package myshop.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String URL = "JDBC:mysql://localhost:3306/test";
    private static final String USERNAME = "java1";
    private static final String PASSWORD = "";

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("database connection failed");
            e.printStackTrace();
            throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            dbConnect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryStmt);
        } catch (Exception e) {
            System.out.println("Problem executing the query " + e);
        }
        return rs;
    }

    public static ResultSet dbExecuteUpdate(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(queryStmt);
        } catch (Exception e) {
            System.out.println("Problem executing the update query " + e);
        }
        return rs;
    }

}
