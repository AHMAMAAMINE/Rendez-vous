package sample.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {


    public ConnectionUtil() {
    }

    public static Connection conDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rendezvous", "root", "");
            return con;
        } catch (SQLException | ClassNotFoundException var1) {
            System.err.println("ConnectionUtil : " + var1.getMessage());
            return null;
        }
    }
}

