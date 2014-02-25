import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by eveil on 2/25/14.
 */
public class AddEntry extends DataConnection{

    private String new_city;
    private int new_population;

    public AddEntry(String p_city, String p_pop, String p_user,String p_pw,
                    String p_db)
    {
        super(p_user,p_pw,p_db);
        new_city=p_city;
        new_population=Integer.parseInt(p_pop);
    }

    @Override
    public void run()
    {
        JFrame frame=new JFrame("Insert");
        try{
            int key=(int)(Math.random()*100);
            if(insertEntry(key,new_city,new_population)!=1){
                JOptionPane.showMessageDialog(frame,"Failed to add new entry");
            }
            else{
                JOptionPane.showMessageDialog(frame,
                        "Entry added successfully.");
            }
        }

        catch(Exception e){
            JOptionPane.showMessageDialog(frame,"Failed to add new entry");
            System.err.println(e.toString());
            return;
        }
        System.out.println("Insert complete");
    }

    public int insertEntry(int key, String city, int pop) throws SQLException
    {

        String query_str="INSERT INTO javatest (no,city,population) VALUES(?," +
                " ?, ?)";
        PreparedStatement statement=connect.prepareStatement(query_str);
        statement.setInt(1,key);
        statement.setString(2,city);
        statement.setInt(3,pop);
        int result=statement.executeUpdate();
        statement.close();
        connect.close();
        return result;

    }
}
