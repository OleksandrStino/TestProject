package com.city_info_service.constants;

/**
 * Stores String constants
 */
public interface Constants {
    String ULR_STRING = "https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=%s";
    String GREETING_MESSAGE = "****************City Location Info Service*************** \nPlease enter absolute " +
            "path of the file with the city list";
    String NO_SUCH_FILE_MESSAGE = "There is no text file with such path. \nPlease enter valid file path." +
            " \nTo leave the program enter \"exit\"";
    String INVALID_SYMBOL_IN_FILE_PATH_MESSAGE = "The entered file path contains invalid symbol. " +
            "\nPlease enter valid file path. \nTo leave the program enter \"exit\"";
    String GOOD_BYE_MESSAGE = "Good Bye. Have a nice day!";
    String TXT_FORMAT = ".txt";
    String DOC_FORMAT = ".doc";
    String DOCX_FORMAT = ".docx";
    String RESULTS_NOT_FOUND = " data not found";
}
