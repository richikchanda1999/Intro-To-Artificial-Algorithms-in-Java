import java.sql.*;
import java.util.Properties;

public class DBDemo
{    // Connection string. emotherearth is the database the program
    // is connecting to. You can include user and password after this
    // by adding (say) ?user=paulr&password=paulr. Not recommended!

    private static final String CONNECTION =
            "jdbc:mysql://127.0.0.1/";

    public static void main(String[] args) throws
            ClassNotFoundException,SQLException
    {
        // Properties for user and password. Here the user and password are both 'paulr'
        Properties p = new Properties();
        p.put("user","richik");
        p.put("password","Rsdc123409#");

        // Now try to connect
        Connection c = DriverManager.getConnection(CONNECTION,p);

        System.out.println("It works !");
        c.close();
    }
}