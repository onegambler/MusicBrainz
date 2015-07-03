package com.musicbrainz.objects;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Artist extends ApiObject {

    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!isNullOrEmpty(id)) {
            sb.append(id);
        }
        sb.append(COMMA_SEPARATOR);
        if (!isNullOrEmpty(name)) {
            sb.append(escape(name));
        }
        return sb.toString();
    }

}
