package classes;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import forms.MainWindow;

/**
 *
 * Created by eveil on 1/22/14.
 */
public class CitySearch extends DataConnection {

    private String query_key=null;
    private MainWindow parent=null;

    public CitySearch(
            String p_query_key,
            String p_user, String p_password, String p_database,
            MainWindow p_parent)
    {
        super(p_user, p_password, p_database);
        parent=p_parent;
        query_key=p_query_key;
    }

    /**
     * There appears to be no way around it. If I want to easily lock the
     * main thread to assign it the queried value, and have the GUI update
     * at the point that value is available (without waiting on the GUI
     * thread), I need to provide access to the text box here in this class.
     *
     * The MainWindow is given a public accessor for the text box,
     * and at least I lock it before writing.
     */
    @Override
    public void run()
    {
        try{
            connectDB();

            Integer population = searchPopulationByCity(query_key);

            if(population==null){
                JFrame frame = new JFrame("Insert");
                JOptionPane.showMessageDialog(frame,"The city was not found " +
                        "in the database.");
                return;
            }

            parent.threadlock.lock();
            parent.access_result_search_pop.setText(population.toString());
            parent.access_result_edit_city.setText(query_key);
            parent.access_result_edit_pop.setText(population.toString());
            parent.threadlock.unlock();
        }
        catch(Exception e){
            reportFatalException(e);
            return;
        }
        System.out.println("Query complete");
    }

    /**
     * Obtains the population for the requested city from the database
     *
     * @param city String the city to search by
     * @return Integer the population if the queried city. null if result not
     * found. Check this.return_status if null
     * @throws java.sql.SQLException Fatal if the database cannot be accessed
     * . Make sure the connection member is not closed before calling and
     * that the database is setup correctly and running.
     */
    public Integer searchPopulationByCity(String city) throws SQLException
    {
        if(city.equals("")) {
            return_status="EMPTY_SEARCH_STRING";
            return null;
        }

        Integer population;
        String query_str = "SELECT * FROM javatest WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query_str);
        statement.setString(1,city);
        ResultSet result = statement.executeQuery();

        if(!result.next()){
            return_status="NO_ENTRY_FOUND";
            return null;
        }

        do{
            population=result.getInt("population");
        }while (result.next());

        result.close();
        statement.close();
        connect.close();
        return_status="GOOD";

        return population;
    }
}
