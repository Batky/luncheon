package eu.me73.luncheon.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DummyConfig {

    public static BufferedReader createBufferedReaderFromFileName(final String fileName) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"windows-1250"));
    }

}
