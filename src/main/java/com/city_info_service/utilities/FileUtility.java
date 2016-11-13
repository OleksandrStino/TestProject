package com.city_info_service.utilities;

import com.city_info_service.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * Responsible for reading and validation of the file path received from console
 */
public class FileUtility {

    /**
     * Reads file path from the console and checks of the received values by invoking methods:
     * <p>{@link #isFilePathValid(String)}</p>
     * <p>{@link #isTextFile(String)}</p>
     * It asks user to enter full file path until entered path is valid. If "exit" is entered, the program will close.
     * Otherwise, it will ask to enter valid file path.
     *
     * @return valid value of absolute file path
     */
    public static String readFilePath() {
        BufferedReader reader = null;
        String absoluteFilePath = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            absoluteFilePath = reader.readLine();
            while (!(isFilePathValid(absoluteFilePath) && isTextFile(absoluteFilePath))) {
                if (absoluteFilePath.equals("exit")) {
                    System.out.println(Constants.GOOD_BYE_MESSAGE);
                    System.exit(0);
                }
                System.out.println(Constants.NO_SUCH_FILE_MESSAGE);
                absoluteFilePath = reader.readLine().trim();
            }
        } catch (InvalidPathException e) {
            System.out.println(Constants.INVALID_SYMBOL_IN_FILE_PATH_MESSAGE);
            return readFilePath();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return absoluteFilePath;
    }

    /**
     * Checks entered value of the file path from console.
     * @param absoluteFilePath absolute file path entered from console
     *
     * @return true if file path is not empty and file with passed path exist in system otherwise return false
     */
    private static boolean isFilePathValid(String absoluteFilePath) {
        return !absoluteFilePath.isEmpty() && Files.exists(Paths.get(absoluteFilePath));
    }

    /**
     * Checks if the entered file path has text format.
     * @param absoluteFilePath absolute file path entered from console
     *
     * @return true if the entered file path has one of three text formats (".txt", ".doc", ".docx") otherwise
     * return false
     */
    private static boolean isTextFile(String absoluteFilePath) {
        return absoluteFilePath.endsWith(Constants.TXT_FORMAT) || absoluteFilePath.endsWith(Constants.DOC_FORMAT)
                || absoluteFilePath.endsWith(Constants.DOCX_FORMAT);
    }
}