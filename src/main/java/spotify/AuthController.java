package spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthController {
    public static void main(String[] args) throws IOException, URISyntaxException {

//        Desktop desk = Desktop.getDesktop();
//        try {
//            URI uri = new URI(AuthController.spotifyLogin());
//            desk.browse(uri);
//        } catch (URISyntaxException | IOException ex) {
//            throw new RuntimeException(ex);
//        }

//        String responseURL = "http://localhost:3000/?code=AQC1cLeVtoz9P2yWwO40XBu1ZLTZXouKUZA0PtKEzYZSDbdL_7gTpOgp7Ho4nljqKzUFXSLXnAGlNqDnHCWRRKC8u3RJ48Jqrn7gelIRvwRvH94b0K53dxr6ztMnur95zqEW8HExKDOrL-nip4SQdFI806yiYz7w7jUjkm_M3tLWj8VbUrBkGyMbSHNiBrR8dWrIH5zjWIkjdUXHVy28PC4Leuun_az_8lBIa2OOrV-aBh7L6mylT-KBJ1QhPsdDbTCssCma7mumtDFV4x_OVjjxO5FA_Lm4ZmVuLiTy7tCJydoFhuAMsbzfXFEEMKrfHzg7fulFcVCI_aIl";
//        Map<String, String> response = splitQuery(new URI(responseURL).toURL());
//
//        System.out.println(getSpotifyUserCode(response.get("code")));

        System.out.println(Arrays.toString(Data.getUserTopArtists()));

    }

    private static final String spotifyRedirectUriResponse = "http://localhost:3000/?code=AQC7xnSyoPKVq5fhTVCEdSxIM2C8zC0PC7DD7paQTHbJ7n9DiLMn7ylM83bO4KRiPQabf_UVZsiL3oX3SGBYRqvkD0bc4Ghwy0JeyQmufgArVtYGa37vkJSn72TcpZuDzWybvvVjmoyeQ4thbh2dYqjrDUvW8h6Efwv6UyX7uTGMAYruwssLDIRxrCl23IMCwqO47ZpJnb80sp4-nM-VpcubTMiJNAS4yRvYubg5ZYU0_Ax9qVjY_zhmpo8pVLSkpGaVC8_I2pqGUWlvdoBpHHxs1ocA7AqS2AYZZvLtfmMLxUbBqYoQiyVTA6nmwSDnyBYp-9DiRfYTHahs";

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000");
    private static String code = "";

    static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(Keys.CLIENT_ID.getId())
            .setClientSecret(Keys.CLIENT_SECRET.getSecret())
            .setRedirectUri(redirectUri)
            .build();

    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
            .build();

    public static String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("playlist-read-private, playlist-read-collaborative, user-follow-read, user-top-read, user-read-recently-played, user-library-read")
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static String getSpotifyUserCode(String userCode) {
        code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            //throw new RuntimeException(e);
            System.out.println("Error: " + e.getMessage());
        }

        return spotifyApi.getAccessToken();
    }
}