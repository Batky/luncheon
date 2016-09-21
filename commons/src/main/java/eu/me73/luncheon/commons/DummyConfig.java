package eu.me73.luncheon.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DummyConfig {

    public static BufferedReader getUserFile(final String fileName) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"windows-1250"));
    }

    public final static int FIRST_YEAR_OF_ORDER_IMPORTING = 2015;
}
