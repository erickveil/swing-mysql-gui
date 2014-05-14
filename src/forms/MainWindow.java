package forms;

import classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by eveil on 1/14/14.
 */
public class MainWindow {
    private JPasswordField pw_mysqlpw;
    private JTextField tb_select_city;
    private JTextField tb_result;
    private JPanel main_win;
    private JButton bu_search;
    private JButton bu_insert;
    private JTextField tb_insert;
    private JTextField tb_mysqluser;
    private JButton bu_connect;
    private JTextField tb_insert_pop;
    private JPanel p_edit;
    private JTextField tb_edit_city;
    private JTextField tb_edit_pop;
    private JButton bu_edit;
    private JPanel p_delete;
    private JLabel lbl_del;
    private JTextField tb_delete;
    private JButton bu_delete;
    private JButton searchButton;

    private DataConnection db;
    private Thread db_thread;
    private String pw;
    private String user;

    /**
     * Holds the last return status set for testing the reason for a
     * soft method failure
     * */
    public String return_status;

    public JTextField access_result_search_pop =this.tb_result;
    public JTextField access_result_edit_pop=this.tb_edit_pop;
    public JTextField access_result_edit_city=this.tb_edit_city;
    public JTextField access_result_delete=this.tb_delete;
    public Lock threadlock = new ReentrantLock();
    public MainWindow self=this;

    /**
     * Primary GUI
     */
    public MainWindow(){
        try{
            bu_connect.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent actionEvent) {
                     connectAction();
                 }
            });

            bu_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    searchAction();
                }
            });

            bu_insert.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    insertAction();
                }
            });

            bu_edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updateAction();
                }
            });

            bu_delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    deleteCity();
                }
            });
        }
        catch(IllegalThreadStateException ex) {
            System.err.println("Caught: "+ex.toString());
            System.err.println("Thread state: "+db_thread.getState());
            System.err.println("Ignoring command.");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    /**
     * todo: delete entry error box shows up empty
     * todo: successull commands should notify the user, not the command line.
     */
    private void deleteCity()
    {
        char[] pw=pw_mysqlpw.getPassword();
        String str_pw = new String(pw);
        String user=tb_mysqluser.getText();
        String city_to_delete=tb_delete.getText();
        db=new DeleteEntry(user,str_pw,"javatest",city_to_delete);
        db_thread=new Thread(db);
        db_thread.start();
    }

    /**
     * Tests the connection to the database.
     * Exceptions are handled at the top level of the thread.
     *
     * No soft failures, this method returns void or throws on its own thread
     * during an error.
     */
    private void connectAction()
    {
        char[] pw=pw_mysqlpw.getPassword();
        String str_pw = new String(pw);
        String user=tb_mysqluser.getText();
        db =new DataConnection(user,str_pw,"javatest");
        db_thread=new Thread(db);
        db_thread.start();
    }

    private boolean searchAction()
    {
        readCredentials();
        String search=tb_select_city.getText();
        db =new CitySearch(search,user,pw,"javatest", self);
        db_thread=new Thread(db);
        db_thread.start();
        return true;
    }

    private boolean insertAction()
    {
        JFrame frame = new JFrame("Insert");
        if(tb_insert.getText().equals("") ||
            tb_insert_pop.getText().equals("")){
            JOptionPane.showMessageDialog(frame,
                "City name or population is missing.");
            return false;
        }

        char[] pw=pw_mysqlpw.getPassword();
        String str_pw = new String(pw);
        String user=tb_mysqluser.getText();
        db = new AddEntry(tb_insert.getText(),
        tb_insert_pop.getText(),user,str_pw,"javatest");
        db_thread=new Thread(db);
        db_thread.start();
        return true;
    }

    private boolean updateAction()
    {
        JFrame frame = new JFrame("Edit");
        if(tb_edit_city.getText().equals("")||
            tb_edit_pop.getText().equals("")){
            JOptionPane.showMessageDialog(frame,
                    "Edit City or population Name missing. " +
                            "Search for existing city to edit " +
                            "first.");
            return false;
        }

        char[] pw=pw_mysqlpw.getPassword();
        String str_pw = new String(pw);
        String user = tb_mysqluser.getText();
        String pop_str=tb_edit_pop.getText();
        String city=tb_edit_city.getText();
        Integer pop=-1;

        try{
            pop=Integer.parseInt(pop_str);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(frame,
                "The population entered must be an integer.");
            return false;
        }

        db = new UpdateEntry(user,str_pw,"javatest",city,pop);
        db_thread=new Thread(db);
        db_thread.start();
        return true;
    }

    private boolean deleteAction()
    {
        return true;
    }

    /**
     * Repeated use method to update user name and password with the
     * currently entered values each time an event is fired.
     * There is no validation on the sourced input
     *
     * Failures are fatal exceptions.
     */
    private void readCredentials()
    {
        char[] raw_pw=pw_mysqlpw.getPassword();
        pw = new String(raw_pw);
        user=tb_mysqluser.getText();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().main_win);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
