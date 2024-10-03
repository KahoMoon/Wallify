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

    public static Artist[] getUserTopArtists() {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).build();

        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            return artistPaging.getItems();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Something went wrong! \n" + e.getMessage());
        }

        return new Artist[0];
    }

    public static Track[] getUsersTopTracks() {

        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().limit(10).offset(0).time_range("medium_term")
                .build();

        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            return trackPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new Track[0];
    }

    public static PlaylistSimplified[] getUsersPlaylists() {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User currUser = null;
        try {
            currUser = getCurrentUsersProfileRequest.execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            return new PlaylistSimplified[0];
        }

        GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi.getListOfUsersPlaylists(currUser.getId()).build();
        try {
            getListOfUsersPlaylistsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return new PlaylistSimplified[0];
        }

        final GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest1 = spotifyApi.getListOfUsersPlaylists(currUser.getId()).build();
        Paging<PlaylistSimplified> playlistSimplifiedPaging = null;
        try {
            playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }

        return playlistSimplifiedPaging.getItems();
    }

    public static PlaylistTrack[] getPlaylistItems(String playlistID) {
        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistID).build();
        try {
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            return playlistTrackPaging.getItems();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            return new PlaylistTrack[0];
        }}
}
