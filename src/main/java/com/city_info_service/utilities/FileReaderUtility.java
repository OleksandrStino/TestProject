package com.city_info_service.utilities;

import com.city_info_service.constants.Constants;
import com.city_info_service.readers.DocFileReader;
import com.city_info_service.readers.DocxFileReader;
import com.city_info_service.readers.FileReaderStrategy;
import com.city_info_service.readers.TxtFileReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Responsible for reading text files
 */
public class FileReaderUtility {

    private FileReaderStrategy fileReaderStrategy;

    public FileReaderUtility(FileReaderStrategy iFileReader) {
        this.fileReaderStrategy = iFileReader;
    }

    /**
     * Creates map of city names from the file and corresponding urls from transferred set of city names.
     *
     * @param setOfCityNames set of city names received from the read file
     * @return map of city names from the file and corresponding urls
     */
    public static Map<String, URL> createMapOfCitiesURLs(Set<String> setOfCityNames) {
        Map<String, URL> mapOfURLs = new HashMap<>();
        setOfCityNames.forEach((cityName) -> {
            try {
                mapOfURLs.put(cityName, new URL(String.format(Constants.ULR_STRING, cityName.replaceAll(" ", ""))));
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        });
        return mapOfURLs;
    }

    /**
     * Returns set of read strings from the file, by invoking {@link FileReaderStrategy#readData(String)} in the
     * instance of FileReaderStrategy that creates in accordance  of thr file extension received from the
     * absoluteFilePath.
     *
     * @param absoluteFilePath valid file path entered by user from console
     * @return set of read strings from the file
     */
    public static Set<String> getSetOfCitiesFromFile(String absoluteFilePath) {
        String fileExtension = absoluteFilePath.substring(absoluteFilePath.lastIndexOf("."), absoluteFilePath.length());
        switch (fileExtension) {
            case Constants.TXT_FORMAT:
                return new FileReaderUtility(new TxtFileReader()).fileReaderStrategy.readData(absoluteFilePath);
            case Constants.DOC_FORMAT:
                return new FileReaderUtility(new DocFileReader()).fileReaderStrategy.readData(absoluteFilePath);
            case Constants.DOCX_FORMAT:
                return new FileReaderUtility(new DocxFileReader()).fileReaderStrategy.readData(absoluteFilePath);
            default:
                return null;
        }
    }
}