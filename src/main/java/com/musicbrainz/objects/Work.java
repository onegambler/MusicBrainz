package com.musicbrainz.objects;

public class Work extends ApiObject {


    private String id;
    private String title;
    private String iswc;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIswc(String iswc) {
        this.iswc = iswc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendIfNotNull(sb, id);
        sb.append(COMMA_SEPARATOR);
        appendIfNotNull(sb, escape(title));
        sb.append(COMMA_SEPARATOR);
        appendIfNotNull(sb, iswc);
        return sb.toString();
    }
}
