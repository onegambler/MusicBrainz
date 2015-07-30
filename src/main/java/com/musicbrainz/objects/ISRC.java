package com.musicbrainz.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "isrc")
public class ISRC extends ApiObject {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int recordingListSize = getRecordingList().size();
        for (int i = 0; i < recordingListSize; i++) {
            sb.append(id);
            sb.append(COMMA_SEPARATOR);
            sb.append(recordingList.get(i).toString());
            sb.append(COMMA_SEPARATOR);
            if (i < recordingListSize - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
