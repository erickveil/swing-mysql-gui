import javax.swing.*;

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
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().main_win);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
