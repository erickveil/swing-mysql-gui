import junit.framework.Assert;
import org.junit.After;
import org.junit.BeforeClass;

import javax.swing.*;

/**
 * I'm going to go ahead and just throw all of my tests in this class.
 * Lets me only gather pw once, easily.
 *
 * Created by eveil on 2/24/14.
 */
public class DataConnectionTest {

    public static String user="";
    public static String pw="";
    public static String db="";

    @org.junit.Before
    public void setUp() throws Exception {  }


    @After
    public void tearDown() throws Exception {

        // todo: delete the Test entries from the database
        // delete from database where city="test";
    }


    /**
     * used to run to obtain db connection first
     */
    @BeforeClass
    public static void getPassword() {
        pw = JOptionPane.showInputDialog("MySQL Password:");
        user="root";
        db="javatest";
    }

    @org.junit.Test
    public void testConnectDB() throws Exception {
        DataConnection target=new DataConnection(user,pw,db);
        target.connectDB();
    }

    /*
    Todo: make test for edit method
    Todo: make sure edit method follows new TDD procedure
    Todo: determine why testUpdateEntry fails
     */

    @org.junit.Test
    public void testSearchByCity() throws Exception {

        MainWindow mw = new MainWindow();

        String city = "";

        city = "Cleveland";
        CitySearch target=new CitySearch(city,user,pw,db, mw);
        target.connectDB();

        Integer actual = target.searchByCity(city);
        Integer expected=123456;

        String msg="";
        Assert.assertEquals(msg,expected,actual);

        city = "Not in db";
        target=new CitySearch(city,user,pw,db, mw);
        target.connectDB();
        actual = target.searchByCity(city);
        expected=null;
        msg="";
        Assert.assertEquals(msg,expected,actual);
    }

    @org.junit.Test
    public void testInsertEntry() throws Exception{
        String city = "Test";
        int pop=10;
        int key=(int)(Math.random()*100);

        MainWindow mw = new MainWindow();

        AddEntry target = new AddEntry(city,"10",user,pw,db);
        target.connectDB();

        int actual = target.insertEntry(key,city,pop);
        int expected=1;
        String msg="";

        Assert.assertEquals(msg,expected,actual);

    }

    @org.junit.Test
    public void testUpdateEntry() throws Exception{

        String city = "Test";
        int first_pop=10;
        int second_pop=20;

        AddEntry mock= new AddEntry("","10",user,pw,db);
        mock.connectDB();
        mock.insertEntry(50,city,first_pop);

        UpdateEntry target=new UpdateEntry(user,pw,db,"",10);
        target.connectDB();
        int expected=1;
        int actual=target.edit(city,second_pop);

        Assert.assertEquals(expected,actual);
    }
}

