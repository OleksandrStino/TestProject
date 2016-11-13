package com.city_info_service.readers;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of FileReaderStrategy interface. Responsible for reading "*.docx" files.
 */
public class DocxFileReader implements FileReaderStrategy {

    /**
     * Reads text data from "*.docx" files and adds each string to the set by invoking
     * {@link #splitterOfText(String, Set)}
     *
     * @param absoluteFilePath valid value of the file path entered by user from console
     * @return set of read strings from file
     */
    @Override
    public Set<String> readData(String absoluteFilePath) {
        Set<String> strings = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(absoluteFilePath)) {
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> lines = document.getParagraphs();
            for (XWPFParagraph line : lines) {
                splitterOfText(line.getText(), strings);
            }
        } catch (Exception ignored) {
        }
        return strings;
    }
}