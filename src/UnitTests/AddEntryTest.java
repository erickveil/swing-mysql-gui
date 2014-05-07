package UnitTests;

import org.junit.Test;
import org.junit.BeforeClass;
import javax.swing.*;
import classes.AddEntry;


/**
 * Created by eveil on 5/7/14.
 */
public class AddEntryTest {

    public static String pw;
    public static String user;
    public static String db;

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
    public void testIsEntryExists() throws Exception {
        String city="";
        String pop="0";
        AddEntry target=new AddEntry(city,pop,user,pw,db);
    }
}
