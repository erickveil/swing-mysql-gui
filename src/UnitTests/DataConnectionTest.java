package UnitTests;

import org.junit.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import javax.swing.*;
import classes.*;
import forms.*;


/**
 * Created by eveil on 2/24/14.
 */
public class DataConnectionTest {

    public static String user="";
    public static String pw="";
    public static String db="";

    @org.junit.Before
    public void setUp() throws Exception {  }

    public void tearDown() throws Exception {
        DeleteEntry delete_manager = new DeleteEntry(user,pw,db,"");
        delete_manager.connectDB();
        delete_manager.deleteByCity("test");
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

    @org.junit.Test
    public void testSearchByCity() throws Exception {

        //forms.MainWindow mw = new forms.MainWindow();

        String city = "";

        city = "Cleveland";
        CitySearch target=new CitySearch(city,user,pw,db, null);
        target.connectDB();

        Integer actual = target.searchPopulationByCity(city);
        Integer expected=123456;

        String msg="";
        Assert.assertEquals(msg,expected,actual);

        city = "Not in db";
        target=new CitySearch(city,user,pw,db, null);
        target.connectDB();
        actual = target.searchPopulationByCity(city);
        expected=null;
        msg="";
        Assert.assertEquals(msg,expected,actual);
    }

    /**
     * @throws Exception
     */
    @org.junit.Test
    public void testInsertEntry() throws Exception{
        String city = "test";
        int pop=10;
        int key=(int)(Math.random()*100);

        MainWindow mw = new MainWindow();

        AddEntry target = new AddEntry(city,"10",user,pw,db);
        target.connectDB();

        StringBuilder result=new StringBuilder();
        boolean actual = target.insertEntry(key,city,pop);
        boolean expected=true;
        String msg="result: "+result.toString();

        Assert.assertEquals(msg,expected,actual);

        // duplicate entries should fail
        target.connectDB();
        expected=false;
        actual = target.insertEntry(key,city,pop);
        msg="result: "+result.toString();
        Assert.assertEquals(msg,expected,actual);

        tearDown();
    }

    @org.junit.Test
    public void testUpdateEntry() throws Exception{

        String city = "test";
        int first_pop=10;
        int second_pop=20;

        AddEntry mock= new AddEntry("","10",user,pw,db);
        mock.connectDB();

        StringBuilder result=new StringBuilder();
        mock.insertEntry(50,city,first_pop);

        UpdateEntry target=new UpdateEntry(user,pw,db,"",10);
        target.connectDB();
        boolean expected=true;
        boolean actual=target.edit(city,second_pop);

        Assert.assertEquals(expected,actual);
        tearDown();
    }
}

