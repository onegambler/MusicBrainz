package com.musicbrainz.rest;

import com.musicbrainz.domain.Resource;
import com.musicbrainz.util.ConnectionBouncer;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class MusicBrainzClient {

    private final static String MUSIC_BRAINZ_END_POINT = "http://musicbrainz.org/ws/2/";

    private final RESTClient client;

    public MusicBrainzClient(RESTClient client) {
        this.client = client;
    }

    private String invokeWithDelay(Supplier<String> callFunction) {
        ConnectionBouncer.getInstance().waitTurn();
        return callFunction.get();
    }

    public String lookup(Resource resource, String mbID, String... included) {
        UriBuilder uriBuilder = UriBuilder.fromUri(MUSIC_BRAINZ_END_POINT)
                .path(resource.getUriValue())
                .path(mbID);

        addIncludeQueryParamIfNotNull(uriBuilder, included);

        return get(uriBuilder.build());
    }

    public String browse(Resource mainResource, Resource queryResource, String id, String limit, String offset, String... included) {
        requireNonNull(mainResource, "Main resource cannot be null");
        requireNonNull(queryResource, "Query resource cannot be null");
        UriBuilder uriBuilder = UriBuilder
                .fromUri(MUSIC_BRAINZ_END_POINT)
                .path(mainResource.getUriValue())
                .queryParam(queryResource.getUriValue(), id);

        addQueryParamIfNotNull(uriBuilder, LIMIT_PARAM_NAME, limit);
        addQueryParamIfNotNull(uriBuilder, OFFSET_PARAM_NAME, offset);
        addIncludeQueryParamIfNotNull(uriBuilder, included);

        return get(uriBuilder.build());
    }

    public String browse(Resource mainResource, Resource queryResource, String id, String... included) {
        return browse(mainResource, queryResource, id, null, null, included);
    }

    /**
     * search:   /<ENTITY>?query=<QUERY>&limit=<LIMIT>&offset=<OFFSET>
     *
     * @return a String
     */
    public String search(Resource resource, String query, String limit, String offset) {
        requireNonNull(resource, "Resource cannot be null");
        requireNonNull(query, "Query cannot be null");
        UriBuilder uriBuilder = UriBuilder.fromUri(MUSIC_BRAINZ_END_POINT)
                .path(resource.getUriValue());
        addQueryParamIfNotNull(uriBuilder, LIMIT_PARAM_NAME, limit);
        addQueryParamIfNotNull(uriBuilder, OFFSET_PARAM_NAME, offset);

        uriBuilder.queryParam("query", query);

        return get(uriBuilder.build());
    }

    public String search(Resource resource, String query) {
        return search(resource, query, null, null);
    }

    private String get(URI uri) {
        Request request = Request.builder().get()
                .to(uri)
                .accept(MediaType.APPLICATION_XML_TYPE)
                .create();
        return invokeWithDelay(() -> this.client.get(request));
    }

    private UriBuilder addQueryParamIfNotNull(UriBuilder builder, String queryParamName, String queryParamValue) {
        if (nonNull(queryParamValue)) {
            builder.queryParam(queryParamName, queryParamValue);
        }
        return builder;
    }

    private UriBuilder addIncludeQueryParamIfNotNull(UriBuilder builder, String... included) {
        if (nonNull(included) && included.length > 0) {
            builder.queryParam("inc", String.join("+", included));
        }
        return builder;
    }

    public static final String LIMIT_PARAM_NAME = "limit";
    public static final String OFFSET_PARAM_NAME = "offset";
}
