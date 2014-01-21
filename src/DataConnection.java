import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by eveil on 1/21/14.
 */
public class DataConnection implements Runnable{

    private String user;
    private String password;
    private String database;
    private Connection connect;

    public DataConnection(String p_user, String p_password, String p_database)
    {
        user=p_user;
        password=p_password;
        database=p_database;
    }

    public void run()
    {
        try{
            connectDB();
        }
        catch(Exception e){
            System.err.println(e.toString());
            return;
        }
        System.out.println("Database connected.");
    }

    /**
     * Get the connector from:
     * http://dev.mysql.com/downloads/connector/j
     *
     * @throws Exception
     */
    public void connectDB() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        connect= DriverManager.getConnection("jdbc:mysql://localhost/"+database+"?user="+user+"&password="+password);
    }

    public void searchByCity()
    {

    }
}
