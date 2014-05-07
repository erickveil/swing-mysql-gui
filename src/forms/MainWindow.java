package forms;

import classes.AddEntry;
import classes.CitySearch;
import classes.DataConnection;
import classes.UpdateEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
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
    private JButton searchButton;

    private DataConnection db;
    private Thread db_thread;

    public JTextField access_result_search_pop =this.tb_result;
    public JTextField access_result_edit_pop=this.tb_edit_pop;
    public JTextField access_result_edit_city=this.tb_edit_city;
    public Lock threadlock = new ReentrantLock();
    public MainWindow self=this;

    public MainWindow(){

        try{
            bu_connect.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent actionEvent) {

                     char[] pw=pw_mysqlpw.getPassword();
                     String str_pw = new String(pw);
                     String user=tb_mysqluser.getText();
                     db =new DataConnection(user,str_pw,"javatest");
                     db_thread=new Thread(db);
                     db_thread.start();

                 }
            });

            bu_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    char[] pw=pw_mysqlpw.getPassword();
                    String str_pw = new String(pw);
                    String user=tb_mysqluser.getText();
                    String search=tb_select_city.getText();
                    db =new CitySearch(search,user,str_pw,"javatest", self);
                    db_thread=new Thread(db);
                    db_thread.start();

                }
            });

            bu_insert.addActionListener(new ActionListener() {
                JFrame frame = new JFrame("Insert");
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(tb_insert.getText().equals("") ||
                            tb_insert_pop.getText().equals("")){
                        JOptionPane.showMessageDialog(frame,
                                "City name or population is missing.");
                        return;
                    }

                    char[] pw=pw_mysqlpw.getPassword();
                    String str_pw = new String(pw);
                    String user=tb_mysqluser.getText();
                    db = new AddEntry(tb_insert.getText(),
                            tb_insert_pop.getText(),user,str_pw,"javatest");
                    db_thread=new Thread(db);
                    db_thread.start();
                }
            });

            bu_edit.addActionListener(new ActionListener() {
                JFrame frame = new JFrame("Edit");
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    if(tb_edit_city.getText().equals("")||
                            tb_edit_pop.getText().equals("")){
                        JOptionPane.showMessageDialog(frame,
                                "Edit City or population Name missing. " +
                                        "Search for existing city to edit " +
                                        "first.");
                        return;
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
                        return;
                    }

                    db = new UpdateEntry(user,str_pw,"javatest",city,pop);
                    db_thread=new Thread(db);
                    db_thread.start();
                }
            });
        }
        catch(IllegalThreadStateException ex) {
            System.err.println("Caught: "+ex.toString());
            System.err.println("Thread state: "+db_thread.getState());
            System.err.println("Ignoring command.");
        }
        catch(Exception ex){
            System.err.println("Caught: "+ex.toString());
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().main_win);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}