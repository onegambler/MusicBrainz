package com.kobalt.objects;

public class Recording extends ApiObject {

    private String id;
    private String recordingID;
    private String title;
    private Artist artist;
    private Work work;

    public Recording() {
        this.artist = new Artist();
        this.work = new Work();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendIfNotNull(sb, id);
        sb.append(COMMA_SEPARATOR);
        appendIfNotNull(sb, escape(title));
        sb.append(COMMA_SEPARATOR);
        sb.append(artist.toString());
        sb.append(COMMA_SEPARATOR);
        sb.append(work.toString());
        return sb.toString();
    }
}
