package app.frontend;

import spotify.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class PasteURL {

    private JPanel pasteURLPanel;
    private JTextArea responseURLText;
    private JButton confirmButton;
    private JLabel enterTheURLText;

    public PasteURL() {
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setContentPane(pasteURLPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Wallify");
        jFrame.setSize(700, 400);
        jFrame.setVisible(true);
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String url = responseURLText.getText();
                try {
                    Map<String, String> splitQuery = AuthController.splitQuery(new URI(url).toURL());

                    if (AuthController.checkLogin(splitQuery)) {
                        System.out.println("Worked");
                    } else {
                        JTextArea textArea = new JTextArea("Error code: " + AuthController.getError(splitQuery));
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        textArea.setLineWrap(true);
                        textArea.setWrapStyleWord(true);
                        scrollPane.setPreferredSize(new Dimension(200, 100));
                        JOptionPane.showMessageDialog(jFrame, scrollPane, "An error has occurred", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (UnsupportedEncodingException | MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    JOptionPane.showMessageDialog(jFrame, "The string pasted does not match URL syntax.", "An error has occurred", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
