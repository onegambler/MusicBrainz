package com.musicbrainz.objects;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "isrc")
public class ISRC {

    @Getter @Setter
    @XmlAttribute(name = "id")
    private String id;

    @Getter @Setter
    @XmlElement
    private List<Recording> recordingList;

    public ISRC() {
        recordingList = new ArrayList<>();
    }
}
