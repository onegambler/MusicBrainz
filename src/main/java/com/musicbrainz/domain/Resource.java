package com.musicbrainz.domain;

public enum Resource {
    AREA("area"),
    ARTIST("artist"),
    EVENT("event"),
    INSTRUMENT("instrument"),
    LABEL("label"),
    RECORDING("recording"),
    RELEASE("release"),
    RELEASE_GROUP("release-group"),
    SERIES("series"),
    WORK("work"),
    URL("url");

    private String uriValue;

    Resource(String uriValue) {
        this.uriValue = uriValue;
    }

    public String getUriValue() {
        return uriValue;
    }
}
