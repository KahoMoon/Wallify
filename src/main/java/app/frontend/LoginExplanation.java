package app.frontend;

import spotify.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class LoginExplanation {

    JFrame jFrame;
    private JPanel loginExplanationPanel;

    private JPanel explanationPanel;
    private JLabel exampleImage1Label;
    private JLabel exampleImage2Label;
    private JPanel exampleImagePanel;
    private JTextArea exampleImage1Text;
    private JTextArea exampleImage2Text;
    private JButton loginToSpotifyButton;
    private final CardLayout exampleImagePanelCardLayout = (CardLayout) exampleImagePanel.getLayout();

    private final CardLayout explanationPanelCardLayout = (CardLayout) explanationPanel.getLayout();
    ImageIcon exampleImage1 = new ImageIcon("src/main/java/exampleImage1.png");
    ImageIcon exampleImage2 = new ImageIcon("src/main/java/exampleImage2.png");

    public static void main(String[] args) {
        new LoginExplanation();
    }

    public LoginExplanation() {
        jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setContentPane(loginExplanationPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Wallify");
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);

        Dimension exampleImage1Dimension = getScaledDimension(new Dimension(exampleImage1.getIconWidth(), exampleImage1.getIconHeight()), new Dimension(500, 300));
        exampleImage1Label.setIcon(new ImageIcon(exampleImage1.getImage().getScaledInstance((int) exampleImage1Dimension.getWidth(), (int) exampleImage1Dimension.getHeight(), Image.SCALE_SMOOTH)));

        Dimension exampleImage2Dimension = getScaledDimension(new Dimension(exampleImage2.getIconWidth(), exampleImage2.getIconHeight()), new Dimension(500, 300));
        exampleImage2Label.setIcon(new ImageIcon(exampleImage2.getImage().getScaledInstance((int) exampleImage2Dimension.getWidth(), (int) exampleImage2Dimension.getHeight(), Image.SCALE_SMOOTH)));

        exampleImagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                exampleImagePanelCardLayout.next(exampleImagePanel);
                explanationPanelCardLayout.next(explanationPanel);
            }
        });

        loginToSpotifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Desktop desk = Desktop.getDesktop();
                try {
                    URI uri = new URI(AuthController.spotifyLogin());
                    desk.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }

                String responseURL = "http://localhost:3000/?code=AQC1cLeVtoz9P2yWwO40XBu1ZLTZXouKUZA0PtKEzYZSDbdL_7gTpOgp7Ho4nljqKzUFXSLXnAGlNqDnHCWRRKC8u3RJ48Jqrn7gelIRvwRvH94b0K53dxr6ztMnur95zqEW8HExKDOrL-nip4SQdFI806yiYz7w7jUjkm_M3tLWj8VbUrBkGyMbSHNiBrR8dWrIH5zjWIkjdUXHVy28PC4Leuun_az_8lBIa2OOrV-aBh7L6mylT-KBJ1QhPsdDbTCssCma7mumtDFV4x_OVjjxO5FA_Lm4ZmVuLiTy7tCJydoFhuAMsbzfXFEEMKrfHzg7fulFcVCI_aIl";
                Map<String, String> response = null;
                try {
                    response = AuthController.splitQuery(new URI(responseURL).toURL());
                } catch (UnsupportedEncodingException | MalformedURLException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

                jFrame.dispose();
                new PasteURL();
            }
        });
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
