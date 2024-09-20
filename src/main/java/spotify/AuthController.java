package spotify;

import app.frontend.Login;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthController {

    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
        System.out.println(splitQuery(new URL(spotifyRedirectUriResponse)));
    }

    /*A hard coded url for post-Spotify-permission acceptance. Use only for development and testing*/
    private static final String spotifyRedirectUriResponse = "http://localhost:3000/?code=AQC7xnSyoPKVq5fhTVCEdSxIM2C8zC0PC7DD7paQTHbJ7n9DiLMn7ylM83bO4KRiPQabf_UVZsiL3oX3SGBYRqvkD0bc4Ghwy0JeyQmufgArVtYGa37vkJSn72TcpZuDzWybvvVjmoyeQ4thbh2dYqjrDUvW8h6Efwv6UyX7uTGMAYruwssLDIRxrCl23IMCwqO47ZpJnb80sp4-nM-VpcubTMiJNAS4yRvYubg5ZYU0_Ax9qVjY_zhmpo8pVLSkpGaVC8_I2pqGUWlvdoBpHHxs1ocA7AqS2AYZZvLtfmMLxUbBqYoQiyVTA6nmwSDnyBYp-9DiRfYTHahs";

    /*URL that the user is redirected to after accepting the permissions (need to change to custom URL protocol)*/
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000");
    private static String code = "";

    //necessary for Spotify API access
    static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(Keys.CLIENT_ID.getId())
            .setClientSecret(Keys.CLIENT_SECRET.getSecret())
            .setRedirectUri(redirectUri)
            .build();

    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
            .build();

    /*Generates the url for the respective Spotify permissions*/
    public static String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("playlist-read-private, playlist-read-collaborative, user-follow-read, user-top-read, user-read-recently-played, user-library-read")
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    /*Parses the given URL and returns the key value pairs
    * @param url the url that will be parsed
    * @return the key value pairs from the url*/
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

    /*Checks whether the parsed Spotify response from the login attempt is successful
    * @param response the parsed response
    * @return the login status*/
    public static Boolean checkLogin(Map<String, String> response) {
        if (response.get("code") != null) {
            return true;
        }
        return false;
    }

    /*Returns the error code from the unsuccessful login attempted
    * @param response the parsed response*/
    public static String getError(Map<String, String> response) {
        return response.get("error");
    }

    /*Requests the user's Spotify access token
    * @return the user access token in the case of a successful request*/
    public static String getSpotifyUserCode(String userCode) {
        code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn() + " seconds");
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return spotifyApi.getAccessToken();
    }
}