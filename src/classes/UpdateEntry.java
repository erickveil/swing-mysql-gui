package classes;

import classes.DataConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by eveil on 2/28/14.
 */
public class UpdateEntry extends DataConnection {

    public String city;
    public Integer pop;

    public UpdateEntry(String p_user,String p_pw,String p_db,String p_city,
                       Integer p_pop){

        super(p_user,p_pw,p_db);
        city=p_city;
        pop=p_pop;
    }

    @Override
    public void run()
    {
        JFrame frame=new JFrame("Edit");
        try{
            connectDB();

            int number_of_updates=edit(city,pop);
            if(number_of_updates!=1){
                JOptionPane.showMessageDialog(frame,"Failed to edit entry" +
                        ".\nCity: "+city+" pop: "+pop+" results: "+
                        number_of_updates);
            }
            else{
                JOptionPane.showMessageDialog(frame,
                        "Entry edited sucessfully.");
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(frame,"Failed to add new entry");
            System.err.println(e.toString());
            System.err.println(e.getCause());
            e.printStackTrace();
            System.err.println("city: "+city+" pop: "+ pop);
            return;
        }
        System.out.println("Insert complete");
    }

    public int edit(String city, int pop) throws SQLException
    {
        String query="UPDATE javatest SET population=? WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setInt(1,pop);
        statement.setString(2, city);
        int result=statement.executeUpdate();
        statement.close();
        connect.close();
        return result;
    }

}
