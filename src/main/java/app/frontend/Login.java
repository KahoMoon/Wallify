package app.frontend;

import spotify.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Login {
    private JPanel panel1;
    private JPanel accentColor;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JTextField usernameText;
    private JButton loginButton;

    public static void main(String[] args) {
        new Login();
    }

    public Login() {
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("This is a test");
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Desktop desk = Desktop.getDesktop();
                try {
                    URI uri = new URI(AuthController.spotifyLogin());
                    desk.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
