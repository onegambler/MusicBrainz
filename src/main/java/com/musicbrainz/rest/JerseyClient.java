package com.musicbrainz.rest;

import com.musicbrainz.io.LoggerSingleton;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import java.util.logging.Logger;

public class JerseyClient implements RESTClient {

    private static final int MAX_RETRIES = 3;
    private final Client client;
    private final Logger logger = LoggerSingleton.getLogger();

    public JerseyClient(Client client) {
        this.client = client;
    }

    public String get(String uri) {
        WebResource webResource = client.resource(uri);
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return webResource.get(String.class);
            } catch (UniformInterfaceException e) {
                if (e.getResponse().getStatus() == 404) {
                    throw e;
                } else {
                    logger.warning("Handled exception: " + e.getMessage() + ". Trying again!");
                    retries++;
                }
            }
        }

        throw new RuntimeException("Impossible to process ISRC");
    }
}
