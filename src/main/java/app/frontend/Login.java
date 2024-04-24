package app.frontend;

import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import spotify.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Login extends JFrame{
    private JPanel panel1;
    private JPanel accentColor;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JTextField usernameText;
    private JButton loginButton;

    public static void main(String[] args) {
        Login login = new Login();
    }

    public Login() {
        super("Testing the login");
        this.getContentPane().add(panel1, BorderLayout.CENTER);
        this.setEnabled(true);
        this.setVisible(true);
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
