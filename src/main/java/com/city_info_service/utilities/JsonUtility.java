package com.city_info_service.utilities;

import com.city_info_service.constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * Responsible for work with JSON objects
 */
public class JsonUtility {

    /**
     * Gets JSON object from passed URL
     *
     * @param url received url
     * @return JSON object saved in string
     * @throws IOException
     */
    private static String getJsonObjectFromUrl(URL url) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return stringBuilder.toString();
    }

    /**
     * Returns sorted map of the city names and corresponding JSON objects from received map that contains city
     * names and corresponding urls by invoking {@link #getJsonObjectFromUrl(URL)} for each url.
     *
     * @param urls map of the city names from the file and corresponding urls
     * @return sorted map of the city names from file and corresponding JSON objects
     */
    public static Map<String, String> getMapOfParsedJsonObjects(Map<String, URL> urls) {
        Map<String, String> mapOfParsedJsonFromUrl = new TreeMap<>();
        urls.forEach((cityName, url) -> {
            try {
                mapOfParsedJsonFromUrl.put(cityName, getJsonObjectFromUrl(url));
            } catch (IOException ignored) {
            }
        });
        return mapOfParsedJsonFromUrl;
    }

    /**
     * <p>Parse JSON object received from Google Service to string that contains the name of the city
     * and its coordinates and print them to console.</p>
     * <p>If JSON object is empty it prints "not found" message for received city name from the file.</p>
     *
     * @param nameOfCityFromFile name of the city that was read from the file
     * @param jsonString         JSON object received from URL that was created from the city name
     */
    private static void parseCurrentCityInfoJson(String nameOfCityFromFile, String jsonString) {
        try {
            JSONObject cityInfoObject = (JSONObject) JSONValue.parseWithException(jsonString);
            JSONArray array = (JSONArray) cityInfoObject.get("results");
            if (array.isEmpty()) {
                System.out.println(nameOfCityFromFile.concat(Constants.RESULTS_NOT_FOUND));
                return;
            }
            //Parse of the city name from Google service
            JSONObject jsonObject = (JSONObject) array.get(0);
            JSONArray cityAddressComponents = (JSONArray) jsonObject.get("address_components");
            String cityName = String.valueOf(((JSONObject) cityAddressComponents.get(0)).get("long_name"));
            System.out.println("City Name: " + cityName);

            //Parse of the city location
            JSONObject cityGeoInfo = (JSONObject) jsonObject.get("geometry");
            JSONObject cityLocationInfo = (JSONObject) cityGeoInfo.get("location");
            String cityLatitude = String.valueOf(cityLocationInfo.get("lat"));
            String cityLongitude = String.valueOf(cityLocationInfo.get("lng"));
            String cityLocation = String.format("Coordinates: (Latitude %s; Longitude %s)", cityLatitude, cityLongitude);
            System.out.println(cityLocation);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints city info by invoking {@link #parseCurrentCityInfoJson(String, String)} for each JSON object in map.
     *
     * @param mapOfJsonObjects map of the city names from the file and corresponding JSON objects from Google service
     */
    public static void printCityInfo(Map<String, String> mapOfJsonObjects) {
        mapOfJsonObjects.forEach((cityName, parsedJsonObject) -> {
            System.out.println();
            //Using Thread.sleep(150) to avoid OVER_QUERY_LIMIT (more than 10 query per second)
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("*********************************************************");
            parseCurrentCityInfoJson(cityName, parsedJsonObject);
            System.out.println("*********************************************************");
        });
    }
}