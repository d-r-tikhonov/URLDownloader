package ru.ncedu.tikhonov.URLDownloader;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String  pdfURL         = null;
        String  savePath       = URLDownloader.getDefaultFilePath();
        boolean openFirstImage = false;
        int     dpi            = 300;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("--open")) {
                openFirstImage = true;
            } else if (pdfURL == null) {
                pdfURL = arg;
            } else if (arg.matches("\\d+")) {
                dpi = Integer.parseInt(arg);
            } else {
                savePath = arg;
            }
        }

        if (pdfURL == null) {
            System.out.println("You must specify the URL of the PDF file.");
            return;
        }

        String pdfFileName = URLDownloader.downloadPDF(pdfURL, savePath);
        URLDownloader.convertPdfToPNG(pdfFileName, dpi);
        if (openFirstImage) {
            URLDownloader.openFirstImage(pdfFileName);
        }
    }
}