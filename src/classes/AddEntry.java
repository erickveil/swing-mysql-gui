package classes;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by eveil on 2/25/14.
 */
public class AddEntry extends DataConnection {

    private String new_city;
    private int new_population;
    private CitySearch entry_checker;

    public AddEntry(String p_city, String p_pop, String p_user,String p_pw,
                    String p_db)
    {
        super(p_user,p_pw,p_db);
        new_city=p_city;
        new_population=Integer.parseInt(p_pop);
        entry_checker=new CitySearch(p_city,p_user,p_pw,p_db,null);
    }

    @Override
    public void run()
    {
        int key=(int)(Math.random()*100);
        try{
            connectDB();
            StringBuilder result=new StringBuilder();

            if(!insertEntry(key,new_city,new_population)){
                JOptionPane.showMessageDialog(
                        null, "Failed to add new entry", "Insert",
                        JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(
                        null, "Entry added successfully.", "Insert",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(
                    null, "Failed to add new entry", "Insert",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println(e.toString());
            System.err.println(e.getCause());
            e.printStackTrace();
            System.err.println("key: "+key+" city: "+new_city+" pop: "+
                    new_population);
            return;
        }
        System.out.println("Insert complete");
    }

    /**
     * Adds a new entry to the database
     *
     * @param key int The database index key
     * @param city String The city name
     * @param pop int The population of the city.
     * @return boolean True if the insert was successful. If false,
     * check this.result.
     * @throws java.sql.SQLException Usually because the database is not
     * writable
     * @throws Exception if an unexpected number of entries have been written
     * (should always be 1).
     */
    public boolean insertEntry(
            int key, String city, int pop)
            throws SQLException, Exception
    {
        if(isEntryExists(city)){
            return_status="ALREADY_EXISTS";
            return false;
        }

        Integer query_result=0;
        String query_str="INSERT INTO javatest (no,city,population) VALUES(?," +
                " ?, ?)";
        try{
            PreparedStatement statement=connect.prepareStatement(query_str);
            statement.setInt(1,key);
            statement.setString(2,city);
            statement.setInt(3,pop);
            query_result=statement.executeUpdate();
            statement.close();
        }
        catch(NullPointerException ex){
            System.err.println("NullPointerException: make sure you have " +
                    "connected to a database and set the connection to " +
                    "classes.AddEntry.connect before calling this method.");
            System.err.println(ex.toString());
            System.err.println(ex.getCause());
            ex.printStackTrace();
            System.err.println("key: "+key+" city: "+new_city+" pop: "+
                    new_population);
        }

        connect.close();
        if(query_result==1){
            return_status="GOOD";
            return true;
        }

        throw new Exception("Incorrect number of inserts have been made: "
                +query_result );
    }

    /**
     * Checks the city to see if that entry has already been inserted into
     * the database
     *
     * @param city string The city to search for
     * @return boolean true if exists, false if not
     * @throws Exception
     */
    public boolean isEntryExists(String city) throws Exception
    {
        entry_checker.connectDB();
        return (entry_checker.searchPopulationByCity(city)!=null);
    }
}
