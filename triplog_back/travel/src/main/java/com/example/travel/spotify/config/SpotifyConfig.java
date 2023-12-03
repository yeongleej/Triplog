package com.example.travel.spotify.config;


import net.minidev.json.parser.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

public class SpotifyConfig {
    private static SpotifyConfig instance = new SpotifyConfig();
    private SpotifyConfig() {}
    private static final String CLIENT_ID = "85508b8ab9fb4deeb4648af3f84ace5e";
    private static final String CLIENT_SECRET = "a03f8796b184427bb53c36e295443255";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();
    public static SpotifyConfig getInstance() {
        return instance;
    }


    public String accessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "error";

        }
    }
}
