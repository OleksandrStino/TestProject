package com.city_info_service.solution;

import com.city_info_service.constants.Constants;
import com.city_info_service.utilities.*;

import java.net.URL;
import java.util.Map;
import java.util.Set;

public class CityInfoService {
    public static void main(String[] args) {
        System.out.println(Constants.GREETING_MESSAGE);
        //Valid file path from the console
        String absoluteFilePath = FileUtility.readFilePath();
        //Set of cities from the text file
        Set<String> setOfCities = FileReaderUtility.getSetOfCitiesFromFile(absoluteFilePath);
        //Map of cities names from the file and corresponding urls
        Map<String, URL> mapOfCitiesURLs = FileReaderUtility.createMapOfCitiesURLs(setOfCities);
        //Map of cities names from the file and parsed info from JSON that gained from the corresponding url
        Map<String, String> setOfJsonObjects = JsonUtility.getMapOfParsedJsonObjects(mapOfCitiesURLs);
        //Printing information about cities
        JsonUtility.printCityInfo(setOfJsonObjects);
    }
}