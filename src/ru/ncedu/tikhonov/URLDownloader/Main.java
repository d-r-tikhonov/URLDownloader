package ru.ncedu.tikhonov.URLDownloader;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String pdfURL = args[0];

        String pdfFileName = URLDownloader.downloadPDF(pdfURL);
        URLDownloader.convertPdfToPNG(pdfFileName);
    }
}