package com.city_info_service.readers;

import java.util.Set;

/**
 * Interface for strategy pattern for the FileReaderUtility
 */
public interface FileReaderStrategy {

    Set<String> readData(String absoluteFilePath);

    /**
     * Checks if the received text line contains more than one value and if it contains then method adds each
     * value to the set of received set of strings, otherwise the method adds received text to the received set.
     *
     * @param text    read line from the file
     * @param strings set of the strings from the file
     */
    default void splitterOfText(String text, Set<String> strings) {
        if (!text.trim().isEmpty()) {
            String[] splitString = text.split("[,.:;\r\n]");
            if (splitString.length > 1) {
                for (String s : splitString) {
                    if (!s.isEmpty()) {
                        strings.add(s.trim());
                    }
                }
            } else {
                strings.add(text.trim());
            }
        }
    }
}
