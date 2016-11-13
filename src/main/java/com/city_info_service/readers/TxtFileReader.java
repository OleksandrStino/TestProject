package com.city_info_service.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of FileReaderStrategy interface. Responsible for reading "*.txt" files.
 */
public class TxtFileReader implements FileReaderStrategy {

    /**
     * Reads text data from "*.txt" files and adds each string to the set by invoking
     * {@link #splitterOfText(String, Set)}
     *
     * @param absoluteFilePath valid value of file path entered by user from console
     * @return set of read strings from file
     */
    @Override
    public Set<String> readData(String absoluteFilePath) {
        Set<String> strings = new HashSet<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(absoluteFilePath))) {
            String part;
            while ((part = fileReader.readLine()) != null) {
                splitterOfText(part, strings);
            }
        } catch (IOException ignored) {
        }
        return strings;
    }
}
