package com.musicbrainz.main;

import com.google.common.base.Preconditions;
import com.musicbrainz.io.FileWriter;
import com.musicbrainz.io.ISRCFileWriter;
import com.musicbrainz.io.LoggerSingleton;
import com.musicbrainz.objects.ISRC;
import com.musicbrainz.parser.ISRCXMLParser;
import com.musicbrainz.parser.ResponseParser;
import com.musicbrainz.rest.JerseyClient;
import com.musicbrainz.rest.RESTClient;
import com.sun.jersey.api.client.Client;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;

public class Main {

    private static final String END_POINT = "http://musicbrainz.org/ws/2/isrc/%s?inc=work-rels+artists";

    private static final Logger logger = LoggerSingleton.getLogger();

    public static void main(String[] args) throws ParserConfigurationException {

        Preconditions.checkArgument(args.length == 1, "Usage: please specify the input file");
        String fileName = args[0];
        requireNonNull(fileName, "File name cannot be null");
        RESTClient client = new JerseyClient(Client.create());
        ResponseParser<ISRC> parser = new ISRCXMLParser();
        String endPoint;
        String response;
        String line;
        logger.info("Ready to process file " + fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            try (FileWriter<ISRC> writer = new ISRCFileWriter(new java.io.FileWriter(fileName + ".csv"))) {

                while ((line = reader.readLine()) != null) {
                    logger.info("Calling MusicBrainz with id " + line);
                    endPoint = String.format(END_POINT, line);
                    try {
                        response = client.get(endPoint);

                        checkNotNull(response, "GET response is null");
                        ISRC isrcResponse = parser.parse(response);
                        checkNotNull(isrcResponse, "ISRC object parsed is null");
                        writer.write(isrcResponse);
                        logger.info("CALL SUCCESSFUL! for id " + line);
                    } catch (RuntimeException e) {
                        logger.severe("ERROR: Impossible to retrieve information for id " + line + ". Error: " + e.getMessage());
                    }
                    logger.info("Now taking a nap before trying with next one");
                    Thread.sleep(2000);
                }
            }

        } catch (Exception e) {
            logger.severe("REALLY BAD ERROR! " + e.getMessage());
        }
    }
}
