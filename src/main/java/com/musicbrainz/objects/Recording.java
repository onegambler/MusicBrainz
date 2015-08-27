package com.musicbrainz.objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
public class Recording {

    private String id;
    private String recordingID;
    private String title;
    private Artist artist;
    private Work work;
}
