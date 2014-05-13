package classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by eveil on 5/13/2014.
 */
public class DeleteEntry extends DataConnection{

    private String city_to_delete;

    public DeleteEntry(String p_user, String p_password, String p_database,
                       String city_name) {

        super(p_user, p_password, p_database);
        city_to_delete=city_name;
    }

    @Override
    public void run()
    {
        try{
            deleteByCity(city_to_delete);
        }
        catch(Exception e){
            reportFatalException(e,"Delete Entry");
        }
        System.out.println("Delete Successful");
    }

    public boolean deleteByCity(String city_name) throws SQLException, Exception
    {
        if(city_name.equals("")){
            return_status="EMPTY_CITY";
            return false;
        }

        String query="DELETE from javatest WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setString(1, city_name);
        int number_of_deletes=statement.executeUpdate();
        statement.close();
        connect.close();

        if(number_of_deletes==0){
            throw new Exception("No entries were deleted.");
        }

        if(number_of_deletes!=1){
            throw new Exception("Unexpected number of deletes= "+
            number_of_deletes+" (should be 1)");
        }

        return true;


    }
}
