package com.musicbrainz.parser;

import com.google.common.base.Throwables;
import com.musicbrainz.objects.Artist;
import com.musicbrainz.objects.ISRC;
import com.musicbrainz.objects.Recording;
import com.musicbrainz.objects.Work;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;

public class ISRCXMLParser implements ResponseParser<ISRC> {

    public static final String RECORDING_LIST_TAG_NAME = "recording-list";
    public static final String ID_ATTRIBUTE = "id";
    public static final String ARTIST_TAG = "artist";
    public static final String TITLE_TAG = "title";
    public static final String NAME_TAG = "name";
    public static final String WORK_TAG = "work";
    public static final String ISWC_TAG = "iswc";
    public static final String ISRC_TAG = "isrc";
    private final DocumentBuilder dBuilder;

    public ISRCXMLParser() throws ParserConfigurationException {
        dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }


    public ISRC parse(String response) {
        Document document = null;
        try {
            document = dBuilder.parse(new InputSource(new StringReader(response)));
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        ISRC isrc = new ISRC();

        assert document != null;
        isrc.setId(document.getElementsByTagName(ISRC_TAG).item(0).getAttributes().item(0).getNodeValue());
        NodeList recordingListNodes = document.getElementsByTagName(RECORDING_LIST_TAG_NAME);

        if (recordingListNodes.getLength() == 1) {
            Recording currentRecording;
            Node recordingListNode = recordingListNodes.item(0);
            if (isElementNode(recordingListNode)) {
                NodeList recordingNodeCollection = recordingListNode.getChildNodes();
                for (int i = 0; i < recordingNodeCollection.getLength(); i++) {
                    Node recordingNode = recordingNodeCollection.item(i);
                    if (isElementNode(recordingNodeCollection.item(i))) {
                        currentRecording = parseRecording((Element) recordingNode);
                        isrc.getRecordingList().add(currentRecording);
                    }
                }
            }
        }

        return isrc;
    }

    private Recording parseRecording(Element element) {
        Recording recording = new Recording();
        recording.setId(element.getAttribute(ID_ATTRIBUTE));
        recording.setTitle(element.getElementsByTagName(TITLE_TAG).item(0).getTextContent());

        NodeList artistNodeList = element.getElementsByTagName(ARTIST_TAG);
        if (artistNodeList.getLength() > 0) {
            Node artistNode = artistNodeList.item(0);
            if (isElementNode(artistNode)) {
                recording.setArtist(parseArtist((Element) artistNode));
            }
        }

        NodeList workNodeList = element.getElementsByTagName(WORK_TAG);
        if (workNodeList.getLength() > 0) {
            Node workNode = workNodeList.item(0);
            if (isElementNode(workNode)) {
                recording.setWork(parseWork((Element) workNode));
            }
        }

        return recording;
    }

    private Artist parseArtist(Element element) {
        Artist artist = new Artist();
        artist.setId(element.getAttribute(ID_ATTRIBUTE));
        artist.setName(element.getElementsByTagName(NAME_TAG).item(0).getTextContent());
        return artist;
    }

    private Work parseWork(Element element) {
        Work work = new Work();
        work.setId(element.getAttribute(ID_ATTRIBUTE));
        work.setTitle(element.getElementsByTagName(TITLE_TAG).item(0).getTextContent());
        NodeList iswcNodeList = element.getElementsByTagName(ISWC_TAG);
        if (iswcNodeList.getLength() > 0) {
            work.setIswc(iswcNodeList.item(0).getTextContent());
        }
        return work;
    }

    private boolean isElementNode(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }
}
