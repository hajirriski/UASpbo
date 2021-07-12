package database;
import java.sql.*;
import javax.swing.JOptionPane;
public class DBkonek {
	static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sewabuku";
	static final String USER = "root";
	static final String PASS = "";
	
	public static Connection Koneksi() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Konek dengan database");
            return null;
        }
    }
}