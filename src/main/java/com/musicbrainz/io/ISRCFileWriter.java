package com.musicbrainz.io;

import com.musicbrainz.objects.ISRC;

public class ISRCFileWriter extends FileWriter<ISRC> {

    public ISRCFileWriter(java.io.FileWriter writer) {
        super(writer);
    }

    @Override
    public String getObjectAsString(ISRC object) {
        return object.toString();
    }

    @Override
    protected String getColumnNames() {
        return "ISRC ID,RECORDING ID,RECORDING TITLE,ARTIST ID,ARTIST NAME,WORK ID,WORK TITLE,ISWC";
    }


}
