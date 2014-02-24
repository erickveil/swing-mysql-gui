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
}
