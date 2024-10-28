package ru.ncedu.tikhonov.URLDownloader;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Utils {

    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        String invalidChars = "\\/:*?\"<>|";
        for (char c : invalidChars.toCharArray()) {
            if (fileName.indexOf(c) != -1){
                return false;
            }
        }

        try {
            Paths.get(fileName);
        } catch (InvalidPathException e) {
            return false;
        }

        return true;

    }
}