import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton searchButton;

    public MainWindow(){

        bu_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                char[] pw=pw_mysqlpw.getPassword();
                String str_pw = new String(pw);

                String user=tb_mysqluser.getText();
                DataConnection db =new DataConnection(user,str_pw,"javatest");

                try{
                    db.connectDB();
                }
                catch(Exception e){
                    System.err.println(e.toString());
                    System.err.println(e.getMessage());
                    System.err.println(e.getCause());
                    e.printStackTrace();
                    return;
                }
                System.out.println("Database connected.");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().main_win);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
