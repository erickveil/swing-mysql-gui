package classes;

import javax.swing.*;
import java.sql.PreparedStatement;

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
        JFrame frame=new JFrame("Insert");
        int key=(int)(Math.random()*100);
        try{
            connectDB();

            StringBuilder result=new StringBuilder();
            if(insertEntry(key,new_city,new_population,result)!=1){
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
     * @param result StringBuilder Output value. Check if returns null. If
     *               set to ALREADY_EXISTS, then the entry is not added
     *               because the city already exists in the database. Will be
     *               set to GOOD if the entry was inserted.
     * @return Integer|null Will be 1 if the entry is inserted, or null if not.
     * @throws Exception Usually because the database is not writable or
     * accessible.
     *
     * Todo: Is null return the correct abortive exit here? Rewrite using
     * exceptions and see. If exceptions are not the way,
     * rewind to this commit.
     */
    public Integer insertEntry(
            int key, String city, int pop, StringBuilder result )
            throws Exception
    {
        if(isEntryExists(city)){
            result.append("ALREADY_EXISTS");
            return null;
        }

        Integer query_result=null;
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
        result.append("GOOD");
        return query_result;
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
