package ru.ncedu.tikhonov.URLDownloader;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;

public class URLDownloader {

    public static final int MAX_BUFFER_SIZE = 4096;

    private static String getFileNameFromURL(String pdfURL) {
        String fileName = pdfURL.substring(pdfURL.lastIndexOf('/') + 1).split("\\?")[0];
        return fileName.isEmpty() ? "document.pdf" : fileName;
    }

    public static String downloadPDF(String pdfURL){
        String pdfFileName = getFileNameFromURL(pdfURL);
        URL    url         = null;

        try {
            url = new URL (pdfURL);
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            FileOutputStream out = new FileOutputStream(pdfFileName);

            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            int bytesRead = 0;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("The PDF file has been uploaded: " + pdfFileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return pdfFileName;
    }
}