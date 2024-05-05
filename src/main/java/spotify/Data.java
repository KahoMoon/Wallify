package spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

import java.io.IOException;

public class Data {
    public static Artist[] getUserTopArtists() {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = AuthController.spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).build();

        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            return artistPaging.getItems();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Something went wrong! \n" + e.getMessage());
        }

        return new Artist[0];
    }
}
