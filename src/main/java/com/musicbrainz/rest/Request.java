package com.musicbrainz.rest;

import lombok.Data;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Data
public class Request {

    private URI uri;
    private Set<KeyValuePair> arguments;
    private RequestType type;
    private MediaType acceptedMediaType;

    private Request(URI uri, Set<KeyValuePair> arguments, RequestType type, MediaType acceptedMediaType) {
        this.uri = uri;
        this.arguments = arguments;
        this.type = type;
        this.acceptedMediaType = acceptedMediaType;
    }

    public void addQueryArgument(String key, String value) {
        if(arguments == null) {
            arguments = new HashSet<>();
        }

        arguments.add(new KeyValuePair(key, value));
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {
        private URI uri;
        private Set<KeyValuePair> arguments;
        private RequestType type;
        private MediaType acceptedMediaType;

        private RequestBuilder(){}

        public RequestBuilder to(URI uri) {
            this.uri = uri;
            return this;
        }

        public RequestBuilder to(String string) {
            this.uri = URI.create(string);
            return this;
        }

        public RequestBuilder addQueryArgument(String key, String value) {
            if(arguments == null) {
                arguments = new HashSet<>();
            }

            arguments.add(new KeyValuePair(key, value));

            return this;
        }

        public RequestBuilder get() {
            this.type = RequestType.GET;
            return this;
        }

        public RequestBuilder post() {
            this.type = RequestType.POST;
            return this;
        }

        public RequestBuilder accept(MediaType acceptedMediaType) {
            this.acceptedMediaType = acceptedMediaType;
            return this;
        }

        public Request create() {
            validate();
            return new Request(uri, arguments, type, acceptedMediaType);
        }

        private void validate() {
            requireNonNull(uri, "A URI must be specified in order to have a valid request");
            requireNonNull(type, "A request type must be specified in order to have a valid request");
        }
    }

    private enum RequestType {
        GET, POST
    }

    @Data
    public static class KeyValuePair {
        private final String key;
        private final String value;
    }
}
