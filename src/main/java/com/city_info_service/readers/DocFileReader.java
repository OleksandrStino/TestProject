package com.city_info_service.readers;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of FileReaderStrategy interface. Responsible for reading "*.doc" files.
 */
public class DocFileReader implements FileReaderStrategy {

    /**
     * Reads text data from "*.doc" files and adds each string to the set by invoking
     * {@link #splitterOfText(String, Set)}
     *
     * @param absoluteFilePath valid value of file path entered by user from console
     * @return set of read strings from file
     */
    @Override
    public Set<String> readData(String absoluteFilePath) {
        Set<String> strings = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(absoluteFilePath)) {
            HWPFDocument doc = new HWPFDocument(fis);
            WordExtractor we = new WordExtractor(doc);
            String[] line = we.getParagraphText();
            for (String part : line) {
                splitterOfText(part, strings);
            }
        } catch (Exception ignored) {
        }
        return strings;
    }
}
