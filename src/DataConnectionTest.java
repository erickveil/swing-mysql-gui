import junit.framework.Assert;

import javax.swing.*;

/**
 * Created by eveil on 2/24/14.
 */
public class DataConnectionTest {

    public String user="";
    public String pw="";
    public String db="";

    @org.junit.Before
    public void setUp() throws Exception {

        pw = JOptionPane.showInputDialog("MySQL Password:");
        user="root";
        db="javatest";

    }

    @org.junit.Test
    public void testConnectDB() throws Exception {
        DataConnection target=new DataConnection(user,pw,db);
        target.connectDB();
    }

    // I'm going to go ahead and just throw all of my tests in this class.
    // Lets me only gather pw once, easily.

    @org.junit.Test
    public void testSearchByCity() throws Exception {

        String city = "Cleveland";

        MainWindow mw = new MainWindow();

        CitySearch target=new CitySearch(city,user,pw,db, mw);
        target.connectDB();

        int actual = target.searchByCity(city);
        int expected=123456;
        String msg="";

        Assert.assertEquals(msg,expected,actual);

    }
}

