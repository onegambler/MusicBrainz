package com.musicbrainz.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "isrc")
public class ISRC {

    @XmlAttribute(name = "id")
    private String id;

    @XmlElement
    private List<Recording> recordingList;

    public ISRC() {
        recordingList = new ArrayList<>();
    }

    public List<Recording> getRecordingList() {
        return recordingList;
    }

    public void setId(String id) {
        this.id = id;
    }
}
