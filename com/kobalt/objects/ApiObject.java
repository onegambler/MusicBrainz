package com.kobalt.objects;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ApiObject {

    public static final String COMMA_SEPARATOR = ",";

    protected String escape(String value) {
        if (value == null) {
            return value;
        }

        return value.replaceAll(",", " ");
    }

    void appendIfNotNull(StringBuilder sb, String value) {
        if (!isNullOrEmpty(value)) {
            sb.append(value);
        }
    }
}
