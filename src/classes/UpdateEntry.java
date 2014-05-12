package classes;

import classes.DataConnection;
import org.w3c.dom.ranges.RangeException;

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
        try{
            connectDB();
            boolean is_edit_success=edit(city,pop);

            if(!is_edit_success){
                throw new Exception("Failed to edit entry.\n" +
                        "City: "+city+" pop: "+pop);
            }
            else{
                JOptionPane.showMessageDialog(
                        null, "Entry edited successfully.", "Edit",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(
                    null, "Failed to add new entry", "Edit",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println(e.toString());
            System.err.println(e.getCause());
            e.printStackTrace();
            System.err.println("city: "+city+" pop: "+ pop + "return status: " +
                    return_status);
            return;
        }
        System.out.println("Insert complete");
    }

    /**
     * @param city String The lookup key corresponding to the city to edit
     * @param pop Integer The new population to edit
     * @return boolean true if edit was successful. If false,
     * look up this.return_status.
     * @throws SQLException If database cannot be edited
     * @throws Exception if the number of edited entries is other than 1,
     * or if a negative population is attempted
     */
    public boolean edit(String city, int pop) throws SQLException, Exception
    {
        if (city.equals("")){
            return_status="EMPTY_CITY";
            return false;
        }

        if(pop<0){
            throw new Exception(
                    "Population must be set to 0 or greater number");
        }

        String query="UPDATE javatest SET population=? WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setInt(1,pop);
        statement.setString(2, city);
        int result=statement.executeUpdate();
        statement.close();
        connect.close();

        if(result==0){
            throw new Exception("Failed to edit the database");
        }
        if(result!=1){
            throw new Exception("Unexpected number of edits at: "+result);
        }

        return true;
    }

}
