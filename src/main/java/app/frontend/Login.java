package app.frontend;

import spotify.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Login {
    private JPanel panel1;
    private JPanel accentColor;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JTextField usernameText;
    private JButton loginButton;

    public static void main(String[] args) {
        Desktop desk = Desktop.getDesktop();
        try {
            URI uri = new URI(AuthController.spotifyLogin());
            desk.browse(uri);
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public Login() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usernameText = usernameLabel.getText();
                String password = Arrays.toString(passwordText.getPassword());

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