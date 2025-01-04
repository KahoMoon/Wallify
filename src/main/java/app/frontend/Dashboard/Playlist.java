package app.frontend.Dashboard;

import app.backend.ImageManipulation;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Image;
import spotify.Data;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Playlist extends JPanel{

    public static void main(String[] args) throws IOException, ParseException, SpotifyWebApiException {
        new Playlist(Data.getPlaylistCover("4Z8W4fKeB5YxbusRsdQVPb")[0], "playlistName", "playlistOwnerName");
    }

    JLabel imageJlabel;
    JLabel playlistName;
    JLabel playlistOwnerName;

    public Playlist(Image image, String playlistName, String playlistOwnerName) {

        try {
            this.imageJlabel = new JLabel(new ImageIcon(ImageManipulation.getImageFromURL(image.getUrl())));
        } catch (URISyntaxException e) {
            //this.image = placeholder
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.playlistName = new JLabel(playlistName);
        this.playlistOwnerName = new JLabel(playlistOwnerName);

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        add(imageJlabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(this.playlistName, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(this.playlistOwnerName, gridBagConstraints);
    }
}
