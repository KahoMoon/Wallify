package app.backend;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ImageManipulation {

    public static void main(String[] args) throws IOException, URISyntaxException {
        File outputFile = new File("testImage.png");
        String[] urls = new String[]{"https://i.scdn.co/image/ab67616d0000b2731c76e29153f29cc1e1b2b434", "https://i.scdn.co/image/ab67616d0000b27353b879d4b7a804e3c52fcd8b", "https://i.scdn.co/image/ab67616d0000b2730cef6fbea2d695cedc33b2db", "https://i.scdn.co/image/ab67616d0000b273df55e326ed144ab4f5cecf95"};
        BufferedImage testImage = stitchImages(urls, 1, 4);
        ImageIO.write(testImage, "png", outputFile);
    }

    private static final int SPOTIFYIMAGEWIDTHHEIGHTPIXELS = 640;

    public static Image getImageFromURL(String url) throws URISyntaxException, IOException {
            URI imageURl = new URI(url);
            return ImageIO.read(imageURl.toURL());
    }

    public static Image[] getImagesFromURls(String[] urls) throws URISyntaxException, IOException {
        Image[] images = new Image[urls.length];
        for (int i = 0; i < urls.length; i++) {
            images[i] = getImageFromURL(urls[i]);
        }

        return images;
    }

    public static BufferedImage stitchImages(String[] urls, int finalNumOfRowCol) throws URISyntaxException, IOException {
        BufferedImage finalImage = new BufferedImage(SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRowCol, SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRowCol, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = finalImage.createGraphics();
        Image[] images = getImagesFromURls(urls);

        int x = 0;
        int y = 0;
        int imageNum = 0;
        while (imageNum <= finalNumOfRowCol * finalNumOfRowCol) {

            //check if x coordinate is at end of row
            if (x >= SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRowCol) {
                x = 0;
                y += SPOTIFYIMAGEWIDTHHEIGHTPIXELS;

                //check if y coordinate finished with bottom right
                if (y >= SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRowCol) {
                    return finalImage;
                }
            }

            graphics.drawImage(images[imageNum], x, y, null);
            x += SPOTIFYIMAGEWIDTHHEIGHTPIXELS;
            imageNum++;
        }

        return finalImage;
    }

    public static BufferedImage stitchImages(String[] urls, int finalNumOfRows, int finalNumOfCols) throws URISyntaxException, IOException {
        BufferedImage finalImage = new BufferedImage(SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfCols, SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRows, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = finalImage.createGraphics();
        Image[] images = getImagesFromURls(urls);

        int x = 0;
        int y = 0;
        int imageNum = 0;
        while (imageNum <= finalNumOfRows * finalNumOfCols) {

            //check if x coordinate is at end of row
            if (x >= SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfCols) {
                x = 0;
                y += SPOTIFYIMAGEWIDTHHEIGHTPIXELS;

                //check if y coordinate finished with bottom right
                if (y >= SPOTIFYIMAGEWIDTHHEIGHTPIXELS * finalNumOfRows) {
                    return finalImage;
                }
            }

            graphics.drawImage(images[imageNum], x, y, null);
            x += SPOTIFYIMAGEWIDTHHEIGHTPIXELS;
            imageNum++;
        }

        return finalImage;
    }



}
