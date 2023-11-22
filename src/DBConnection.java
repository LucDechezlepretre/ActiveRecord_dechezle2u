import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private static Connection connection;
    private DBConnection(){
    }

    public static synchronized Connection getConnection() throws SQLException {
        if(connection == null) {
            // variables a modifier en fonction de la base
            String userName = "root";
            String password = "";
            String serverName = "localhost";
            //Attention, sous MAMP, le port est 8889
            String portNumber = "3306";

            // iL faut une base nommee testPersonne !
            String dbName = "testpersonne";

            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            Connection connect = DriverManager.getConnection(urlDB, connectionProps);
        }
        return connection;
    }
}
