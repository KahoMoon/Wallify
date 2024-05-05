package app.frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel panel1;
    private JPanel accentColor;
    private JButton getStartedButton;

    public static void main(String[] args) {
        new Login();
    }

    public Login() {
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setContentPane(panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Wallify");
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);

        getStartedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginExplanation();
            }
        });
    }

}
