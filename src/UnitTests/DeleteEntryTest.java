package UnitTests;

import classes.AddEntry;
import classes.DeleteEntry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import javax.swing.*;

import static org.junit.Assert.*;

public class DeleteEntryTest {

    private static String pw;
    private static String user;
    private static String db;

    /**
     * used to run to obtain db connection first
     */
    @BeforeClass
    public static void getPassword() {
        pw = JOptionPane.showInputDialog("MySQL Password:");
        user="root";
        db="javatest";
    }

    @Test
    public void testDeleteByCity() throws Exception
    {
        String city="test";
        String pop="1";
        AddEntry mock_maker =new AddEntry(city,pop,user,pw,db);
        mock_maker.connectDB();
        mock_maker.insertEntry(10,city,new Integer(pop));

        DeleteEntry target = new DeleteEntry(user,pw,db,city);
        target.connectDB();
        boolean actual = target.deleteByCity(city);
        boolean expected = true;

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testFailDeleteByCity() throws Exception
    {
        String city="";

        DeleteEntry target = new DeleteEntry(user,pw,db,city);
        target.connectDB();
        boolean actual = target.deleteByCity(city);
        boolean expected = false;

        String expected_status="EMPTY_CITY";

        Assert.assertEquals(expected,actual);
        Assert.assertEquals(expected_status,target.return_status);
    }

    @Test(expected = Exception.class)
    public void testExceptionDeleteByCity() throws Exception
    {
        String city="Not There";

        DeleteEntry target = new DeleteEntry(user,pw,db,city);
        target.connectDB();
        target.deleteByCity(city);
    }
}
