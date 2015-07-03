package com.kobalt;

public enum MusicBrainzEntity {
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

    private String urlValue;

    MusicBrainzEntity(String urlValue) {
        this.urlValue = urlValue;
    }
}
