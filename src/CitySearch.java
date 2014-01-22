import javax.swing.*;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eveil on 1/22/14.
 */
public class CitySearch extends DataConnection{

    private JTextField search_out=null;
    private String query_key=null;
    public CitySearch(String p_user, String p_password, String p_database, String p_query_key, JTextField p_search_out) {
        super(p_user, p_password, p_database);
        search_out=p_search_out;
        query_key=p_query_key;
    }

    public void run()
    {
        try{
            connectDB();
            searchByCity(query_key);
        }
        catch(Exception e){
            System.err.println(e.toString());
            return;
        }
        System.out.println("Query complete");
    }

    public void searchByCity(String city) throws SQLException
    {
        int population=0;
        String query_str = "SELECT * FROM javatest WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query_str);
        statement.setString(1,city);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            population=result.getInt("population");
        }

        search_out.setText(((Integer)population).toString());
        result.close();
        statement.close();
        connect.close();
    }
}
