package com.musicbrainz.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Recording {

    private String id;
    private String recordingID;
    private String title;
    private Artist artist;
    private Work work;

    public Recording() {
        this.artist = new Artist();
        this.work = new Work();
    }
}
