package com.kobalt.io;

import com.google.common.base.Throwables;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public abstract class FileWriter<T extends Object> implements AutoCloseable {

    private java.io.FileWriter writer;

    public FileWriter(java.io.FileWriter writer) {
        this.writer = writer;
        try {
            writer.write(getColumnNames());
            writer.write("\n");
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    public void write(T object) {
        requireNonNull(object, "Passed object is null");
        try {
            this.writer.write(getObjectAsString(object));
            this.writer.write("\n");
            this.writer.flush();
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    public abstract String getObjectAsString(T object);

    public void close() throws Exception {
        this.writer.close();
    }

    protected abstract String getColumnNames();
}
