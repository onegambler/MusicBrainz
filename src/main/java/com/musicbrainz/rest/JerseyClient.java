package com.musicbrainz.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient implements RESTClient {

    private final Client client;

    public JerseyClient(Client client) {
        this.client = client;
    }

    public String get(Request request) {
        return prepareWebResourceBuilder(request).get(String.class);
    }

    private WebResource.Builder prepareWebResourceBuilder(Request request) {
        return client.resource(request.getUri()).accept(request.getAcceptedMediaType());
    }
}
