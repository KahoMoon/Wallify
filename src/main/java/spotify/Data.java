package spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;

import static spotify.AuthController.spotifyApi;

public class Data {

    public static Artist[] getUserTopArtists() throws IOException, ParseException, SpotifyWebApiException {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).build();

        final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
        return artistPaging.getItems();
    }

    public static Track[] getUsersTopTracks() throws IOException, ParseException, SpotifyWebApiException {

        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().limit(10).offset(0).time_range("medium_term")
                .build();

        final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

        return trackPaging.getItems();
    }

    public static PlaylistSimplified[] getUsersPlaylists() throws IOException, ParseException, SpotifyWebApiException {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User currUser;
        currUser = getCurrentUsersProfileRequest.execute();


        GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi.getListOfUsersPlaylists(currUser.getId()).build();
        getListOfUsersPlaylistsRequest.execute();

        Paging<PlaylistSimplified> playlistSimplifiedPaging;
        playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();

        return playlistSimplifiedPaging.getItems();
    }

    public static PlaylistTrack[] getPlaylistItems(String playlistID) throws IOException, ParseException, SpotifyWebApiException {
        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistID).build();
        Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
        return playlistTrackPaging.getItems();
    }
}
