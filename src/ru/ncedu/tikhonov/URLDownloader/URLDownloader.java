package ru.ncedu.tikhonov.URLDownloader;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.nio.file.InvalidPathException;

public class URLDownloader {

    public static final int MAX_BUFFER_SIZE = 4096;

    public static String getDefaultFilePath() {
        return System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
    }

    private static String getFileNameFromURL(String pdfURL, String savePath) {
        String fileName      = pdfURL.substring(pdfURL.lastIndexOf('/') + 1).split("\\?")[0];
        File   savePathFile  = new File(savePath);

        if (savePathFile.isDirectory()) {
            return new File(savePath, fileName).getPath();
        } else if (!savePathFile.exists() && Utils.isValidFileName(savePath)) {
            return new File(getDefaultFilePath(), savePath + ".pdf").getPath();
        }

        return new File(getDefaultFilePath(), fileName).getPath();
    }

    public static String downloadPDF(String pdfURL, String savePath){
        String pdfFileName = getFileNameFromURL(pdfURL, savePath);
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

    public static String convertPdfToPNG(String pdfFileName, int dpi) {
        try {
            PDDocument  document    = Loader.loadPDF(new File(pdfFileName));
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, dpi);
                String        imageFileName = pdfFileName.replace(".pdf", "_page_" + (page + 1) + ".png");

                ImageIOUtil.writeImage(bufferedImage, imageFileName, dpi);
            }
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        return pdfFileName.replace(".pdf", "_page_" + 1 + ".png");
    }

    public static void openFirstImage(String pdfFileName) {
        File firstFile = new File(pdfFileName.replace(".pdf", "_page_" + 1 + ".png"));

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(firstFile);
            } else {
                System.out.println("Error! Opening images is not supported on this system.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}