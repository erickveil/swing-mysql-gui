package classes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by eveil on 1/21/14.
 */
public class DataConnection implements Runnable{

    private String user;
    private String password;
    private String database;

    public Connection connect;

    protected String return_status;

    public DataConnection(String p_user, String p_password, String p_database)
    {
        user=p_user;
        password=p_password;
        database=p_database;
    }

    /**
     * Start point of the thread. Any fatal error ends up caught here.
     */
    public void run()
    {
        try{
            connectDB();
        }
        catch(ClassNotFoundException ex){
            String msg="ClassNotFound exception, likely caused from a " +
                    "missing jdbc driver.\nDownload driver at http:dev.mysql" +
                    ".com/downloads/connector/j\nIn the project tree, " +
                    "right click your jdk library under External Libraries, " +
                    "Open library settings, Press + and add a path to the " +
                    "jar file for th jdbc.";
            JOptionPane.showMessageDialog(null,msg,"No database connector " +
                    "detected",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){

            reportFatalException(e);
            return;
        }
        System.out.println("Database connected.");
    }

    /**
     * When fatal exceptions are caught, all descendant classed handle them
     * the same way at their own thread entry points.
     *
     * @param e Exception The thrown fatal exception
     */
    protected void reportFatalException(Exception e)
    {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,e.getMessage(),
                "Connection Error",JOptionPane.ERROR_MESSAGE);
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
        connect= DriverManager.getConnection("jdbc:mysql://localhost/"+
                database+"?user="+user+"&password="+password);
    }
}
