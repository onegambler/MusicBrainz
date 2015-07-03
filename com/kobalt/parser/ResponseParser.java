package com.kobalt.parser;

public interface ResponseParser<T extends Object> {

    T parse(String response);
}
