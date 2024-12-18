package app.frontend;

import javax.swing.*;

public class Dashboard {
    private JPanel panel1;
    private JPanel topMenu;
    private JTextField searchBar;
    private JPanel leftMenu;
    private JPanel centerMenu;
    private JPanel profilePicture;

    public static void main(String[] args) {
        new Dashboard();
    }

    public Dashboard() {
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setContentPane(panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Wallify");
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);
    }
}
