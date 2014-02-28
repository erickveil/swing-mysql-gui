import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by eveil on 2/28/14.
 */
public class UpdateEntry extends DataConnection{

    public String city;
    public int pop;

    public UpdateEntry(String p_user,String p_pw,String p_db,String p_city,
                       String p_pop){

        super(p_user,p_pw,p_db);
        city=p_city;
        pop=Integer.parseInt(p_pop);
    }

    @Override
    public void run()
    {

    }

    public int edit(String city, int pop) throws SQLException
    {
        String query="UPDATE javatest SET population=? WHERE city=?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setInt(1,pop);
        statement.setString(2,city);
        int result=statement.executeUpdate();
        statement.close();
        connect.close();
        return result;
    }

}
