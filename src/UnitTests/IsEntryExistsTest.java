package UnitTests;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import javax.swing.*;
import classes.AddEntry;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;


/**
 * Created by eveil on 5/7/14.
 */
@RunWith(Parameterized.class)
public class IsEntryExistsTest{

    public static String pw;
    public static String user;
    public static String db;

    private String city;
    private boolean expected;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Cleveland",true},
                {"Seattle",false},
        });
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

    public IsEntryExistsTest(String city, boolean expected)
    {
        this.city=city;
        this.expected=expected;
    }

    @Test
    public void testIsEntryExists() throws Exception {
        String _city="";
        String pop="0";
        AddEntry target=new AddEntry(_city,pop,user,pw,db);
        boolean actual=target.isEntryExists(this.city);
        Assert.assertEquals(this.expected,actual);
    }
}
