package com.musicbrainz.rest;

import com.musicbrainz.util.ConnectionBouncer;

import javax.ws.rs.core.UriBuilder;
import java.util.function.Supplier;

public class MusicBrainzClient implements RESTClient{

    private final static String MUSIC_BRAINZ_END_POINT = "";

    private final RESTClient client;
    private final UriBuilder uriBuilder = UriBuilder.fromUri(MUSIC_BRAINZ_END_POINT);

    public MusicBrainzClient(RESTClient client) {
        this.client = client;
    }

    @Override
    public String get(String uri) {
        return invokeWithDelay(() -> client.get(uri));
    }

    private String invokeWithDelay(Supplier<String> callFunction) {
        ConnectionBouncer.getInstance().waitTurn();
        return callFunction.get();
    }
}
