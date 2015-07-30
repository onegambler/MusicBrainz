package com.musicbrainz.domain;

public enum AreaSearchField {
    AID("The area ID"),
    ALIAS("The aliases/misspellings for this area"),
    AREA("The area name"),
    BEGIN("area begin date"),
    COMMENT("disambugation comment"),
    END("area end date"),
    ENDED("area ended"),
    SORTNAME("area sort name"),
    ISO("area iso1, iso2 or iso3 codes"),
    ISO1("area iso1 codes"),
    ISO2("area iso3 codes"),
    ISO3("area iso3 codes"),
    TYPE("the aliases/misspellings for this label");

    private String description;

    AreaSearchField(String description) {
        this.description = description;
    }

}
